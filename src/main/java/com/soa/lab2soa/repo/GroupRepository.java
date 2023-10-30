package com.soa.lab2soa.repo;

import com.soa.lab2soa.model.Coordinates;
import com.soa.lab2soa.model.StudyGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
    List<StudyGroup> findAllLessThanTransferredStudents(@Param("transferred") int transferred);


    Optional<StudyGroup> findByCoordinates(Coordinates coordinates);

    @Query("SELECT c FROM StudyGroup c")
    Page<StudyGroup> findAllPageable(Pageable pageable);

}
