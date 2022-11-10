package com.example.scheduler_project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scheduler_project.dto.PositionDto;
import com.example.scheduler_project.service.PositionService;

@RestController
public class PositionApiController {

	@Autowired
	private PositionService positionService;

	@PostMapping("/position/create")
	public ResponseEntity<PositionDto> create(@RequestBody PositionDto dto) {
		PositionDto createdDto = positionService.create(dto);

		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}
	
}
