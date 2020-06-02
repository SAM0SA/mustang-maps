package edu.cs.stonybrook.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.cs.stonybrook.map.Bounds;
import edu.cs.stonybrook.map.Precinct;
import edu.cs.stonybrook.services.PrecinctService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PrecinctController {
	
	@Autowired
	PrecinctService precinctService;

	@RequestMapping(value = "/precincts/getNeighbors", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getNeighbors(@RequestParam String precinctId){
		return precinctService.getNeighbors(precinctId);
	}

	@RequestMapping(value = "/precincts/getPrecinctsByState", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Precinct> getPrecinctsByState(@RequestParam String stateId){
		return precinctService.getPrecinctsByState(stateId);
	}

	@RequestMapping(value = "/precincts/getPrecinctBoundsByState", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Precinct> getPrecinctBoundsByState(@RequestParam String stateId){
		return precinctService.getPrecinctBoundsByState(stateId);
	}
	
	@RequestMapping(value = "/precincts/updateNeighbors", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE)
	public Boolean updateNeighbors(@RequestBody Map<String,Object> body){
		return precinctService.updateNeighbors((String)body.get("precinct1Id"), (String)body.get("precinct2Id"), 
				(Boolean) body.get("makeNeighbors"));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/precincts/updateBounds", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE)
	public Boolean updateBounds(@RequestBody Map<String,Object> body){
		return precinctService.updateBounds((String)body.get("precinctId"), (String) body.get("precinctBounds"), (Integer) body.get("errorId"));
	}
}
