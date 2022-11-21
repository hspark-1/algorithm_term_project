package com.example.scheduler_project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scheduler_project.annotation.RunningTime;
import com.example.scheduler_project.dto.ArrivalDto;
import com.example.scheduler_project.dto.LunchDto;
import com.example.scheduler_project.dto.TakenDto;
import com.example.scheduler_project.service.ArrivalService;
import com.example.scheduler_project.service.LunchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PositionApiController {

	@Autowired
	private LunchService lunchService;
	@Autowired
	private ArrivalService arrivalService;

	@PostMapping("/position/create/arrival")
	public ResponseEntity<ArrivalDto> create_arrival(@RequestBody ArrivalDto dto) {
		ArrivalDto createdDto = arrivalService.create(dto);

		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}

	@PostMapping("/position/create/lunch")
	public ResponseEntity<LunchDto> create(@RequestBody LunchDto dto) {
		LunchDto createdDto = lunchService.create(dto);

		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}

	@PostMapping("/taken/create")
	public ResponseEntity<TakenDto> createtaken(@RequestBody TakenDto dto) {
		log.info("dto = " + dto);
		TakenDto createdDto = lunchService.createtaken(dto);

		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}

	// 댓글 삭제
	@RunningTime
	@DeleteMapping("/api/arrival/{id}")
	public ResponseEntity<ArrivalDto> arrivaldelete(@PathVariable Long id) {
		// 서비스에게 위임
		ArrivalDto updatedDto = arrivalService.delete(id);

		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
	}
	
	// 댓글 수정
	@PatchMapping("/lunch/update/{id}")
	public ResponseEntity<LunchDto> lunchupdate(@PathVariable Long id, @RequestBody LunchDto dto) {
		// 서비스에게 위임
		LunchDto updatedDto = lunchService.update(id, dto);

		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
	}

	// 댓글 삭제
	@RunningTime
	@DeleteMapping("/api/lunch/{id}")
	public ResponseEntity<LunchDto> lunchdelete(@PathVariable Long id) {
		// 서비스에게 위임
		LunchDto updatedDto = lunchService.delete(id);

		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
	}

}
