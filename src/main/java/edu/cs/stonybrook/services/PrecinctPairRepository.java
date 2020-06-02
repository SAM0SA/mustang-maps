package edu.cs.stonybrook.services;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.cs.stonybrook.errorAndLog.PrecinctPair;

public interface PrecinctPairRepository extends JpaRepository<PrecinctPair, Integer> {

}
