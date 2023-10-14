package com.soa.lab2soa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private java.time.ZonedDateTime birthday;
    private Float weight;
    private Float height;
    private String passportID;

    public Person(String name, ZonedDateTime birthday, Float weight, Float height, String passportID) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.passportID = passportID;
    }
}
