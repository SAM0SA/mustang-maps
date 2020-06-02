package edu.cs.stonybrook.errorAndLog;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import edu.cs.stonybrook.map.State;

@Entity
public class LogEntry {
	
	private int entryId;
	private State state;
	private List<PrecinctPair> involvedPrecincts;
	private Error error;
	private LocalDateTime creationDate;
	private String user;
	
	public LogEntry() {
		
	}
	
	public LogEntry(State state, Error error, LocalDateTime date, String user, List<PrecinctPair> involvedPrecincts) {
		this.state = state;
		this.error = error;
		this.creationDate = date;
		this.user = user;
		this.involvedPrecincts = involvedPrecincts;
	}
	
	@Id
	@Column(name="ENTRY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getEntryId() {
		return entryId;
	}
	
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	
	@ManyToOne
	@JoinColumn(name="STATE", referencedColumnName="REGION_ID")
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	@OneToOne
    @JoinColumn(name="ERROR_ID")
	public Error getError() {
		return this.error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Column(name="USER")
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	@Column(name="CREATION_DATE")
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@OneToMany(mappedBy="entry")
	public List<PrecinctPair> getInvolvedPrecincts() {
		return involvedPrecincts;
	}
	
	public void setInvolvedPrecincts(List<PrecinctPair> involvedPrecincts) {
		this.involvedPrecincts = involvedPrecincts;
	}
	
}
