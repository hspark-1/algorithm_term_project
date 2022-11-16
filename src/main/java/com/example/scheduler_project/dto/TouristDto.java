package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.Tourist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class TouristDto {
	
	private Long id;
	private String x_position;
	private String y_position;
	private String name;
	private int time;

	public static TouristDto createTouristDto(Tourist tourist) {
		return new TouristDto(
			tourist.getId(),
			tourist.getX_position(),
			tourist.getY_position(),
			tourist.getName(),
			tourist.getTime()
		);
	}

}
