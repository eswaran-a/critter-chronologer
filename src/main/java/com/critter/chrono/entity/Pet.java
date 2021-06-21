package com.critter.chrono.entity;

import com.critter.chrono.types.PetType;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Pet {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    @Nationalized
    private String name;

    @ManyToOne
    private Customer customer;

    private LocalDate birthDate;

    @Nationalized
    @Column(length = 500)
    private String notes;

}
