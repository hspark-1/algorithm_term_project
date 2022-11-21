package com.example.scheduler_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.scheduler_project.dto.LunchDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Lunch {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String x_position;

	@Column
	private String y_position;

	@Column
	private String name;

	@Column
	private int time;
	
	@Column
	private int index;

	@Column
	private int visit;

	public static Lunch createPosition(LunchDto dto) {

		if(dto.getId()!=null)
			throw new IllegalArgumentException("위치 생성 실패! id가 없음!");

		return new Lunch(
			dto.getId(),
			dto.getX_position(),
			dto.getY_position(),
			dto.getName(),
			dto.getTime(),
			dto.getIndex(),
			dto.getVisit()
		);
	}

	public void patch(LunchDto dto) {
		// 예외 발생
		if (this.id != dto.getId())
			throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");

		// 객체를 갱신
		if (dto.getId() != null)
			this.id = dto.getId();

		if (dto.getX_position() != null)
			this.x_position = dto.getX_position();

		if (dto.getY_position() != null)
			this.y_position = dto.getY_position();

		if (dto.getName() != null)
			this.name = dto.getName();

		if (dto.getTime() != 0)
			this.time = dto.getTime();

		if (dto.getIndex() != 0)
			this.index = dto.getIndex();

		if (dto.getVisit() != 1)
			this.visit = dto.getVisit();
	}

}
