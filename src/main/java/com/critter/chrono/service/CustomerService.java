package com.critter.chrono.service;

import com.critter.chrono.exception.CustomerNotFoundException;
import com.critter.chrono.repository.CustomerRepository;
import com.critter.chrono.entity.Customer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findOwnerByPetId(long petId) {
        return customerRepository.findByPetsId(petId).orElseThrow(CustomerNotFoundException::new);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findOwnerById(long ownerId) {
        return customerRepository.findById(ownerId).orElseThrow(CustomerNotFoundException::new);
    }

    public List<Customer> findOwnerByPets(List<Long> petIds) {
        return customerRepository.findByPetsIdIn(petIds);
    }
}
