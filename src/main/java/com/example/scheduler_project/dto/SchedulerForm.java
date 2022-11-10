package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.Scheduler;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class SchedulerForm {
	
	private Long id;
	private int travel_day;
	private String arrival_time;
	private String departure_time;
	private String return_time;

	public Scheduler toEntity() {
		return new Scheduler(id, travel_day, arrival_time, departure_time, return_time);
	}
	
}
