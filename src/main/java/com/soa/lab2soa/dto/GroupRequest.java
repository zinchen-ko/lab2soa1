package com.soa.lab2soa.dto;

import com.soa.lab2soa.model.Coordinates;
import com.soa.lab2soa.model.Person;
import com.soa.lab2soa.model.Semester;
import lombok.Data;

@Data
public class GroupRequest {
    private String name;
    private Coordinates coordinates;
    private java.time.ZonedDateTime creationDate;
    private long studentsCount;
    private long transferredStudents;
    private int averageMark;
    private Semester semesterEnum;
    private Person groupAdmin;
}
