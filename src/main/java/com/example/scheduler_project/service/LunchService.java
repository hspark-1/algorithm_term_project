package com.example.scheduler_project.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scheduler_project.dto.LunchDto;
import com.example.scheduler_project.entity.Lunch;
import com.example.scheduler_project.repository.LunchRepository;

@Service
public class LunchService {

	@Autowired
	private LunchRepository lunchRepository;

	public List<LunchDto> positions() {
		return lunchRepository.findAll()
				.stream()
				.map(lunch -> LunchDto.createLunchDto(lunch))
				.collect(Collectors.toList());
	}

	@Transactional
	public LunchDto create(LunchDto dto) {

		Lunch lunch = Lunch.createPosition(dto);

		Lunch created = lunchRepository.save(lunch);

		return LunchDto.createLunchDto(created);
	}
	
}
