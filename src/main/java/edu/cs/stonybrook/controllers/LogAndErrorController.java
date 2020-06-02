package edu.cs.stonybrook.controllers;

import java.util.HashMap;
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

import edu.cs.stonybrook.services.ErrorService;
import edu.cs.stonybrook.services.LogService;
import edu.cs.stonybrook.errorAndLog.Error;
import edu.cs.stonybrook.errorAndLog.ErrorType;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LogAndErrorController {

	@Autowired
	ErrorService errorService;
	
	@Autowired
	LogService logService;

	@RequestMapping(value = "/perrors/getErrors", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<ErrorType, List<Error>> getErrors(@RequestParam String regionId, @RequestParam int regionType){
		return errorService.getErrorsForRegionByType(regionId, regionType);
	}
	
	@RequestMapping(value = "/perrors/handleEnclosedPrecincts", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> handleEnclosedPrecincts(@RequestBody Map<String,Object> body){
		Map<String, String> res = new HashMap<String, String>();
		res.put("newPrecinctId", errorService.handleEnclosedPrecincts((Integer) body.get("errorId"), 
				(Boolean) body.get("merge"), (String) body.get("newBounds") ));
		return res;
	}
	
	@RequestMapping(value = "/perrors/mergePrecincts", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> mergePrecincts(@RequestBody Map<String,Object> body){
		Map<String, String> res = new HashMap<String, String>();
		res.put("newPrecinctId", errorService.mergePrecincts((String) body.get("p1Id"), 
				(String) body.get("p2Id"), (String) body.get("bounds") ));
		return res;
	}
	
	@RequestMapping(value = "/perrors/createGhostPrecinct", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean createGhostPrecinct(@RequestBody Map<String,Object> body){
		return errorService.createGhostPrecinct((Integer)body.get("errorId"), (Boolean) body.get("isGhost"), 
				(String) body.get("id"));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/perrors/handleOverlappingPrecincts", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean handleOverlappingPrecincts(@RequestBody Map<String,Object> body){
		return errorService.handleOverlappingPrecincts((Integer)body.get("errorId"), 
				(String) body.get("p1Bounds"), 
				(String) body.get("p2Bounds"));
	}
	
	@RequestMapping(value = "/perrors/ignoreError", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public void ignoreError(@RequestBody Map<String,Object> body){
		 errorService.ignoreError((Integer)body.get("errorId"));
	}
	
	@RequestMapping(value = "/perrors/getErrorsByType", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Error> getErrorsByType(@RequestParam int errorType, @RequestParam String regionId){
		return errorService.getErrorsByType(errorType, regionId);
	}
}
