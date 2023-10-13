package com.soa.lab2soa.service;

import com.soa.lab2soa.dto.GroupRequest;
import com.soa.lab2soa.model.Coordinates;
import com.soa.lab2soa.model.StudyGroup;
import com.soa.lab2soa.repo.CoordinatesRepository;
import com.soa.lab2soa.repo.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final CoordinatesRepository coordinatesRepository;

    public GroupService(GroupRepository groupRepository, CoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
        this.groupRepository = groupRepository;
    }

    public Optional<StudyGroup> getGroupById(long id) {
        return groupRepository.findById(id);
    }

    public void save(StudyGroup studyGroup) {
        try {
            groupRepository.save(studyGroup);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public StudyGroup updateGroup(long id, GroupRequest groupRequest) {
        Optional<StudyGroup> studyGroupOptional = groupRepository.findById(id);
        StudyGroup studyGroup = studyGroupOptional.get();
        studyGroup.setName(groupRequest.getName());
        studyGroup.setCoordinates(groupRequest.getCoordinates());
        studyGroup.setAverageMark(groupRequest.getAverageMark());
        studyGroup.setStudentsCount(groupRequest.getStudentsCount());
        studyGroup.setSemesterEnum(groupRequest.getSemesterEnum());
        studyGroup.setTransferredStudents(groupRequest.getTransferredStudents());
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

