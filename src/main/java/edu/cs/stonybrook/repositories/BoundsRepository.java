package edu.cs.stonybrook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.cs.stonybrook.map.Bounds;

public interface BoundsRepository extends JpaRepository<Bounds, Integer> {

}
