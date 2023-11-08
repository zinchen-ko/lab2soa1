package com.soa.lab2soa.repo;

import com.soa.lab2soa.model.domain.Coordinates;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {

    @Query(
            "SELECT c FROM Coordinates c ORDER BY abs(c.x) + abs(c.y)"
    )
    List<Coordinates> findLessCoordinates();
}
