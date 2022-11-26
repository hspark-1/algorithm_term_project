package com.example.scheduler_project.dto;

import com.example.scheduler_project.entity.result;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class resultDto {
	
	private Long id;
	private String name;
	private String time;
	private String move;

	public static result toEntity(String name, String time, String move) {
		return new result(null, name, time, move);
	}
	
}
