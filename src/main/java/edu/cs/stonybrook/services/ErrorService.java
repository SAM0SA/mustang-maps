package edu.cs.stonybrook.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cs.stonybrook.map.Bounds;
import edu.cs.stonybrook.map.Precinct;
import edu.cs.stonybrook.errorAndLog.Error;
import edu.cs.stonybrook.errorAndLog.EnclosedPrecinctError;
import edu.cs.stonybrook.errorAndLog.ErrorType;
import edu.cs.stonybrook.errorAndLog.OverlapError;
import edu.cs.stonybrook.repositories.BoundsRepository;
import edu.cs.stonybrook.repositories.ErrorRepository;
import edu.cs.stonybrook.repositories.PrecinctRepository;

@Service
public class ErrorService {
	
	private final int STATE = 0;
	private final int PRECINCT = 1;
	
	@Autowired
	private ErrorRepository errorRepo;
	
	@Autowired
	private PrecinctRepository precinctRepo;
	
	@Autowired
	private BoundsRepository boundsRepo;
	
	@Autowired
	LogService logService;

	public boolean resolveError() {
		return false;
	}
	
	public Map<ErrorType, List<Error>> getErrorsForRegionByType(String regionId, int regionType){
		Map<ErrorType, List<Error>> errorMap = new HashMap<>();
		System.out.println("GETTING ERRORS....");
		switch(regionType) {
			case STATE:
				for(ErrorType e: ErrorType.values()) {
					errorMap.put(e, errorRepo.getErrorsByTypeForState(e.ordinal(), regionId));
				}
				System.out.println("ERRORS " + errorMap);
				return errorMap;
//			case PRECINCT:
//				for(ErrorType e: ErrorType.values()) {
//					errorMap.put(e, errorRepo.getErrorsByTypeForPrecinct(e.ordinal(), regionId));
//				}
//				return errorMap;
		}
		return null;	
	}
	
	public String handleEnclosedPrecincts(Integer errorId, Boolean merge, String newBounds) {
		if(errorRepo.findById(errorId).isPresent()) {
			EnclosedPrecinctError enclosedError = (EnclosedPrecinctError) errorRepo.findById(errorId).get();
			
			if(!merge) {
				enclosedError.setResolved(true);
				errorRepo.saveAndFlush(enclosedError);
				return null;
			}
			
			Precinct enclosingPrecinct = enclosedError.getPrecinct();
			Precinct enclosedPrecinct = enclosedError.getEnclosedPrecinct();
			
//			enclosingPrecinct.setDisplayOnMap(false);
			enclosedPrecinct.setDisplayOnMap(false);
			
			String newId = enclosingPrecinct.getRegionId();
			
			Bounds nb = new Bounds(0,0,newBounds);
			boundsRepo.saveAndFlush(nb);
			
			Precinct oldversion = enclosingPrecinct.getCopyAndUpdateVersion(false);
			precinctRepo.saveAndFlush(oldversion);
			enclosingPrecinct.setBounds(nb);
			precinctRepo.saveAndFlush(enclosingPrecinct);
			logService.addNewEntry(enclosedError, enclosedPrecinct.getState(), enclosedPrecinct, enclosingPrecinct, oldversion, enclosingPrecinct);
			
			return newId;
		}
		return null;
	}
	
	public String mergePrecincts(String p1Id, String p2Id, String bounds) {
			
			Precinct enclosingPrecinct = precinctRepo.findById(p1Id).get();
			Precinct enclosedPrecinct = precinctRepo.findById(p2Id).get();
			
//			enclosingPrecinct.setDisplayOnMap(false);
			enclosedPrecinct.setDisplayOnMap(false);
			
			String newId = enclosingPrecinct.getRegionId();
			
			Bounds nb = new Bounds(0,0,bounds);
			boundsRepo.saveAndFlush(nb);
			
			Precinct oldversion = enclosingPrecinct.getCopyAndUpdateVersion(false);
			precinctRepo.saveAndFlush(oldversion);
			enclosingPrecinct.setBounds(nb);
			precinctRepo.saveAndFlush(enclosingPrecinct);
			logService.addNewEntry(null, enclosedPrecinct.getState(), enclosedPrecinct, enclosingPrecinct, oldversion, enclosingPrecinct);
			
			return newId;
		
	}

	public Boolean createGhostPrecinct(Integer errorId, Boolean isGhost, String ghostPrecinctId){
		if(errorRepo.findById(errorId).isPresent()) {
			Error unmappedError = errorRepo.findById(errorId).get();
			unmappedError.setResolved(true);
			errorRepo.saveAndFlush(unmappedError);
			Precinct newPrecinct = null;
			
			if(isGhost) {
				newPrecinct = new Precinct(ghostPrecinctId, unmappedError.getErrorLocation(), null, "",
						unmappedError.getState(), null, isGhost, true, 0);
				precinctRepo.saveAndFlush(newPrecinct);
			}
			logService.addNewEntry(unmappedError, unmappedError.getState(), newPrecinct, null, null, null);
			return true;
		}
		return false;
	}

	public Boolean handleOverlappingPrecincts(Integer errorId, String p1BoundsMap, String p2BoundsMap) {
		if(errorRepo.findById(errorId).isPresent()) {
			OverlapError overlapError = (OverlapError) errorRepo.findById(errorId).get();
			
			Bounds p1Bounds = new Bounds(0,0,p1BoundsMap);
			Bounds p2Bounds = new Bounds(0,0,p2BoundsMap);
			
			boundsRepo.saveAndFlush(p1Bounds);
			boundsRepo.saveAndFlush(p2Bounds);
			
			Precinct precinct1 = overlapError.getPrecinct();
			Precinct precinct2 = overlapError.getSecondOverlappingPrecinct();
			Precinct oldPrecinct1 = precinct1.getCopyAndUpdateVersion(false);
			Precinct oldPrecinct2 = precinct2.getCopyAndUpdateVersion(false);
			
			precinctRepo.saveAndFlush(oldPrecinct1);
			precinctRepo.saveAndFlush(oldPrecinct2);
			
			precinct1.setBounds(p1Bounds);
			precinct2.setBounds(p2Bounds);
			
			precinctRepo.saveAndFlush(precinct1);
			precinctRepo.saveAndFlush(precinct2);
			
			overlapError.setResolved(true);
			errorRepo.saveAndFlush(overlapError);
			
			logService.addNewEntry(overlapError, overlapError.getState(), oldPrecinct1, precinct1, oldPrecinct2, precinct2);
			return true;
		}
		return false;
	}
	
	public void ignoreError(Integer errorId) {
		Error error = errorRepo.findById(errorId).get();
		
		error.setResolved(true);
		errorRepo.saveAndFlush(error);
		logService.addNewEntry(error, error.getState(), null, null, null, null);

	}

	public List<Error> getErrorsByType(int errorType, String regionId) {
		
		return errorRepo.getErrorsByTypeForState(errorType, regionId);
	}
}
