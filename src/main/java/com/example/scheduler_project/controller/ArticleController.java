package com.example.scheduler_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.scheduler_project.dto.ArticleForm;
import com.example.scheduler_project.entity.Article;
import com.example.scheduler_project.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ArticleController {

	public int result = 0;

	@Autowired
	private ArticleRepository articleRepository;
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@PostMapping("/cal")
	public String resultArticle(ArticleForm form) {
		log.info(form.toString());
		
		Article article = form.toEntity();
		log.info(article.toString());

		Article saved = articleRepository.save(article);
		log.info(saved.toString());
		
		result = article.cal();

		return "redirect:/result";
	}

	@GetMapping("result")
	public String result(Model model) {
		model.addAttribute("number", result);

		return "result";
	}
	
}
