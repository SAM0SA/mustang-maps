package edu.cs.stonybrook.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cs.stonybrook.errorAndLog.OverlapError;
import edu.cs.stonybrook.errorAndLog.Error;
import edu.cs.stonybrook.map.Bounds;
import edu.cs.stonybrook.map.Precinct;
import edu.cs.stonybrook.map.State;
import edu.cs.stonybrook.repositories.BoundsRepository;
import edu.cs.stonybrook.repositories.ErrorRepository;
import edu.cs.stonybrook.repositories.PrecinctRepository;
import edu.cs.stonybrook.repositories.StateRepository;

@Service
public class PrecinctService {
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private PrecinctRepository precinctRepo;
	
	@Autowired
	private BoundsRepository boundsRepo;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private ErrorRepository errorRepo;

	
	public List<String> getNeighbors(String precinctId) {
		List<String> neighborIds = new ArrayList<>();
		if(precinctRepo.findById(precinctId).isPresent()) {
			Precinct requestedPrecinct = precinctRepo.findById(precinctId).get();
			System.out.println("GEtting neighbors...");
			System.out.println(requestedPrecinct.getNeighbors());
			System.out.println(requestedPrecinct.getRegionId());
			for(Precinct neighbor: requestedPrecinct.getNeighbors()) {
				neighborIds.add(neighbor.getRegionId());
			}
		}
		return neighborIds;
	}

	public List<Precinct> getPrecinctsByState(String stateId) {
		if(stateRepo.findById(stateId).isPresent()) {
			return precinctRepo.getLatestPrecinct(stateId);
		}
		return null;
	}
	
	public List<Precinct> getPrecinctBoundsByState(String stateId) {
		List<Precinct> precinctBounds = new ArrayList<>();
		if(stateRepo.findById(stateId).isPresent()) {
			for(Precinct precinct: precinctRepo.getLatestPrecinct(stateId)) {
				precinct.setNeighbors(null);
				precinct.setErrors(null);
				precinct.setState(null);
				precinctBounds.add(precinct);
			}
			return precinctBounds;
		}
		return null;
	}
	
	public Boolean updateNeighbors(String precinct1Id, String precinct2Id, Boolean makeNeighbors ) {
		if((precinctRepo.findById(precinct1Id)).isPresent() && precinctRepo.findById(precinct2Id).isPresent()) {
			Precinct precinct1 = precinctRepo.findById(precinct1Id).get();
			Precinct precinct2 = precinctRepo.findById(precinct2Id).get();
			Precinct oldPrecinct1 = precinct1.getCopyAndUpdateVersion(false);
			Precinct oldPrecinct2 = precinct2.getCopyAndUpdateVersion(false);
			
			if(makeNeighbors) {
				precinctRepo.saveAndFlush(oldPrecinct1);
				precinctRepo.saveAndFlush(oldPrecinct2);
				precinct1.addNeighbor(precinct2);
				precinct2.addNeighbor(precinct1);
			}else {
				if(!precinct1.getNeighbors().contains(precinct2) || !precinct2.getNeighbors().contains(precinct1) ) return false;
				precinctRepo.saveAndFlush(oldPrecinct1);
				precinctRepo.saveAndFlush(oldPrecinct2);
				precinct1.removeNeighbor(precinct2);
				precinct2.removeNeighbor(precinct1);
			}
			
			precinctRepo.saveAndFlush(precinct1);
			precinctRepo.saveAndFlush(precinct2);
			logService.addNewEntry(null, precinct1.getState(), oldPrecinct1, precinct1, oldPrecinct2, precinct2);
			return true;
		}
		
		return false;
	}
	
	public Boolean updateBounds(String precinctId, String bounds, int errorId) {
	
		if((precinctRepo.findById(precinctId)).isPresent()) {
			Precinct precinct = precinctRepo.findById(precinctId).get();
			Bounds newBounds = new Bounds(0, 0, bounds);
			boundsRepo.saveAndFlush(newBounds);
			Precinct oldPrecinct = precinct.getCopyAndUpdateVersion(false);
			oldPrecinct.setDisplayOnMap(false);
			precinctRepo.saveAndFlush(oldPrecinct);
			precinct.setBounds(newBounds);
			precinct.setDisplayOnMap(true);
			precinctRepo.saveAndFlush(precinct);
			if(errorId != -1) {
				Error e = errorRepo.findById(errorId).get();
				e.setResolved(true);
				errorRepo.saveAndFlush(e);
				logService.addNewEntry(e, precinct.getState(), oldPrecinct, precinct, null, null);
			}else {
				logService.addNewEntry(null, precinct.getState(), oldPrecinct, precinct, null, null);
			}
			
			
			return true;
		}
		return false;
	}	
}

