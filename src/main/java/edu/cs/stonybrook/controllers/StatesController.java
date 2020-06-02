package edu.cs.stonybrook.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.cs.stonybrook.map.Bounds;
import edu.cs.stonybrook.map.NationalPark;
import edu.cs.stonybrook.services.StatesService;

@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StatesController {
	
	@Autowired
	private StatesService statesService;
	
	@RequestMapping(value = "/getStateBoundaries", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Bounds> getStateBoundaries() {
		return statesService.getStateBoundaries();
	}
	
	@RequestMapping(value = "/getNationalParks", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NationalPark> getNationalParks() {
		return statesService.getNationalParks();
	}
}
