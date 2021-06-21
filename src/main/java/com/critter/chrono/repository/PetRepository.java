package com.critter.chrono.repository;

import com.critter.chrono.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    public List<Pet> findAllByCustomerId(Long id);
    public List<Pet> findByIdIn(List<Long> petIds);
}
