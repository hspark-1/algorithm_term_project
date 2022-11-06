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
public class Article {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String title;

	@Column
	private String content;

	public int cal() {
		int ans=0;

		ans = Integer.parseInt(title)+Integer.parseInt(content);

		return ans;
	}

}
