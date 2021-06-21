package com.critter.chrono.repository;

import com.critter.chrono.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findByPetsId(Long petId);
    public List<Customer> findByPetsIdIn(List<Long> petIds);
}
