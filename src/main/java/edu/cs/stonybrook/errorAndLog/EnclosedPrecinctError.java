package edu.cs.stonybrook.errorAndLog;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.cs.stonybrook.map.Bounds;
import edu.cs.stonybrook.map.Precinct;
import edu.cs.stonybrook.map.State;


@Entity
@DiscriminatorValue("Enclosed")
public class EnclosedPrecinctError extends Error {

	private Precinct enclosedPrecinct;
	
	public EnclosedPrecinctError() {
		
	}
	
	public EnclosedPrecinctError(State state, Precinct precinct, String description, boolean resolved,Bounds errorLocation,
			Precinct enclosedPrecinct, ErrorType type) {
		super(state, precinct, description, resolved, errorLocation, type);
		this.enclosedPrecinct = enclosedPrecinct;
	}

	public void setEnclosedPrecinct(Precinct enclosedPrecinct) {
		this.enclosedPrecinct = enclosedPrecinct;
	}
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne
    @JoinColumn(name="PRECINCT2", referencedColumnName="REGION_ID")
	public Precinct getEnclosedPrecinct() {
		return enclosedPrecinct;
	}

	@Override
	public void setResolved(boolean resolved) {
		this.resolved = true;
	}

}
