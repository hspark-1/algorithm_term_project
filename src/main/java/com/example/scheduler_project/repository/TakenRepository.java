package com.example.scheduler_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scheduler_project.entity.Taken;

public interface TakenRepository extends JpaRepository<Taken, Long> {
	
}
