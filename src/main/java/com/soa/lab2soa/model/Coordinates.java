package com.soa.lab2soa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long x; //Поле не может быть null
    private Float y; //Поле не может быть null

    public Coordinates(long x, float y) {
        this.x = x;
        this.y = y;
    }
}
