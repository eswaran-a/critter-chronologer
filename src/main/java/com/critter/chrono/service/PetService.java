package com.critter.chrono.service;
import com.critter.chrono.entity.Pet;
import com.critter.chrono.exception.PetNotFoundException;
import com.critter.chrono.repository.PetRepository;
import com.critter.chrono.entity.Customer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {
    private PetRepository petRepository;
    private CustomerService customerService;

    public PetService(PetRepository petRepository, CustomerService customerService) {
        this.petRepository = petRepository;
        this.customerService = customerService;
    }

    public List<Pet> findPetByOwnerId(Long ownerId) {
        List<Pet> petList = petRepository.findAllByCustomerId(ownerId);

        if(petList.isEmpty())
            throw new PetNotFoundException();

        return petList;
    }

    public Pet findPetById(Long petId) {
        return petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    public Pet save(Pet pet, Long ownerId) {
        Customer customer = customerService.findOwnerById(ownerId);
        pet.setCustomer(customer);
        Pet savedPet = petRepository.save(pet);

        customer.getPets().add(pet);
        customerService.save(customer);

        return savedPet;
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> findPetsByIds(List<Long> petIds) {
        return petRepository.findByIdIn(petIds);
    }
}
