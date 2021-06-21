package com.critter.chrono.service;

import com.critter.chrono.exception.EmployeeNotFoundException;
import com.google.common.collect.Sets;
import com.critter.chrono.types.EmployeeSkill;
import com.critter.chrono.entity.Employee;
import com.critter.chrono.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        List<Employee> empDaysAvailable = employeeRepository.findByDaysAvailableIn(Sets.newHashSet(date.getDayOfWeek()));
        List<Employee> empMatchingSkills = empDaysAvailable.stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
        return empMatchingSkills;
    }

    public List<Employee> findEmployeesByIds(List<Long> employeeIds) {
        return employeeRepository.findByIdIn(employeeIds);
    }
}
