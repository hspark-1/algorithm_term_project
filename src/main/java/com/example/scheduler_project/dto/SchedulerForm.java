package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.Scheduler;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class SchedulerForm {
	
	private Long id;
	private int travel_day;
	private String arrival_point;
	private String arrival_time;
	private String departure_time;
	private String return_time;
	private String lunch;
	private String dinner;
	private String tour_place;

	public Scheduler toEntity() {
		return new Scheduler(id, travel_day, arrival_point, arrival_time, departure_time, return_time, lunch, dinner, tour_place);
	}
	
}
