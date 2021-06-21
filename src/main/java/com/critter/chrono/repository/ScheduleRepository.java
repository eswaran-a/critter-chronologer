package com.critter.chrono.repository;
import com.critter.chrono.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPetsId(long petId);
    List<Schedule> findByEmployeesId(long employeeId);
}
