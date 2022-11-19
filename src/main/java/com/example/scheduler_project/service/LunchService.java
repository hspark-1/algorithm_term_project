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

	public List<Lunch> findall() {
		return lunchRepository.findAll();
	}

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
	
	@Transactional
	public LunchDto update(Long id, LunchDto dto) {
		// 댓글 조회 및 예외 발생
		Lunch target = lunchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

		// 댓글 수정
		target.patch(dto);

		// DB로 갱신
		Lunch updated = lunchRepository.save(target);

		// 댓글 엔티티를 DTO로 변환 및 반환
		return LunchDto.createLunchDto(updated);
	}

	@Transactional
	public LunchDto delete(Long id) {
		// 댓글 조회(및 예외 발생)
		Lunch target = lunchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

		// 댓글 DB에서 삭제
		lunchRepository.delete(target);

		// 삭제 댓글을 DTO로 반환
		return LunchDto.createLunchDto(target);
	}
	
}
