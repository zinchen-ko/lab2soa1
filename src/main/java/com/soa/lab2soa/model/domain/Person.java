package com.soa.lab2soa.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min = 2, message = "Name should be min 2 symbols!")
    private String name;
    @NotNull
    private Date birthday;
    @NotNull
    @PositiveOrZero
    private Float weight;
    @NotNull
    @PositiveOrZero
    private Float height;
    @NotNull
    @Size(min = 10, max = 10, message = "PassportId should be 10 symbols!")
    private String passportID;

    public Person(String name, Date birthday, Float weight, Float height, String passportID) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.passportID = passportID;
    }
}
