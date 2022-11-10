package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.Dinner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DinnerDto {
	
	private Long id;
	private String x_position;
	private String y_position;

	public static DinnerDto createDinnerDto(Dinner dinner) {
		return new DinnerDto(
			dinner.getId(),
			dinner.getX_position(),
			dinner.getY_position()
		);
	}

}
