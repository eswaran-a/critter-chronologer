package com.critter.chrono.entity;

import com.critter.chrono.types.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private long id;

    private LocalDate date;

    @ManyToMany
    private List<Employee> employees = new ArrayList<>();
    @ManyToMany
    private List<Pet> pets = new ArrayList<>();

    @ManyToMany
    private List<Customer> customer = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;
}
