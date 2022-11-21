package com.example.scheduler_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.scheduler_project.dto.ArrivalDto;
import com.example.scheduler_project.dto.LunchDto;
import com.example.scheduler_project.dto.SchedulerForm;
import com.example.scheduler_project.entity.Lunch;
import com.example.scheduler_project.entity.Scheduler;
import com.example.scheduler_project.entity.Taken;
import com.example.scheduler_project.repository.LunchRepository;
import com.example.scheduler_project.repository.SchedulerRepository;
import com.example.scheduler_project.repository.TakenRepository;
import com.example.scheduler_project.service.ArrivalService;
import com.example.scheduler_project.service.LunchService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SchedulerController {
	
	@Autowired
	private SchedulerRepository schedulerRepository;
	@Autowired
	private LunchRepository lunchRepository;
	@Autowired
	private ArrivalService arrivalService;
	@Autowired
	private LunchService lunchService;
	@Autowired
	private TakenRepository takenRepository;

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

		return "redirect:/arrival";
	}

	@GetMapping("/arrival")
	public String selectArrival(Model model) {
		List<ArrivalDto> arrivalDto = arrivalService.positions();
		model.addAttribute("positionDto", arrivalDto);

		return "arrival";
	}

	@GetMapping("/lunch")
	public String selectLunch(Model model) {
		List<LunchDto> lunchDtos = lunchService.positions(1);
		List<ArrivalDto> arrivalDto = arrivalService.positions();
		log.info(arrivalDto.toString());
		model.addAttribute("positionDto", arrivalDto);
		model.addAttribute("positionDtos", lunchDtos);

		return "lunch";
	}

	@GetMapping("/dinner")
	public String selectDinner(Model model) {
		List<LunchDto> dinnerDtos = lunchService.positions(2);
		List<ArrivalDto> arrivalDto = arrivalService.positions();
		model.addAttribute("positionDto", arrivalDto);
		model.addAttribute("positionDtos", dinnerDtos);

		return "dinner";
	}

	@GetMapping("/tourist")
	public String selectTourist(Model model) {
		List<LunchDto> touristDtos = lunchService.positions(3);
		List<ArrivalDto> arrivalDto = arrivalService.positions();
		model.addAttribute("positionDto", arrivalDto);
		model.addAttribute("positionDtos", touristDtos);

		List<Lunch> lunchEntity = lunchRepository.findAll();
		model.addAttribute("lunchDtos", lunchEntity);
		log.info(lunchEntity.toString());

		return "tourist";
	}

	@GetMapping("/map")
	public String map(){
		return "map";
	}

	@GetMapping("/cal")
	public String Cal(Model model) {
		List<Lunch> lunchEntity = lunchRepository.findAll();
		model.addAttribute("lunchDtos", lunchEntity);
		log.info(lunchEntity.toString());

		List<Taken> takens = takenRepository.findAll();
		model.addAttribute("takensEntity", takens);
		log.info(takens.toString());
		
		return "calculate";
	}
	

}
