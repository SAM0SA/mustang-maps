package edu.cs.stonybrook.map;

import edu.cs.stonybrook.errorAndLog.Error;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@MappedSuperclass
public abstract class Region {
	
	protected String id;
	protected Bounds bounds;
	protected List<Error> errors;
	protected int version;
	
	public Region() {
		
	}
	
	public Region(String id, Bounds bounds, List<Error> errors, int version) {
		this.id = id;
		this.bounds = bounds;
		this.errors = errors;
		this.version = version;
	}

	@Id
	@Column(name="REGION_ID")
	public String getRegionId() {
		return id;
	}
	
	public void setRegionId(String id) {
		this.id = id;
	}

	@Column(name="VERSION")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@OneToOne
	@JoinColumn(name="BOUNDS", referencedColumnName="BOUNDS_ID")
	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}
	
	@Transient
	abstract public List<Error> getErrors();
	
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	
}