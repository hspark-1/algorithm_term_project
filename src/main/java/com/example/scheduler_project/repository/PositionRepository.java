package com.example.scheduler_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scheduler_project.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
	
}
