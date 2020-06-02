package edu.cs.stonybrook.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cs.stonybrook.errorAndLog.Error;
import edu.cs.stonybrook.errorAndLog.LogEntry;
import edu.cs.stonybrook.errorAndLog.PrecinctPair;
import edu.cs.stonybrook.map.Precinct;
import edu.cs.stonybrook.map.State;
import edu.cs.stonybrook.repositories.LogEntryRepository;

@Service
public class LogService {
	static final String DEFAULT_USER = "DERRICK ROSE";
	
	@Autowired
	LogEntryRepository logEntryRepo;
	
	@Autowired
	PrecinctPairRepository ppRepo;
	
	public void addNewEntry(Error error, State state, Precinct oldPrecinct1, Precinct modifiedPrecinct1,
			Precinct oldPrecinct2, Precinct modifiedPrecinct2) {
		List<PrecinctPair> precincts =  new ArrayList<>();
		LogEntry entry = new LogEntry(state, error, LocalDateTime.now(), DEFAULT_USER, precincts);
		PrecinctPair pp2 = null;
		PrecinctPair pp1 = null;
		
		if(oldPrecinct1 != null) {
			pp1 =  new PrecinctPair(oldPrecinct1, modifiedPrecinct1, entry);
			
			precincts.add(pp1);
		}
		if(oldPrecinct2 != null) {
			 pp2 =  new PrecinctPair(oldPrecinct2, modifiedPrecinct2, entry);
			
			precincts.add(pp2);
		}
			
		logEntryRepo.saveAndFlush(entry);
		if(pp1 != null)
			ppRepo.saveAndFlush(pp1);
		if(pp2 != null)
			ppRepo.saveAndFlush(pp2);
	}
}
