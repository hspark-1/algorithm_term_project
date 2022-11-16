package com.example.scheduler_project.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scheduler_project.dto.ArrivalDto;
import com.example.scheduler_project.entity.Arrival;
import com.example.scheduler_project.repository.ArrivalRepository;

@Service
public class ArrivalService {
	
	@Autowired
	private ArrivalRepository arrivalRepository;

	public List<ArrivalDto> positions() {
		return arrivalRepository.findAll()
				.stream()
				.map(arrival -> ArrivalDto.createArrivalDto(arrival))
				.collect(Collectors.toList());
	}

	@Transactional
	public ArrivalDto create(ArrivalDto dto) {

		Arrival arrival = Arrival.createPosition(dto);

		Arrival created = arrivalRepository.save(arrival);

		return ArrivalDto.createArrivalDto(created);
	}

	@Transactional
	public ArrivalDto delete(Long id) {
		// 댓글 조회(및 예외 발생)
		Arrival target = arrivalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

		// 댓글 DB에서 삭제
		arrivalRepository.delete(target);

		// 삭제 댓글을 DTO로 반환
		return ArrivalDto.createArrivalDto(target);
	}
	
}
