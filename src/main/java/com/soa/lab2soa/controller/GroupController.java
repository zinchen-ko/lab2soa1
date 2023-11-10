package com.soa.lab2soa.controller;


import com.soa.lab2soa.common.Constants;
import com.soa.lab2soa.model.domain.Semester;
import com.soa.lab2soa.model.requests.GroupFilters;
import com.soa.lab2soa.model.requests.GroupView;
import com.soa.lab2soa.model.domain.Coordinates;
import com.soa.lab2soa.model.domain.Person;
import com.soa.lab2soa.model.domain.StudyGroup;
import com.soa.lab2soa.model.requests.SortParam;
import com.soa.lab2soa.model.responses.StudyGroupPage;
import com.soa.lab2soa.service.GroupService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/groups")
@CrossOrigin
@Validated
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public StudyGroup getGroup(@PathVariable long id) {
        try {
            Optional<StudyGroup> studyGroup = groupService.getGroupById(id);
            return studyGroup.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping()
    public StudyGroup addGroup(@Valid @RequestBody GroupView groupView) {
        Date creationDate = new Date();
        Coordinates coordinates = new Coordinates(groupView.getCoordinates().getX(), groupView.getCoordinates().getY());
        Person groupAdmin = new Person(
                groupView.getGroupAdmin().getName(),
                groupView.getGroupAdmin().getBirthday(),
                groupView.getGroupAdmin().getWeight(),
                groupView.getGroupAdmin().getHeight(),
                groupView.getGroupAdmin().getPassportID()
        );

        StudyGroup studyGroup = new StudyGroup(
                groupView.getName(),
                coordinates,
                creationDate,
                groupView.getStudentsCount(),
                groupView.getTransferredStudents(),
                groupView.getAverageMark(),
                Semester.fromString(groupView.getSemester()),
                groupAdmin
        );
        groupService.save(studyGroup, groupAdmin, coordinates);
        return studyGroup;
    }

    @PutMapping("/{id}")
    public StudyGroup updateGroup(@PathVariable long id, @Valid @RequestBody GroupView groupView) {
        StudyGroup studyGroup = groupService.updateGroup(id, groupView);
        if (studyGroup == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return studyGroup;
    }

    @DeleteMapping("/{id}")
    public Long deleteGroup(@PathVariable long id) {
        try {
            Optional<Long> groupId = groupService.deleteGroup(id);
            return groupId.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping()
    public StudyGroupPage getGroups(
            @RequestParam(value = "sortBy", required = false) Optional<String> sort,
            @RequestParam(value = "sortDir", required = false) Optional<String> sortOrder,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        try {
            if (sort.isPresent() && sortOrder.isPresent()) {
                SortParam sortParam = SortParam.fromString(sort.get());
                Sort.Direction sortDir = Sort.Direction.fromString(sortOrder.get());
                return groupService.getGroups(
                        page,
                        pageSize,
                        sortParam,
                        sortDir
                );
            }
            return groupService.getGroups(page, pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/filtered")
    public StudyGroupPage getFilteredGroups(
            @RequestBody GroupFilters filters,
            @RequestParam(value = "sortBy", required = false) Optional<String> sort,
            @RequestParam(value = "sortDir", required = false) Optional<String> sortOrder,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        if (sort.isPresent() && sortOrder.isPresent()) {
            SortParam sortParam = SortParam.fromString(sort.get());
            Sort.Direction sortDir = Sort.Direction.fromString(sortOrder.get());
            return groupService.getGroupsFiltered(
                    filters,
                    page,
                    pageSize,
                    sortParam,
                    sortDir
            );
        }
        return groupService.getGroupsFiltered(filters, page, pageSize);
    }

    @PostMapping("/smallest-coordinates")
    public StudyGroup getGroupSmallestCoordinate() {
         try {
             return groupService.getGroupSmallestCoordinate();
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }

    @PostMapping("delete-all-by-average-mark/{averageMark}")
    public Integer deleteAllByAverageMark(@PathVariable int averageMark) {
        try {
            return groupService.deleteAllByAverageMark(averageMark);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("transferred-students-less-than/{transferredStudents}")
    public List<StudyGroup> getGroupsTransferredStudentsLess(@PathVariable int transferredStudents) {
         try {
             return groupService.getGroupsTransferredStudentsLess(transferredStudents);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }
}
