package com.example.scheduler_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.scheduler_project.dto.PositionDto;
import com.example.scheduler_project.dto.SchedulerForm;
import com.example.scheduler_project.entity.Scheduler;
import com.example.scheduler_project.repository.SchedulerRepository;
import com.example.scheduler_project.service.PositionService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SchedulerController {
	
	@Autowired
	private SchedulerRepository schedulerRepository;
	@Autowired
	private PositionService positionService;

	@GetMapping("/mainpage")
	public String index() {
		return "mainpage";
	}

	@PostMapping("/create")
	public String createArticle(SchedulerForm form) {
		log.info(form.toString());

		Scheduler article = form.toEntity();
		log.info(article.toString());

		Scheduler saved = schedulerRepository.save(article);
		log.info(saved.toString());

		return "";
	}

	@GetMapping("/map")
	public String showMap(Model model) {
		List<PositionDto> positionDtos = positionService.positions();
		model.addAttribute("positionDtos", positionDtos);

		return "map";
	}

}
