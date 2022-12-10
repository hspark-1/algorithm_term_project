package com.example.scheduler_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.scheduler_project.dto.LunchDto;
import com.example.scheduler_project.dto.SchedulerForm;
import com.example.scheduler_project.entity.Lunch;
import com.example.scheduler_project.entity.Scheduler;
import com.example.scheduler_project.entity.Taken;
import com.example.scheduler_project.entity.result;
import com.example.scheduler_project.repository.LunchRepository;
import com.example.scheduler_project.repository.SchedulerRepository;
import com.example.scheduler_project.repository.TakenRepository;
import com.example.scheduler_project.repository.resultRepository;
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
	private LunchService lunchService;
	@Autowired
	private TakenRepository takenRepository;
	@Autowired
	private resultRepository resultrepository;

	@GetMapping("/mainpage")
	public String index() {
		return "mainpage";
	}

	@PostMapping("/create")
	public String createArticle(SchedulerForm form) {
		List<Scheduler> targetScheduler = schedulerRepository.findAll();
		for(int i=0; i<targetScheduler.size(); i++) {
			Scheduler target = targetScheduler.get(i);
			schedulerRepository.delete(target);
		}
		log.info(form.toString());

		Scheduler article = form.toEntity();
		log.info(article.toString());

		Scheduler saved = schedulerRepository.save(article);
		log.info(saved.toString());

		return "redirect:/arrival";
	}

	@GetMapping("/arrival")
	public String selectArrival(Model model) {
		List<LunchDto> arrivalDto = lunchService.positions(0);
		model.addAttribute("positionDto", arrivalDto);

		return "arrival";
	}

	@GetMapping("/lunch")
	public String selectLunch(Model model) {
		List<LunchDto> lunchDtos = lunchService.positions(1);
		List<LunchDto> arrivalDto = lunchService.positions(0);
		log.info(arrivalDto.toString());
		model.addAttribute("positionDto", arrivalDto);
		model.addAttribute("positionDtos", lunchDtos);

		return "lunch";
	}

	@GetMapping("/dinner")
	public String selectDinner(Model model) {
		List<LunchDto> dinnerDtos = lunchService.positions(2);
		List<LunchDto> arrivalDto = lunchService.positions(0);
		model.addAttribute("positionDto", arrivalDto);
		model.addAttribute("positionDtos", dinnerDtos);

		return "dinner";
	}

	@GetMapping("/tourist")
	public String selectTourist(Model model) {
		List<LunchDto> touristDtos = lunchService.positions(3);
		List<LunchDto> arrivalDto = lunchService.positions(0);
		model.addAttribute("positionDto", arrivalDto);
		model.addAttribute("positionDtos", touristDtos);

		List<Lunch> lunchEntity = lunchRepository.findAll();
		model.addAttribute("lunchDtos", lunchEntity);
		log.info(lunchEntity.toString());

		return "tourist";
	}

	@GetMapping("/algorithm")
	public String asd() {
		List<result> targetResult = resultrepository.findAll();
		List<Taken> targetTaken = takenRepository.findAll();
		for(int i=0; i<targetResult.size(); i++) {
			result target = targetResult.get(i);
			resultrepository.delete(target);
		}
		for(int i=0; i<targetTaken.size(); i++) {
			Taken target = targetTaken.get(i);
			takenRepository.delete(target);
		}
		return "asd";
	}

	@GetMapping("/result")
	public String algorithm(Model model) {
		List<Scheduler> schedulerEntity = schedulerRepository.findAll();
		log.info(schedulerEntity.toString());
		Scheduler scheduler = schedulerEntity.get(0);
		log.info(scheduler.toString());
		List<Lunch> arrivalEntity = lunchRepository.findAllByIndex(0);
		log.info(arrivalEntity.toString());
		Lunch arrival = arrivalEntity.get(0);
		log.info(arrival.toString());

		String arrival_time;
		long arrival_place;
		int traveldays;

        arrival_time = scheduler.getArrival_time();
		log.info(arrival_time);
        arrival_place = arrival.getId();
		log.info(""+arrival_place);
        traveldays = scheduler.getTravel_day();
		log.info(""+traveldays);
           
        travel tv = new travel(resultrepository, takenRepository, lunchRepository);
		log.info(""+traveldays);
        tv.atra(arrival_place, arrival_time, traveldays, tv, scheduler, model);
		
		return "result";
	}

	@GetMapping("/reset")
	public String reset() {
		List<Lunch> targetLunch = lunchRepository.findAll();
		List<result> targetResult = resultrepository.findAll();
		List<Taken> targetTaken = takenRepository.findAll();
		List<Scheduler> targetScheduler = schedulerRepository.findAll();

		for(int i=0; i<targetResult.size(); i++) {
			result target = targetResult.get(i);
			resultrepository.delete(target);
		}
		for(int i=0; i<targetTaken.size(); i++) {
			Taken target = targetTaken.get(i);
			takenRepository.delete(target);
		}
		for(int i=0; i<targetLunch.size(); i++) {
			Lunch target = targetLunch.get(i);
			lunchRepository.delete(target);
		}
		for(int i=0; i<targetScheduler.size(); i++) {
			Scheduler target = targetScheduler.get(i);
			schedulerRepository.delete(target);
		}

		return "redirect:/mainpage";
	}

}
