package edu.cs.stonybrook.errorAndLog;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.*;

import edu.cs.stonybrook.map.Bounds;
import edu.cs.stonybrook.map.Precinct;
import edu.cs.stonybrook.map.State;



@Entity
@DiscriminatorColumn(name="ERROR_NAME", discriminatorType = DiscriminatorType.STRING)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
//property = "errorId")
public abstract class Error {
	
	
	protected int errorId;
	protected State state;
	protected ErrorType type;
	protected Precinct precinct;
	protected String description;
	protected boolean resolved;
	protected Bounds errorLocation;
	
	
	public Error() {
		
	}

	public Error(
		State state,
		Precinct precinct,
		String description, 
		boolean resolved,
		Bounds errorLocation,
		ErrorType type
		) {
		this.state = state;
		this.precinct = precinct;
		this.description = description;
		this.resolved = resolved;
		this.errorLocation = errorLocation;
		this.type = type;
	}

	@Column(name="ERROR_TYPE")
	public ErrorType getType() {
		return type;
	}

	public void setType(ErrorType type) {
		this.type = type;
	}

	@Column(name="RESOLVED")
	public boolean isResolved() {
		return resolved;
	}
	
	abstract public void setResolved(boolean resolved);

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToOne
	@JoinColumn(name="LOCATION", referencedColumnName="BOUNDS_ID")
	public Bounds getErrorLocation() {
		return errorLocation;
	}

	public void setErrorLocation(Bounds errorLocation) {
		this.errorLocation = errorLocation;
	}

	@Id
	@Column(name="ERROR_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
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
	
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne
	@JoinColumn(name="PRECINCT")
	public Precinct getPrecinct() {
		return precinct;
	}

	public void setPrecinct(Precinct precinct) {
		this.precinct = precinct;
	}
}


