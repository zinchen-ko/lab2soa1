package com.soa.lab2soa.repo;

import com.soa.lab2soa.model.domain.Coordinates;
import com.soa.lab2soa.model.domain.StudyGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<StudyGroup, Long> {
    Optional<StudyGroup> findById(long id);

    @Transactional
    void deleteAllByAverageMark(int averageMark);

    List<StudyGroup> findAllByAverageMark(int averageMark);

    @Query(
            "SELECT c FROM StudyGroup c WHERE c.transferredStudents < :transferred"
    )
    Page<StudyGroup> findAllLessThanTransferredStudents(Pageable pageable, @Param("transferred") int transferred);

    Optional<StudyGroup> findByCoordinates(Coordinates coordinates);

    @Query("SELECT c FROM StudyGroup c")
    Page<StudyGroup> findAllPageable(Pageable pageable);

    List<StudyGroup> findAllByCreationDateBetween(Date start, Date end);

    @Query("SELECT c from StudyGroup c WHERE c.id in :ids")
    Page<StudyGroup> findAllByIds(Pageable pageable, @Param("ids") List<Long> ids);
}
