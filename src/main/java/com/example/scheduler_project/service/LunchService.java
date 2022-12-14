package com.example.scheduler_project.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scheduler_project.dto.LunchDto;
import com.example.scheduler_project.dto.TakenDto;
import com.example.scheduler_project.entity.Lunch;
import com.example.scheduler_project.entity.Taken;
import com.example.scheduler_project.repository.LunchRepository;
import com.example.scheduler_project.repository.TakenRepository;

@Service
public class LunchService {

	@Autowired
	private LunchRepository lunchRepository;
	@Autowired
	private TakenRepository takenRepository;

	public List<Lunch> findall() {
		return lunchRepository.findAll();
	}

	public List<LunchDto> positions(int id) {
		return lunchRepository.findAllByIndex(id)
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
	public TakenDto createtaken(TakenDto dto) {
		Lunch lunch1 = lunchRepository.findById(dto.getLunch1()).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));
		Lunch lunch2 = lunchRepository.findById(dto.getLunch2()).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));
		Taken taken = Taken.createPosition(dto, lunch1, lunch2);

		Taken created = takenRepository.save(taken);

		return TakenDto.createTakenDto(created);
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
