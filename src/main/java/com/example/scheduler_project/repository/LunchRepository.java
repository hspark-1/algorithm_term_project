package com.example.scheduler_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scheduler_project.entity.Lunch;

public interface LunchRepository extends JpaRepository<Lunch, Long> {
	
}
