package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.Arrival;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ArrivalDto {
	
	private Long id;
	private String x_position;
	private String y_position;
	private String name;

	public static ArrivalDto createArrivalDto(Arrival arrival) {
		return new ArrivalDto(
			arrival.getId(),
			arrival.getX_position(),
			arrival.getY_position(),
			arrival.getName()
		);
	}
	
}
