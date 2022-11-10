package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.Position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PositionDto {
	
	private Long id;
	private String x_position;
	private String y_position;

	public static PositionDto createPositionDto(Position position) {
		return new PositionDto(
			position.getId(),
			position.getX_position(),
			position.getY_position()
		);
	}

}
