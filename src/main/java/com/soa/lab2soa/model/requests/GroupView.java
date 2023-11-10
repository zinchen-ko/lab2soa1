package com.soa.lab2soa.model.requests;

import com.soa.lab2soa.model.domain.Coordinates;
import com.soa.lab2soa.model.domain.Person;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@AllArgsConstructor
public class GroupView {
    @Size(min = 2, message = "Name should be min 2 symbols!")
    private String name;
    private Coordinates coordinates;
    @NotNull
    @PositiveOrZero
    private Long studentsCount;
    @NotNull
    @PositiveOrZero
    private Long transferredStudents;
    @NotNull
    @PositiveOrZero
    private Integer averageMark;
    private String semester;
    private Person groupAdmin;
}
