package com.example.scheduler_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class Scheduler {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private int travel_day;

	@Column
	private String arrival_point;

	@Column
	private String arrival_time;

	@Column
	private String departure_time;

	@Column
	private String return_time;

	@Column
	private String lunch;

	@Column
	private String dinner;

	@Column
	private String tour_place;

}
