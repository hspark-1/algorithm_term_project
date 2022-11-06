package com.example.scheduler_project.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.scheduler_project.entity.Scheduler;

public interface SchedulerRepository extends CrudRepository<Scheduler, Long> {
	
}
