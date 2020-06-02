package edu.cs.stonybrook.map;


import edu.cs.stonybrook.errorAndLog.Error;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "regionId")
@JsonIdentityReference(alwaysAsId = false)
public class Precinct extends Region {
	
	private String originalName;
	private List<Precinct> neighbors;
	private State state;
	private boolean displayOnMap;
	private boolean isGhost;

	public Precinct() {
		super();
	}
	
	public Precinct(String id, Bounds bounds, List<Error> errors, String originalName, State state, List<Precinct> neighbors, 
		boolean isGhost, boolean displayOnMap, int version
		) {
		super(id, bounds, errors, version);
		this.originalName = originalName;
		this.neighbors = neighbors;
		this.isGhost = isGhost;
		this.displayOnMap = displayOnMap;
		this.setState(state);
	}

	@Column(name="ORIGINAL_NAME")
	public String getOriginalName() {
		return originalName;
	}
	
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	@JoinTable(name = "NEIGHBORS", joinColumns = {
    @JoinColumn(name = "P1_ID", referencedColumnName = "REGION_ID", nullable = false)}, inverseJoinColumns = { 
            @JoinColumn(name = "P2_ID", referencedColumnName = "REGION_ID", nullable=false) })
    @ManyToMany
	public List<Precinct> getNeighbors() {
		return neighbors;
	}
	
	public void setNeighbors(List<Precinct> neighbors) {
		this.neighbors = neighbors;
	}

	@Column(name="IS_GHOST")
	public boolean isGhost() {
		return isGhost;
	}
	
	public void setGhost(boolean isGhost) {
		this.isGhost = isGhost;
	}
	
	@OneToMany(mappedBy="precinct")
	public List<Error> getErrors() {
		return super.errors;
	}

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="STATE")
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isDisplayOnMap() {
		return displayOnMap;
	}

	public void setDisplayOnMap(boolean displayOnMap) {
		this.displayOnMap = displayOnMap;
	}
	
	public Precinct getCopyAndUpdateVersion(boolean displayOnMap) {
		Precinct newPrecinct = new Precinct(this.id + "V" + version, 
				this.bounds, this.errors, this.originalName, this.state, this.neighbors, this.isGhost, displayOnMap, version);
		version++;
		return newPrecinct;
	}
	
	public void addNeighbor(Precinct neighbor) {
		neighbors.add(neighbor);
	}
	
	public void removeNeighbor(Precinct neighbor) {
		neighbors.remove(neighbor);
	}


}
