package com.soa.lab2soa.controller;


import com.soa.lab2soa.dto.GroupRequest;
import com.soa.lab2soa.model.StudyGroup;
import com.soa.lab2soa.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public StudyGroup getGroup(@PathVariable long id) {
        try {
            Optional<StudyGroup> studyGroup = groupService.getGroupById(id);
            return studyGroup.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping()
    public StudyGroup addGroup(@RequestBody GroupRequest groupRequest) {
        try {
            ZonedDateTime creationDate = ZonedDateTime.now();
            StudyGroup studyGroup = new StudyGroup(
                    groupRequest.getName(),
                    groupRequest.getCoordinates(),
                    creationDate,
                    groupRequest.getStudentsCount(),
                    groupRequest.getTransferredStudents(),
                    groupRequest.getAverageMark(),
                    groupRequest.getSemesterEnum(),
                    groupRequest.getGroupAdmin()
            );
            groupService.save(studyGroup);
            return studyGroup;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public StudyGroup updateGroup(@PathVariable long id, @RequestBody GroupRequest groupRequest) {
        try {
            StudyGroup studyGroup = groupService.updateGroup(id, groupRequest);
            if (studyGroup == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return studyGroup;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public Long deleteGroup(@PathVariable long id) {
        try {
            Optional<Long> groupId = groupService.deleteGroup(id);
            return groupId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping()
    public List<StudyGroup> getGroups() {
        try {
            return groupService.getGroups();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping()
    public StudyGroup getGroupSmallestCoordinate() {
         try {
             return groupService.getGroupSmallestCoordinate();
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }

    @PostMapping("/{averageMark}")
    public Integer deleteAllByAverageMark(@PathVariable int averageMark) {
        try {
            Optional<Integer> numberOfDeletedGroups = groupService.deleteAllByAverageMark(averageMark);
            return numberOfDeletedGroups.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{transferredStudents}")
    public List<StudyGroup> getGroupsTransferredStudentsLess(@PathVariable int transferredStudents) {
         try {
             return groupService.getGroupsTransferredStudentsLess(transferredStudents);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }
}
