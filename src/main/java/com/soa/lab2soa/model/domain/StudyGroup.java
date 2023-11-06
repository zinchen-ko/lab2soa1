package com.soa.lab2soa.model.domain;

import com.soa.lab2soa.model.domain.Coordinates;
import com.soa.lab2soa.model.domain.Person;
import com.soa.lab2soa.model.domain.Semester;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "studyGroup")
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    private long studentsCount; //Значение поля должно быть больше 0
    @NotNull
    private long transferredStudents; //Значение поля должно быть больше 0
    @NotNull
    private int averageMark; //Значение поля должно быть больше 0
    @NotNull
    private Semester semesterEnum; //Поле может быть null
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "person_id")
    private Person groupAdmin; //Поле не может быть null

    public StudyGroup(String name, Coordinates coordinates, Date creationDate, long studentsCount, long transferredStudents, int averageMark, Semester semesterEnum, Person groupAdmin) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.transferredStudents = transferredStudents;
        this.averageMark = averageMark;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }
}
