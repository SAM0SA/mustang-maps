package edu.cs.stonybrook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.cs.stonybrook.map.NationalPark;

public interface NationalParksRepository extends JpaRepository<NationalPark, String> {

}
