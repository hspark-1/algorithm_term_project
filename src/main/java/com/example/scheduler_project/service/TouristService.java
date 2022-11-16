package com.example.scheduler_project.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scheduler_project.dto.TouristDto;
import com.example.scheduler_project.entity.Tourist;
import com.example.scheduler_project.repository.TouristRepository;

@Service
public class TouristService {

	@Autowired
	private TouristRepository touristRepository;

	public List<TouristDto> positions() {
		return touristRepository.findAll()
				.stream()
				.map(Tourist -> TouristDto.createTouristDto(Tourist))
				.collect(Collectors.toList());
	}

	@Transactional
	public TouristDto create(TouristDto dto) {

		Tourist tourist = Tourist.createPosition(dto);

		Tourist created = touristRepository.save(tourist);

		return TouristDto.createTouristDto(created);
	}
	
	@Transactional
	public TouristDto update(Long id, TouristDto dto) {
		// 댓글 조회 및 예외 발생
		Tourist target = touristRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

		// 댓글 수정
		target.patch(dto);

		// DB로 갱신
		Tourist updated = touristRepository.save(target);

		// 댓글 엔티티를 DTO로 변환 및 반환
		return TouristDto.createTouristDto(updated);
	}

	@Transactional
	public TouristDto delete(Long id) {
		// 댓글 조회(및 예외 발생)
		Tourist target = touristRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

		// 댓글 DB에서 삭제
		touristRepository.delete(target);

		// 삭제 댓글을 DTO로 반환
		return TouristDto.createTouristDto(target);
	}
	
}
