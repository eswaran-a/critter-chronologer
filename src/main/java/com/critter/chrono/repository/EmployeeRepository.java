package com.critter.chrono.repository;

import com.critter.chrono.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public List<Employee> findByIdIn(List<Long> employeeIds);
    List<Employee> findByDaysAvailableIn(Set<DayOfWeek> dayOfWeek);
}
