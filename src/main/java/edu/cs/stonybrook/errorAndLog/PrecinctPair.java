package edu.cs.stonybrook.errorAndLog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import edu.cs.stonybrook.map.Precinct;

@Entity
public class PrecinctPair{
	int id;
	Precinct oldPrecinct;
	Precinct modifiedPrecinct;
	LogEntry entry;
	
	PrecinctPair(){
		
	}
	
	public PrecinctPair(Precinct oldPrecinct, Precinct modifiedPrecinct, LogEntry entry){
		this.oldPrecinct = oldPrecinct;
		this.modifiedPrecinct = modifiedPrecinct;
		this.entry = entry;
	}
	
	@Id
	@Column(name="PAIR_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name="OLD_PRECINCT", referencedColumnName="REGION_ID")
	public Precinct getOldPrecinct() {
		return oldPrecinct;
	}

	public void setOldPrecinct(Precinct oldPrecinct) {
		this.oldPrecinct = oldPrecinct;
	}
	
	@OneToOne
	@JoinColumn(name="MODIFIED_PRECINCT", referencedColumnName="REGION_ID")
	public Precinct getModifiedPrecinct() {
		return modifiedPrecinct;
	}

	public void setModifiedPrecinct(Precinct modifiedPrecinct) {
		this.modifiedPrecinct = modifiedPrecinct;
	}

	@ManyToOne
	@JoinColumn(name="LOG_ENTRY")
	public LogEntry getEntry() {
		return entry;
	}

	public void setEntry(LogEntry entry) {
		this.entry = entry;
	}
	
	
}
