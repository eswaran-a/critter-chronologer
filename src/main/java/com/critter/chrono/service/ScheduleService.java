package com.critter.chrono.service;

import com.critter.chrono.repository.ScheduleRepository;
import com.critter.chrono.repository.CustomerRepository;
import com.critter.chrono.exception.CustomerNotFoundException;
import com.critter.chrono.entity.Schedule;
import com.critter.chrono.entity.Customer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    private CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleForPet(long petId) {
        return scheduleRepository.findByPetsId(petId);
    }

    public List<Schedule> findScheduleForEmployee(long employeeId) {
        return scheduleRepository.findByEmployeesId(employeeId);
    }

    public List<Schedule> findScheduleForCustomer(long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        List<Schedule> customerSchedule = customer.getPets().stream().map(pet -> scheduleRepository.findByPetsId(pet.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return customerSchedule;
    }
}
