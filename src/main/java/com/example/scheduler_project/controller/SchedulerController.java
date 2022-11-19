package com.example.scheduler_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.scheduler_project.dto.ArrivalDto;
import com.example.scheduler_project.dto.DinnerDto;
import com.example.scheduler_project.dto.LunchDto;
import com.example.scheduler_project.dto.SchedulerForm;
import com.example.scheduler_project.dto.TouristDto;
import com.example.scheduler_project.entity.Lunch;
import com.example.scheduler_project.entity.Scheduler;
import com.example.scheduler_project.repository.LunchRepository;
import com.example.scheduler_project.repository.SchedulerRepository;
import com.example.scheduler_project.service.ArrivalService;
import com.example.scheduler_project.service.DinnerService;
import com.example.scheduler_project.service.LunchService;
import com.example.scheduler_project.service.TouristService;

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
	private DinnerService dinnerService;
	@Autowired
	private TouristService touristService;

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
		List<LunchDto> lunchDtos = lunchService.positions();
		List<ArrivalDto> arrivalDto = arrivalService.positions();
		log.info(arrivalDto.toString());
		model.addAttribute("positionDto", arrivalDto);
		model.addAttribute("positionDtos", lunchDtos);

		return "lunch";
	}

	@GetMapping("/dinner")
	public String selectDinner(Model model) {
		List<DinnerDto> dinnerDtos = dinnerService.positions();
		List<ArrivalDto> arrivalDto = arrivalService.positions();
		model.addAttribute("positionDto", arrivalDto);
		model.addAttribute("positionDtos", dinnerDtos);

		return "dinner";
	}

	@GetMapping("/tourist")
	public String selectTourist(Model model) {
		List<TouristDto> touristDtos = touristService.positions();
		List<ArrivalDto> arrivalDto = arrivalService.positions();
		model.addAttribute("positionDto", arrivalDto);
		model.addAttribute("positionDtos", touristDtos);

		return "tourist";
	}

	@GetMapping("/map")
	public String map(){
		return "map";
	}

	@GetMapping("/cal")
	public String Cal(Model model) {
		List<Lunch> lunchEntity = lunchRepository.findAll();
		List<DinnerDto> dinnerDtos = dinnerService.positions();
		List<TouristDto> touristDtos = touristService.positions();
		model.addAttribute("lunchDtos", lunchEntity);
		model.addAttribute("dinnerDtos", dinnerDtos);
		model.addAttribute("touristDtos", touristDtos);
		log.info(lunchEntity.toString());
		log.info(dinnerDtos.toString());
		log.info(touristDtos.toString());
		
		return "calculate";
	}
	

}
