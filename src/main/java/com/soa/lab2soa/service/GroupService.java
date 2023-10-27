package com.soa.lab2soa.service;

import com.soa.lab2soa.dto.GroupRequest;
import com.soa.lab2soa.model.Coordinates;
import com.soa.lab2soa.model.Person;
import com.soa.lab2soa.model.StudyGroup;
import com.soa.lab2soa.repo.CoordinatesRepository;
import com.soa.lab2soa.repo.GroupRepository;
import com.soa.lab2soa.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void save(StudyGroup studyGroup, Person admin, Coordinates coordinates) {
        try {
            personRepository.save(admin);
            coordinatesRepository.save(coordinates);
            groupRepository.save(studyGroup);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public StudyGroup updateGroup(long id, GroupRequest groupRequest) {
        Optional<StudyGroup> studyGroupOptional = groupRepository.findById(id);
        StudyGroup studyGroup = studyGroupOptional.get();
        Person admin = studyGroup.getGroupAdmin();
        admin.setName(groupRequest.getGroupAdmin().getName());
        admin.setBirthday(groupRequest.getGroupAdmin().getBirthday());
        admin.setHeight(groupRequest.getGroupAdmin().getHeight());
        admin.setWeight(groupRequest.getGroupAdmin().getWeight());
        admin.setPassportID(groupRequest.getGroupAdmin().getPassportID());
        Coordinates coordinates = groupRequest.getCoordinates();
        coordinates.setX(groupRequest.getCoordinates().getX());
        coordinates.setY(groupRequest.getCoordinates().getY());
        studyGroup.setName(groupRequest.getName());
        studyGroup.setCoordinates(coordinates);
        studyGroup.setAverageMark(groupRequest.getAverageMark());
        studyGroup.setStudentsCount(groupRequest.getStudentsCount());
        studyGroup.setSemesterEnum(groupRequest.getSemesterEnum());
        studyGroup.setTransferredStudents(groupRequest.getTransferredStudents());
        studyGroup.setGroupAdmin(admin);
        coordinatesRepository.save(coordinates);
        personRepository.save(admin);
        groupRepository.save(studyGroup);
        return studyGroup;
    }

    public Optional<Long> deleteGroup(long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return Optional.of(id);
        }
        return Optional.empty();
    }

    public List<StudyGroup> getGroups() {
        System.out.println("Hello World!");
        return groupRepository.findAll();
    }

    public StudyGroup getGroupSmallestCoordinate() {
        List<Coordinates> coordinatesList = coordinatesRepository.findLessCoordinates();
        Optional<StudyGroup> studyGroup = groupRepository.findByCoordinates(coordinatesList.get(0));
        return studyGroup.get();
    }

    public Optional<Integer> deleteAllByAverageMark(int averageMark) {
        List<StudyGroup> studyGroupList = groupRepository.findAllByAverageMark(averageMark);
        groupRepository.deleteAllByAverageMark(averageMark);
        return Optional.of(studyGroupList.size());
    }

    public List<StudyGroup> getGroupsTransferredStudentsLess(int transferredStudents) {
        return groupRepository.findAllLessThanTransferredStudents(transferredStudents);
    }
}

