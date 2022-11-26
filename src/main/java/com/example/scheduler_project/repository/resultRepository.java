package com.example.scheduler_project.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.scheduler_project.entity.result;

public interface resultRepository extends CrudRepository<result, Long> {
	
	@Override
	ArrayList<result> findAll();

}
