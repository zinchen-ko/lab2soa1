package com.soa.lab2soa.model.responses;

import com.soa.lab2soa.model.domain.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudyGroupPage {
    private List<StudyGroup> studyGroups;
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalCount;
}
