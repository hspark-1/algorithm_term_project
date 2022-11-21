package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.Lunch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LunchDto {
	
	private Long id;
	private String x_position;
	private String y_position;
	private String name;
	private int time;
	private int index;
	private int visit;

	public Lunch toEntity() {
		return new Lunch(id, x_position, y_position, name, time, index, visit);
	}

	public static LunchDto createLunchDto(Lunch lunch) {
		return new LunchDto(
			lunch.getId(),
			lunch.getX_position(),
			lunch.getY_position(),
			lunch.getName(),
			lunch.getTime(),
			lunch.getIndex(),
			lunch.getVisit()
		);
	}

}
