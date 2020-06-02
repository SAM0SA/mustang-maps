package edu.cs.stonybrook.map;

import edu.cs.stonybrook.errorAndLog.Error;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class State extends Region{
	
	List<Precinct> precincts;
	
	public State () {
		super();
	}
	
	public State(String id, Bounds bounds, List<Error> errors, List<Precinct> precincts, int version) {
		super(id, bounds, errors, version);
		this.precincts = precincts;
	}

	@JsonManagedReference
	@OneToMany(mappedBy="state")
	public List<Error> getErrors() {
		return super.errors;
	}

	@JsonManagedReference
	@OneToMany(mappedBy="state")
	public List<Precinct> getPrecincts() {
		return precincts;
	}

	public void setPrecincts(List<Precinct> precincts) {
		this.precincts = precincts;
	}
}
