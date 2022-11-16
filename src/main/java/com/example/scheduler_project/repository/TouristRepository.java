package com.example.scheduler_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scheduler_project.entity.Tourist;

public interface TouristRepository extends JpaRepository<Tourist, Long> {
	
}
