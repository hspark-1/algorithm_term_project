package com.example.scheduler_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.scheduler_project.dto.DinnerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Dinner {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String x_position;

	@Column
	private String y_position;

	public static Dinner createPosition(DinnerDto dto) {

		if(dto.getId()!=null)
			throw new IllegalArgumentException("위치 생성 실패! id가 없음!");

		return new Dinner(
			dto.getId(),
			dto.getX_position(),
			dto.getY_position()
		);
	}

}
