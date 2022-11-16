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
	
	@Transactional
	public DinnerDto update(Long id, DinnerDto dto) {
		// 댓글 조회 및 예외 발생
		Dinner target = dinnerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

		// 댓글 수정
		target.patch(dto);

		// DB로 갱신
		Dinner updated = dinnerRepository.save(target);

		// 댓글 엔티티를 DTO로 변환 및 반환
		return DinnerDto.createDinnerDto(updated);
	}

	@Transactional
	public DinnerDto delete(Long id) {
		// 댓글 조회(및 예외 발생)
		Dinner target = dinnerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

		// 댓글 DB에서 삭제
		dinnerRepository.delete(target);

		// 삭제 댓글을 DTO로 반환
		return DinnerDto.createDinnerDto(target);
	}
	
}
