package edu.cs.stonybrook.map;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import edu.cs.stonybrook.errorAndLog.Error;

@Entity
public class NationalPark extends Region {
	
	public NationalPark() {
	}

	public NationalPark(String id, Bounds bounds, List<Error> errors, int version) {
		super(id, bounds, errors, version);
	}

	@Transient
	@Override
	public List<Error> getErrors() {
		return null;
	}

}
