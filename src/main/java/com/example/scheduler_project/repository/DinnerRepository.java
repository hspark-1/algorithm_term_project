package com.example.scheduler_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scheduler_project.entity.Dinner;

public interface DinnerRepository extends JpaRepository<Dinner, Long> {
	
}
