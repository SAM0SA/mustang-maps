package edu.cs.stonybrook.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.cs.stonybrook.map.Precinct;

public interface PrecinctRepository extends JpaRepository<Precinct, String> {
	@Query(value="SELECT * FROM precinct WHERE display_on_map = 1 AND state = :rs", nativeQuery = true)
	List<Precinct> getLatestPrecinct(@Param("rs") String requestedState);
}
