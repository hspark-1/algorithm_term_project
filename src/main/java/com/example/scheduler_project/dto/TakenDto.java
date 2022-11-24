package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.Taken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class TakenDto {
	
	private Long id;
	private Long lunch1;
	private int start_index;
	private Long lunch2;
	private int arrive_index;
	private int time;
	private int visit;

	public static TakenDto createTakenDto(Taken taken) {
		return new TakenDto(
			taken.getId(),
			taken.getLunch1().getId(),
			taken.getStart_index(),
			taken.getLunch2().getId(),
			taken.getArrive_index(),
			taken.getTime(),
			taken.getVisit()
		);
	}
	
}
