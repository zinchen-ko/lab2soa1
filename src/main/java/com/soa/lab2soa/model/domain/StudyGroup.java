package com.soa.lab2soa.model.domain;

import com.soa.lab2soa.model.domain.Coordinates;
import com.soa.lab2soa.model.domain.Person;
import com.soa.lab2soa.model.domain.Semester;
import com.soa.lab2soa.model.requests.GroupFilters;
import com.soa.lab2soa.model.requests.GroupView;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
    @Size(min = 2, message = "Name should be min 2 symbols!")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    @PositiveOrZero
    private Long studentsCount; //Значение поля должно быть больше 0
    @NotNull
    @PositiveOrZero
    private Long transferredStudents; //Значение поля должно быть больше 0
    @NotNull
    @PositiveOrZero
    private Integer averageMark; //Значение поля должно быть больше 0
    @NotNull
    private Semester semesterEnum; //Поле может быть null
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "person_id")
    private Person groupAdmin; //Поле не может быть null

    public StudyGroup(String name, Coordinates coordinates, Date creationDate, Long studentsCount, Long transferredStudents, Integer averageMark, Semester semesterEnum, Person groupAdmin) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.transferredStudents = transferredStudents;
        this.averageMark = averageMark;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public static StudyGroup fromView(GroupView groupView) {
        return new StudyGroup(
                groupView.getName(),
                groupView.getCoordinates(),
                null,
                groupView.getStudentsCount(),
                groupView.getTransferredStudents(),
                groupView.getAverageMark(),
                Semester.fromString(groupView.getSemester()),
                groupView.getGroupAdmin()
        );
    }

    public static StudyGroup fromFilters(GroupFilters filters) {
        StudyGroup studyGroup = new StudyGroup();

        Person groupAdmin = new Person();
        groupAdmin.setName(filters.getAdminName());
        studyGroup.setGroupAdmin(groupAdmin);

        Coordinates coordinates = new Coordinates(filters.getCoordinateX(), filters.getCoordinateY());
        studyGroup.setCoordinates(coordinates);

        studyGroup.setId(filters.getId());
        studyGroup.setName(filters.getGroupName());
        studyGroup.setStudentsCount(filters.getStudentsCount());
        studyGroup.setTransferredStudents(filters.getTransferredStudents());
        studyGroup.setAverageMark(filters.getAverageMark());
        studyGroup.setCreationDate(filters.getCreationDate());
        studyGroup.setCreationDate(filters.getCreationDate());

        if (filters.getSemester() != null) {
            studyGroup.setSemesterEnum(Semester.fromString(filters.getSemester()));
        }

        return studyGroup;
    }
}
