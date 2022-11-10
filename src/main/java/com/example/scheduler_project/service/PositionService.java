package com.example.scheduler_project.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scheduler_project.dto.PositionDto;
import com.example.scheduler_project.entity.Position;
import com.example.scheduler_project.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;

	public List<PositionDto> positions() {
		return positionRepository.findAll()
				.stream()
				.map(position -> PositionDto.createPositionDto(position))
				.collect(Collectors.toList());
	}

	@Transactional
	public PositionDto create(PositionDto dto) {

		Position position = Position.createPosition(dto);

		Position created = positionRepository.save(position);

		return PositionDto.createPositionDto(created);
	}
	
}
