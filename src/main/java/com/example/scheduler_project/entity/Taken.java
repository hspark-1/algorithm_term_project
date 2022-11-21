package com.example.scheduler_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.scheduler_project.dto.TakenDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Taken {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "start")
	private Lunch lunch1;

	@ManyToOne
	@JoinColumn(name = "arrive")
	private Lunch lunch2;

	@Column
	private int time;

	@Column
	private int visit;

	public static Taken createPosition(TakenDto dto, Lunch lunch1, Lunch lunch2) {

		if(dto.getId()!=null)
			throw new IllegalArgumentException("위치 생성 실패! id가 없음!");

		return new Taken(
			dto.getId(),
			lunch1,
			lunch2,
			dto.getTime(),
			dto.getVisit()
		);
	}
	
}
