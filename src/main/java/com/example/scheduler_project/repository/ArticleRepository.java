package com.example.scheduler_project.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.scheduler_project.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {
	
}
