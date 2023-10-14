package com.soa.lab2soa.repo;

import com.soa.lab2soa.model.Coordinates;
import com.soa.lab2soa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
