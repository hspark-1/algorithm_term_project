package com.example.scheduler_project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scheduler_project.dto.DinnerDto;
import com.example.scheduler_project.dto.LunchDto;
import com.example.scheduler_project.service.DinnerService;
import com.example.scheduler_project.service.LunchService;

@RestController
public class PositionApiController {

	@Autowired
	private LunchService lunchService;
	@Autowired
	private DinnerService dinnerService;

	@PostMapping("/position/create")
	public ResponseEntity<LunchDto> create(@RequestBody LunchDto dto) {
		LunchDto createdDto = lunchService.create(dto);

		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}
	
	@PostMapping("/position/create/dinner")
	public ResponseEntity<DinnerDto> create_dinner(@RequestBody DinnerDto dto) {
		DinnerDto createdDto = dinnerService.create(dto);

		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}
	
}
