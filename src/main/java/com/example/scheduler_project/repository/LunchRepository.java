package com.example.scheduler_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.scheduler_project.entity.Lunch;

public interface LunchRepository extends JpaRepository<Lunch, Long> {

	@Query(value =
            "SELECT * FROM lunch s WHERE s.index = ?1",
            nativeQuery = true)
	List<Lunch> findAllByIndex(int index);
	
}
