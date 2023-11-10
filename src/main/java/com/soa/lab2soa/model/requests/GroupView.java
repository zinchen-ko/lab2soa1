package com.soa.lab2soa.model.requests;

import com.soa.lab2soa.model.domain.Coordinates;
import com.soa.lab2soa.model.domain.Person;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupView {
    @Size(min = 2, message = "Name should be min 2 symbols!")
    private String name;
    @NotNull
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
    @NotNull
    private String semester;
    @NotNull
    @Valid
    private Person groupAdmin;
}
