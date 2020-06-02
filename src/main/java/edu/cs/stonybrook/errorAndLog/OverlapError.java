package edu.cs.stonybrook.errorAndLog;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import edu.cs.stonybrook.map.Bounds;
import edu.cs.stonybrook.map.Precinct;
import edu.cs.stonybrook.map.State;

@Entity
@DiscriminatorValue("Overlap")
public class OverlapError extends Error {

	private Precinct secondOverlappingPrecinct;
	
	public OverlapError() {
		
	}
	public OverlapError(State state, Precinct precinct, String description, boolean resolved, Bounds errorLocation, 
			Precinct secondOverlappingPrecinct, ErrorType type) {
		super(state, precinct, description, resolved, errorLocation, type);
		this.secondOverlappingPrecinct = secondOverlappingPrecinct;
	}

	public void setSecondOverlappingPrecinct(Precinct secondOverlappingPrecinct) {
		this.secondOverlappingPrecinct = secondOverlappingPrecinct;
	}
	
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne
    @JoinColumn(name="PRECINCT2", referencedColumnName="REGION_ID")
	public Precinct getSecondOverlappingPrecinct() {
		return secondOverlappingPrecinct;
	}

	@Override
	public void setResolved(boolean resolved) {
		this.resolved = true;
	}

}
