package edu.cs.stonybrook.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.cs.stonybrook.errorAndLog.Error;

public interface ErrorRepository extends JpaRepository<Error, Integer> {
	
	@Query(value="SELECT * FROM error WHERE error_type = :type AND STATE = :stateId AND resolved = 0", nativeQuery = true)
	List<Error> getErrorsByTypeForState(@Param("type") int type, @Param("stateId") String stateId);
	
//	@Query(value="SELECT * FROM error WHERE error_type = :type AND PRECINCT = :precinctId AND resolved = 0", nativeQuery = true)
//	List<Error> getErrorsByTypeForPrecinct(@Param("type") int type, @Param("precinctId") String precinctId);
}
