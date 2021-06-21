package com.critter.chrono.entity;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private long id;

    @Nationalized
    private String name;

    @Nationalized
    private String phoneNumber;

    @Nationalized
    @Column(length = 500)
    private String notes;

    @OneToMany(mappedBy = "customer")
    private List<Pet> pets = new ArrayList<>();

}
