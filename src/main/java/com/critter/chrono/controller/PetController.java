package com.critter.chrono.controller;

import com.critter.chrono.exception.CustomerNotFoundException;
import com.critter.chrono.service.PetService;
import com.critter.chrono.service.CustomerService;
import com.critter.chrono.dto.PetDTO;
import com.critter.chrono.entity.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;
    private CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        if(petDTO.getOwnerId()==0L)
            throw new CustomerNotFoundException("ownerId field is missing in request body.");
        Pet pet = convertPetDTOToPet(petDTO);
        return convertPetToPetDTO(petService.save(pet, petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(petService.findPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.findAllPets()
                .stream()
                .map(pet -> convertPetToPetDTO(pet))
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.findPetByOwnerId(ownerId)
                .stream()
                .map(pet -> convertPetToPetDTO(pet))
                .collect(Collectors.toList());
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
