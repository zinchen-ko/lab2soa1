package com.soa.lab2soa.model.requests;

import com.soa.lab2soa.model.domain.Coordinates;
import com.soa.lab2soa.model.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupView {
    private String name;
    private Coordinates coordinates;
    private Long studentsCount;
    private Long transferredStudents;
    private Integer averageMark;
    private String semester;
    private Person groupAdmin;
}
