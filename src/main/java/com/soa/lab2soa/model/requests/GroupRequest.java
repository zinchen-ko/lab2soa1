package com.soa.lab2soa.model.requests;

import com.soa.lab2soa.model.domain.Coordinates;
import com.soa.lab2soa.model.domain.Person;
import com.soa.lab2soa.model.domain.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupRequest {
    private String name;
    private Coordinates coordinates;
    private long studentsCount;
    private long transferredStudents;
    private int averageMark;
    private Semester semesterEnum;
    private Person groupAdmin;
}
