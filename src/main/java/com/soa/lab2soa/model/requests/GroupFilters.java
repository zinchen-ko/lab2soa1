package com.soa.lab2soa.model.requests;

import com.soa.lab2soa.model.domain.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GroupFilters {
    private Long id;
    private String groupName;
    private Long coordinateX;
    private Float coordinateY;
    private Long studentsCount;
    private Long transferredStudents;
    private Integer averageMark;
    private String adminName;
    private String semester;
    private Date creationDate;
}