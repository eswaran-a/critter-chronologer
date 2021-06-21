package com.critter.chrono.controller;

import com.critter.chrono.exception.InvalidScheduleException;
import com.critter.chrono.service.PetService;
import com.critter.chrono.dto.ScheduleDTO;
import com.critter.chrono.service.CustomerService;
import com.critter.chrono.entity.Pet;
import com.critter.chrono.entity.Schedule;
import com.critter.chrono.service.ScheduleService;
import com.critter.chrono.entity.Employee;
import com.critter.chrono.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;
    private PetService petService;
    private EmployeeService employeeService;
    private CustomerService customerService;

    public ScheduleController(ScheduleService scheduleService, PetService petService, EmployeeService employeeService, CustomerService customerService) {
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        if(scheduleDTO.getPetIds().isEmpty() || scheduleDTO.getEmployeeIds().isEmpty()
        || scheduleDTO.getActivities().isEmpty())
            throw new InvalidScheduleException();

        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        return convertScheduleToScheduleDTO(scheduleService.save(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAllSchedules()
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findScheduleForPet(petId)
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findScheduleForEmployee(employeeId)
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.findScheduleForCustomer(customerId)
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setEmployeeIds(schedule.getEmployees()
                .stream()
                .map(Employee::getId)
                .collect(Collectors.toList()));

        scheduleDTO.setPetIds(schedule.getPets()
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList()));

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setCustomer(customerService.findOwnerByPets(scheduleDTO.getPetIds()));

        schedule.setPets(petService.findPetsByIds(scheduleDTO.getPetIds()));
        schedule.setEmployees(employeeService.findEmployeesByIds(scheduleDTO.getEmployeeIds()));

        return schedule;
    }
}
