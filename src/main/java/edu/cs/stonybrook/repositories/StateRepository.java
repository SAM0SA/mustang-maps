package edu.cs.stonybrook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.cs.stonybrook.map.State;

public interface StateRepository extends JpaRepository<State, String> {

}
