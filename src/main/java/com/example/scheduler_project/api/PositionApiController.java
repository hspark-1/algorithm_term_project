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
import com.example.scheduler_project.dto.DinnerDto;
import com.example.scheduler_project.dto.LunchDto;
import com.example.scheduler_project.dto.TouristDto;
import com.example.scheduler_project.service.ArrivalService;
import com.example.scheduler_project.service.DinnerService;
import com.example.scheduler_project.service.LunchService;
import com.example.scheduler_project.service.TouristService;

@RestController
public class PositionApiController {

	@Autowired
	private LunchService lunchService;
	@Autowired
	private DinnerService dinnerService;
	@Autowired
	private ArrivalService arrivalService;
	@Autowired
	private TouristService touristService;

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
	
	@PostMapping("/position/create/dinner")
	public ResponseEntity<DinnerDto> create_dinner(@RequestBody DinnerDto dto) {
		DinnerDto createdDto = dinnerService.create(dto);

		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}

	@PostMapping("/position/create/tourist")
	public ResponseEntity<TouristDto> create_tourist(@RequestBody TouristDto dto) {
		TouristDto createdDto = touristService.create(dto);

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
	
	// 댓글 수정
	@PatchMapping("/dinner/update/{id}")
	public ResponseEntity<DinnerDto> dinnerupdate(@PathVariable Long id, @RequestBody DinnerDto dto) {
		// 서비스에게 위임
		DinnerDto updatedDto = dinnerService.update(id, dto);

		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
	}

	// 댓글 삭제
	@RunningTime
	@DeleteMapping("/api/dinner/{id}")
	public ResponseEntity<DinnerDto> dinnerdelete(@PathVariable Long id) {
		// 서비스에게 위임
		DinnerDto updatedDto = dinnerService.delete(id);

		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
	}
	
	// 댓글 수정
	@PatchMapping("/tourist/update/{id}")
	public ResponseEntity<TouristDto> touristupdate(@PathVariable Long id, @RequestBody TouristDto dto) {
		// 서비스에게 위임
		TouristDto updatedDto = touristService.update(id, dto);

		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
	}

	// 댓글 삭제
	@RunningTime
	@DeleteMapping("/api/tourist/{id}")
	public ResponseEntity<TouristDto> touristdelete(@PathVariable Long id) {
		// 서비스에게 위임
		TouristDto updatedDto = touristService.delete(id);

		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
	}
	
}
