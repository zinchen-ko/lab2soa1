package com.soa.lab2soa.service;

import com.soa.lab2soa.common.Constants;
import com.soa.lab2soa.model.domain.Semester;
import com.soa.lab2soa.model.requests.GroupFilters;
import com.soa.lab2soa.model.requests.GroupView;
import com.soa.lab2soa.model.domain.Coordinates;
import com.soa.lab2soa.model.domain.Person;
import com.soa.lab2soa.model.domain.StudyGroup;
import com.soa.lab2soa.model.requests.SortParam;
import com.soa.lab2soa.model.responses.StudyGroupPage;
import com.soa.lab2soa.repo.CoordinatesRepository;
import com.soa.lab2soa.repo.GroupRepository;
import com.soa.lab2soa.repo.PersonRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final CoordinatesRepository coordinatesRepository;

    private final PersonRepository personRepository;

    public GroupService(GroupRepository groupRepository, CoordinatesRepository coordinatesRepository, PersonRepository personRepository) {
        this.coordinatesRepository = coordinatesRepository;
        this.groupRepository = groupRepository;
        this.personRepository = personRepository;
    }

    public Optional<StudyGroup> getGroupById(long id) {
        return groupRepository.findById(id);
    }

    public void save( StudyGroup studyGroup, Person admin, Coordinates coordinates) {
        try {
            personRepository.save(admin);
            coordinatesRepository.save(coordinates);
            groupRepository.save(studyGroup);
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public StudyGroup updateGroup(long id, GroupView groupView) {
        Optional<StudyGroup> studyGroupOptional = groupRepository.findById(id);
        StudyGroup studyGroup = studyGroupOptional.get();

        Person admin = studyGroup.getGroupAdmin();
        admin.setName(groupView.getGroupAdmin().getName());
        admin.setBirthday(groupView.getGroupAdmin().getBirthday());
        admin.setHeight(groupView.getGroupAdmin().getHeight());
        admin.setWeight(groupView.getGroupAdmin().getWeight());
        admin.setPassportID(groupView.getGroupAdmin().getPassportID());

        Coordinates coordinates = groupView.getCoordinates();
        coordinates.setX(groupView.getCoordinates().getX());
        coordinates.setY(groupView.getCoordinates().getY());

        studyGroup.setName(groupView.getName());
        studyGroup.setCoordinates(coordinates);
        studyGroup.setAverageMark(groupView.getAverageMark());
        studyGroup.setStudentsCount(groupView.getStudentsCount());
        studyGroup.setSemesterEnum(Semester.fromString(groupView.getSemester()));
        studyGroup.setTransferredStudents(groupView.getTransferredStudents());
        studyGroup.setGroupAdmin(admin);

        try{
            save(studyGroup,admin,coordinates);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException(e);
        }
        return studyGroup;
    }

    public Optional<Long> deleteGroup(long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return Optional.of(id);
        }
        return Optional.empty();
    }

    public StudyGroupPage getGroups(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return getGroupsPage(pageable);
    }

    public StudyGroupPage getGroups(int page, int pageSize, SortParam sortParam, Sort.Direction sortDir) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortDir, sortParam.getValue()));

        return getGroupsPage(pageable);
    }

    private StudyGroupPage getGroupsPage(Pageable pageable) {
        Page<StudyGroup> studyGroupPage = groupRepository.findAllPageable(pageable);

        return new StudyGroupPage(
                studyGroupPage.toList(),
                studyGroupPage.getNumber(),
                studyGroupPage.getSize(),
                studyGroupPage.getTotalPages(),
                studyGroupPage.getTotalElements()
        );
    }

    public StudyGroupPage getGroupsFiltered(GroupFilters filters, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return getGroupsPageFiltered(filters, pageable);
    }

    public StudyGroupPage getGroupsFiltered(GroupFilters filters, int page, int pageSize, SortParam sortParam, Sort.Direction sortDir) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortDir, sortParam.getValue()));

        return getGroupsPageFiltered(filters, pageable);
    }

    private StudyGroupPage getGroupsPageFiltered(GroupFilters filters, Pageable pageable) {
        StudyGroup studyGroup = StudyGroup.fromFilters(filters);
        Date creationDateFilter = filters.getCreationDate();
        studyGroup.setCreationDate(null);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        Example example = Example.of(studyGroup, matcher);

        Page<StudyGroup> groupsFilteredPage;

        if (creationDateFilter != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(creationDateFilter);

            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date startTime =  cal.getTime();

            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date endTime =  cal.getTime();

            List<StudyGroup> groupFilteredByOther = groupRepository.findAll(example);

            Set<Long> groupIdsFilteredByOther = groupFilteredByOther
                    .stream()
                    .map(StudyGroup::getId)
                    .collect(Collectors.toSet());

            List<Long> groupIdsFiltered = groupRepository.findAllByCreationDateBetween(startTime, endTime)
                    .stream()
                    .map(StudyGroup::getId)
                    .filter(groupIdsFilteredByOther::contains)
                    .toList();

            groupsFilteredPage = groupRepository.findAllByIds(pageable, groupIdsFiltered);
        }
        else {
            groupsFilteredPage = groupRepository.findAll(example, pageable);
        }

        return new StudyGroupPage(
                groupsFilteredPage.toList(),
                groupsFilteredPage.getNumber(),
                groupsFilteredPage.getSize(),
                groupsFilteredPage.getTotalPages(),
                groupsFilteredPage.getTotalElements()
        );
    }

    public StudyGroup getGroupSmallestCoordinate() {
        List<Coordinates> coordinatesList = coordinatesRepository.findLessCoordinates();
        Optional<StudyGroup> studyGroup = groupRepository.findByCoordinates(coordinatesList.get(0));
        return studyGroup.get();
    }

    public Integer deleteAllByAverageMark(int averageMark) {
        List<StudyGroup> studyGroupList = groupRepository.findAllByAverageMark(averageMark);
        groupRepository.deleteAllByAverageMark(averageMark);
        return studyGroupList.size();
    }

    public List<StudyGroup> getGroupsTransferredStudentsLess(int transferredStudents) {
        return groupRepository.findAllLessThanTransferredStudents(transferredStudents);
    }
}

