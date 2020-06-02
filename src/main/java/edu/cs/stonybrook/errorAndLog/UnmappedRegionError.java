package edu.cs.stonybrook.errorAndLog;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.cs.stonybrook.map.Bounds;
import edu.cs.stonybrook.map.Precinct;
import edu.cs.stonybrook.map.State;

@Entity
@DiscriminatorValue("Unmapped")
public class UnmappedRegionError extends Error {
	
	public UnmappedRegionError() {
		
	}
	
	public UnmappedRegionError( State state, Precinct precinct, String description, boolean resolved, 
			Bounds errorLocation, ErrorType type) {
		super(state, precinct, description, resolved, errorLocation, type);
	}

	@Override
	public void setResolved(boolean resolved) {
		this.resolved = true;
	}
}