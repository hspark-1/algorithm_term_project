package com.example.scheduler_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scheduler_project.entity.Arrival;

public interface ArrivalRepository extends JpaRepository<Arrival, Long> {
	
}
