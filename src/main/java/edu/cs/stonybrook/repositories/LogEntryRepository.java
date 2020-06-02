package edu.cs.stonybrook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.cs.stonybrook.errorAndLog.LogEntry;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, String> {
	
}
