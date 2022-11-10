package com.example.scheduler_project.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scheduler_project.dto.DinnerDto;
import com.example.scheduler_project.entity.Dinner;
import com.example.scheduler_project.repository.DinnerRepository;

@Service
public class DinnerService {

	@Autowired
	private DinnerRepository dinnerRepository;

	public List<DinnerDto> positions() {
		return dinnerRepository.findAll()
				.stream()
				.map(dinner -> DinnerDto.createDinnerDto(dinner))
				.collect(Collectors.toList());
	}

	@Transactional
	public DinnerDto create(DinnerDto dto) {

		Dinner dinner = Dinner.createPosition(dto);

		Dinner created = dinnerRepository.save(dinner);

		return DinnerDto.createDinnerDto(created);
	}
	
}
