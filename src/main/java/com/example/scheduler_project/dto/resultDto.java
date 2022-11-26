package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.result;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class resultDto {
	
	private Long id;
	private String name;
	private String starttime;
	private String endtime;
	private long move;
	private long day;

	public static result toEntity(String name, String starttime, String endtime, long move, long day) {
		return new result(null, name, starttime, endtime, move, day);
	}
	
}
