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
@NoArgsConstructor
@Getter
@ToString
public class result {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private String starttime;

	@Column
	private String endtime;

	@Column
	private Long move;

	@Column
	private Long day;
	
}
