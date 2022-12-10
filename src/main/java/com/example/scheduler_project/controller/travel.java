package com.example.scheduler_project.controller;

import java.util.List;

import org.springframework.ui.Model;

import com.example.scheduler_project.dto.resultDto;
import com.example.scheduler_project.entity.Lunch;
import com.example.scheduler_project.entity.Scheduler;
import com.example.scheduler_project.entity.Taken;
import com.example.scheduler_project.entity.result;
import com.example.scheduler_project.repository.LunchRepository;
import com.example.scheduler_project.repository.TakenRepository;
import com.example.scheduler_project.repository.resultRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

class PlaceList { // object for storing places
	private long place;
	private String start_time;
	private int play_time;
	private String end_time;
	private int take_time;
	private int day;
	
	public PlaceList() {
		this.place = -1;
		this.start_time = "0";
		this.end_time = "0";
		this.take_time = -1;
		this.day = -1;
	}
	
	public PlaceList(long place, String start_time, String end_time, int take_time, int day) {
		this.place = place;
		this.start_time = start_time;
		this.end_time = end_time;
		this.take_time = take_time;
		this.day = day;
	}
	
	public void setPlace(long place) {
		this.place = place;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public long getplace() {
		return place;
	}
	
	public String getstarttime() {
		return start_time;
	}
	
	public int getplaytime() {
		return play_time;
	}
	
	public String getendtime() {
		return end_time;
	}
	
	public int gettaketime() {
		return take_time;
	}
	
	public int getday() {
		return day;
	}
}


@RequiredArgsConstructor
@Slf4j
public class travel{

	// repository to communicate with the database
	final resultRepository resultrepository;
	final TakenRepository takenRepository;
	final LunchRepository lunchRepository;
	
	// Variables to store data in the database
	List<Scheduler> schedulerEntity;
	Scheduler scheduler;
	Model model;
	private int returntime;
	private static String arrival_time;
	private static long arrival_place;
	private static int traveldays;

	public int days = 1;
	public int jud_day = days;
	public int count = 0;
	public int[] day_meal = new int[100];
    private PlaceList[] place_list = new PlaceList[10000];
    
    private int ChangetoMiniute(String time){ // Convert data stored in "HH:MM" format to minutes
        String[] temp = time.split(":");
        int hour = Integer.parseInt(temp[0]);
        int min = Integer.parseInt(temp[1]);

        return hour * 60 + min;
    }
    
    private String ComputeTime(String CurrentTime, int taketime, int PlayTime) { // Calculate current time + travel time + required time
    	String time = "";
    	String[] temp = CurrentTime.split(":");
    	int hour = Integer.parseInt(temp[0]);
    	int min = Integer.parseInt(temp[1]);
    	
    	min += taketime;
    	min += PlayTime;
    	
    	if(min >= 60) {
    		hour += min / 60;
    		min = min % 60;
    	}
    	
		if(min<10) {
			time = Integer.toString(hour) + ":0" + Integer.toString(min);
		} else {
			time = Integer.toString(hour) + ":" + Integer.toString(min);
		}
    	return time;
    }
    
    private int checkTime(String startasd, String endasd) { // decide what to do now current time
		String returntime1 = scheduler.getReturn_time();
		String[] str = returntime1.split(":");
		returntime = Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]);
    	int startTime1 = ChangetoMiniute(startasd);
    	int endTime1 = ChangetoMiniute(endasd);
		if(endTime1<=720) { // tour before 12
			return 0;
		} else if(endTime1>=720 && endTime1<=840 && day_meal[days] < 1) { // Lunch 12 - 14
			return 1;
		} else if(startTime1>=720 && startTime1<=840 && day_meal[days] < 1) { // Lunch 12 - 14
			return 1;
		} else if(endTime1>=720 && endTime1<=840 && startTime1>=720 && startTime1<=840 && day_meal[days] < 1) { // Lunch 12 - 14
			return 1;
		} else if(startTime1<=720 && endTime1>=840 && day_meal[days] < 1) { // Lunch 12 - 14
			return 4;
		} else if(startTime1>=1080 && startTime1<=1200 && day_meal[days] < 2) { // Dinner 18 - 20
			return 2;
		} else if(endTime1>=1080 && endTime1<=1200 && day_meal[days] < 2) { // Dinner 18 - 20
			return 2;
		} else if(endTime1>=1080 && endTime1<=1200 && startTime1>=1080 && startTime1<=1200 && day_meal[days] < 2) { // Dinner 18 - 20
			return 2;
		} else if(startTime1<=1080 && endTime1>=1200 && day_meal[days] < 2) { // Dinner 18 - 20
			return 5;
		} else if(endTime1>=(returntime+30)) { // return after return-time
			return 3;
		} else if(startTime1>=returntime) { // return after return-time
			return 3;
		} else { // tour
			return 0;
		}
	}
    
    private void PrintPlaceList(PlaceList[] list, int z) { // Stored in the itinerary database calculated by the algorithm
		// Alert message to communicate with users
		if(z==0) {
			model.addAttribute("msg", "당신의 투어 결과.");
		} else if(z==1) {
			model.addAttribute("msg", "당신의 투어 결과.");
		} else if(z==2) {
			model.addAttribute("msg", "관광지가 너무 많습니다. 조금 줄여주십시오.");
		} else if(z==3) {
			model.addAttribute("msg", "점심 식사할 곳이 없습니다. 조금 추가해주십시오.");
		} else if(z==4) {
			model.addAttribute("msg", "저녁 식사할 곳이 없습니다. 조금 추가해주십시오.");
		}

		log.info("count = " + count);
		int zdxc = 0;
    	for (int i = 0; i < count; i++) {
    		if(place_list[i].getstarttime().equals(scheduler.getDeparture_time()) && zdxc == 0) { // when the date changes
    			log.info("[" + place_list[i].getday() + "]");
				zdxc = 1;
    		}
    		else { // Travel itinerary (save travel location, travel time, travel time, date)
				Lunch lunch = lunchRepository.findById(place_list[i].getplace()).orElse(null);
				String a = place_list[i].getstarttime();
				String d = place_list[i].getendtime();
				long b = place_list[i].gettaketime();
				long c = place_list[i].getday();
				result newResult = resultDto.toEntity(lunch.getName(), a, d, b, c);
				log.info(newResult.toString());
				result save = resultrepository.save(newResult);
				log.info("save = " + save);
    			log.info(lunch.getName() + " (" + place_list[i].getstarttime() + " ~ " + place_list[i].getendtime() + ")");
				log.info(place_list[i].gettaketime() + "분 이동");
				zdxc = 0;
    		}
    	}

		// Pass the model loaded from the database to the viewpager
		List<result> results = resultrepository.findAll();
		log.info(results.toString());
		model.addAttribute("resultEntity", results);
    }

    public void ComputeNextPlace(long current_place, String current_time) { // Determining what to do next through time, conditional on the current time or the time when the next location is reached
        // Find the closest place to your current location through the database and get the time to get there.
		int asd = 1;
    	long NextPlace;
    	int TimetoNextPlace;
    	int PlayTime;
		
		if(count == 0) { // When the current location is the destination
			// Get the fastest reachable place from the database
			List<Taken> takenEntity = takenRepository.findFirsttourlocation(current_place);
			Taken taken = takenEntity.get(0);
			NextPlace = taken.getLunch2().getId();
			TimetoNextPlace = taken.getTime();
			PlayTime = taken.getLunch1().getTime();
		}
		else {
			// Get the fastest reachable place from the database
			List<Taken> takenEntity = takenRepository.findtourlocation(current_place);
			Taken taken;
			try { // try-catch statement for exception handling
				taken = takenEntity.get(0);
			} catch (Exception e) { // Lunch and dinner are left, but there are no tourist attractions.
				// Guide to the nearest location for lunch and dinner
				while(true) { // until there is no lunch or dinner
					if(day_meal[days] < 1) { // when I didn't eat lunch
						// Get the fastest reachable place from the database
						Lunch lunch = lunchRepository.findById(current_place).orElse(null);
						PlayTime = lunch.getTime();
						 // Find out what to do at the current time
						if(ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) < 720) { // for lunch
							List<Taken> takenEntity1 = takenRepository.findFirstLunchlocation(current_place);
							Taken taken1;
							try { // try-catch statement for exception handling
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) {
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
				
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for lunch = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							// Change the visit status to Yes among the information of NextPlace in the database
							takenRepository.updateLunchlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = "12:00";
						} else if(ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= 720 && ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) <= 840) { // for lunch
							List<Taken> takenEntity1 = takenRepository.findFirstLunchlocation(current_place);
							Taken taken1;
							try { // try-catch statement for exception handling
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) {
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
				
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for lunch = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							// Change the visit status to Yes among the information of NextPlace in the database
							takenRepository.updateLunchlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = ComputeTime(current_time, TimetoNextPlace, PlayTime);
						} else { // for lunch
							List<Taken> takenEntity1 = takenRepository.findFirstLunchlocation(current_place);
							Taken taken1;
							try { // try-catch statement for exception handling
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) {
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
				
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for lunch = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							// Change the visit status to Yes among the information of NextPlace in the database
							takenRepository.updateLunchlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = ComputeTime(current_time, TimetoNextPlace, PlayTime);
						}
					} else if(day_meal[days] < 2) { // when you haven't eaten dinner
						Lunch lunch = lunchRepository.findById(current_place).orElse(null);
						PlayTime = lunch.getTime();
						// Find out what to do at the current time
						if(ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) < 1080) {
							// The closest dinner place to your current location
							List<Taken> takenEntity1 = takenRepository.findFirstDinnerlocation(current_place);
							Taken taken1;
							try { // try-catch statement for exception handling
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) {
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
							
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for dinner = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							// Change the visit status to Yes among the information of NextPlace in the database
							takenRepository.updateDinnerlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = "18:00";
							days++;
						} else if(ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= 1080 && ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) <= 1200) { // 18시 - 20시
							// The closest dinner place to your current location
							List<Taken> takenEntity1 = takenRepository.findFirstDinnerlocation(current_place);
							Taken taken1;
							try { // try-catch statement for exception handling
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) {
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
							
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for dinner = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							// Change the visit status to Yes among the information of NextPlace in the database
							takenRepository.updateDinnerlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							days++;
						} else {
							// The closest dinner place to your current location
							List<Taken> takenEntity1 = takenRepository.findFirstDinnerlocation(current_place);
							Taken taken1;
							try { // try-catch statement for exception handling
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) {
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
							
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for dinner = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							// Change the visit status to Yes among the information of NextPlace in the database
							takenRepository.updateDinnerlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							days++;
						}
					} else { // When there is no place for lunch or dinner
						break;
					}
				}
				// Save current location for last data
				Lunch lunch = lunchRepository.findById(current_place).orElse(null);
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 2);
					return;
				}
				log.info("days = " + days);
				// Save to last place travel route
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 0);
    			return;
			}
			NextPlace = taken.getLunch2().getId();
			TimetoNextPlace = taken.getTime();
			PlayTime = taken.getLunch1().getTime();
		}
		
    	String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime); // The current time, the time taken, and the play time are passed to the function to find the end time at the next place.
		log.info(current_time);
		log.info(ExpectTime);
    	
		if(days == traveldays) { // On the last day
			List<Taken> takenEntity = takenRepository.findNotvisit(current_place);
			try { // try-catch statement for exception handling
				Taken taken = takenEntity.get(0);
				log.info(taken.toString());
			} catch (Exception e) {
				Lunch lunch = lunchRepository.findById(current_place).orElse(null);
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 2);
					return;
				}
				// Save to last place travel route
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 1); // no tour in last day
    			return;
			}
			
    		if(checkTime(current_time, ExpectTime) == 3) { // On the last day, there were still places to go, but it was late.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null);
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 2);
					return;
				}
				// Save to last place travel route
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 2); // no time 
    			return;
    		}
    	}
		
		if(jud_day != days) {
			if((checkTime(current_time, ExpectTime) == 1)){ // If the current time or the time to arrive at the next location is lunch or dinner (1 for lunch, 2 for dinner, 0 for pass)
				List<Taken> takenEntity = takenRepository.findFirstLunchlocation(current_place);
				Taken taken;
				try { // try-catch statement for exception handling
					taken = takenEntity.get(0);
				} catch (Exception e) { // I need to eat lunch, but I have nowhere to go.
					Lunch lunch = lunchRepository.findById(current_place).orElse(null);
					PlayTime = lunch.getTime();
					if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
						PrintPlaceList(place_list, 3);
						return;
					}
					// Save to last place travel route
					place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
					PrintPlaceList(place_list, 3); // no lunch
					return ;
				}
				NextPlace = taken.getLunch2().getId();
				day_meal[days]++;
	
				ExpectTime = ComputeTime(current_time, taken.getTime(), taken.getLunch1().getTime());
			}
			current_place = NextPlace;
			jud_day = days;
			ComputeNextPlace(current_place, current_time);
	
			return;
		}
    	
		if((checkTime(current_time, ExpectTime) == 1)){ // If the current time or the time to arrive at the next location is lunch or dinner (1 for lunch, 2 for dinner, 0 for pass)
        	// 데이터테이블에서 정보를 뽑아옴(Lunch만)
			List<Taken> takenEntity = takenRepository.findFirstLunchlocation(current_place);
			Taken taken;
			try { // try-catch statement for exception handling
				taken = takenEntity.get(0);
			} catch (Exception e) { // I need to eat lunch, but I have nowhere to go.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null);
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 3);
					return;
				}
				// Save to last place travel route
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 3); // no lunch
    			return ;
			}
			NextPlace = taken.getLunch2().getId();
			TimetoNextPlace = taken.getTime();
			PlayTime = taken.getLunch1().getTime();

    		ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
			log.info("for lunch = " + current_time);
			log.info(ExpectTime);
			day_meal[days]++;
			// Change the visit status to Yes among the NextPlace information in the DB
			takenRepository.updateLunchlocation(current_place);
        }
    	else if((checkTime(current_time, ExpectTime) == 2)) {
			List<Taken> takenEntity = takenRepository.findFirstDinnerlocation(current_place);
			Taken taken;
			try { // try-catch statement for exception handling
				taken = takenEntity.get(0);
			} catch (Exception e) { // I have to eat dinner, but I have nowhere to go.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null);
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 4);
					return;
				}
				// Save to last place travel route
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 4); // no dinner
    			return;
			}
			NextPlace = taken.getLunch2().getId();
			TimetoNextPlace = taken.getTime();
			PlayTime = taken.getLunch1().getTime();
			
    		ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
			log.info("for dinner = " + current_time);
			log.info(ExpectTime);
			day_meal[days]++;
    		// Change the visit status to Yes among the NextPlace information in the DB
			takenRepository.updateDinnerlocation(current_place);
			if(ChangetoMiniute(ComputeTime(current_time, TimetoNextPlace, PlayTime))>returntime) {
				days++;
				TimetoNextPlace = 0;
				current_time = scheduler.getDeparture_time(); // Reset to departure time
				asd = 0;
			}
        }
        else if (checkTime(current_time, ExpectTime) == 3) { // If it's time to go home
			Lunch lunch = lunchRepository.findById(current_place).orElse(null);
			if(lunch.getIndex()==2) {
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
				takenRepository.updateTourlocation(current_place);
			}
            days++;	// Increase travel date by one
            TimetoNextPlace = 0;
            current_time = scheduler.getDeparture_time(); // Reset to departure time
			asd = 0;
        }
		else if (checkTime(current_time, ExpectTime) == 4) {
			List<Taken> takenEntity = takenRepository.findFirstLunchlocation(current_place);
			Taken taken;
			try { // try-catch statement for exception handling
				taken = takenEntity.get(0);
			} catch (Exception e) { // I need to eat lunch, but I have nowhere to go.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null);
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 3);
					return;
				}
				// Save to last place travel route
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 3);
    			return ;
			}
			day_meal[days]++;

			ExpectTime = ComputeTime(current_time, taken.getTime(), taken.getLunch1().getTime());
			current_place = taken.getLunch2().getId();
			jud_day = days;
			ComputeNextPlace(current_place, current_time);
			return;
		}
		else if (checkTime(current_time, ExpectTime) == 5) {
			List<Taken> takenEntity = takenRepository.findFirstDinnerlocation(current_place);
			Taken taken;
			try { // try-catch statement for exception handling
				taken = takenEntity.get(0);
			} catch (Exception e) { // I have to eat dinner, but I have nowhere to go.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null);
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 4);
					return;
				}
				// Save to last place travel route
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 4);
    			return;
			}
			day_meal[days]++;

			ExpectTime = ComputeTime(current_time, taken.getTime(), taken.getLunch1().getTime());
			current_place = taken.getLunch2().getId();
			jud_day = days;
			ComputeNextPlace(current_place, current_time);
			return;
		}
        else {
        	// Change the visit status to Yes among the NextPlace information in the DB
			takenRepository.updateTourlocation(current_place);
			if(ChangetoMiniute(ComputeTime(current_time, TimetoNextPlace, PlayTime))>returntime) {
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
				days++;
				TimetoNextPlace = 0;
				current_time = scheduler.getDeparture_time(); // Reset to departure time
				asd = 0;
			}
        }

		// Save travel route
    	place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
		
		if(TimetoNextPlace != 0) { //When the date doesn't change
			current_place = NextPlace;
		}
    	
    	if (asd == 0) // When the date changes
    		ComputeNextPlace(current_place, ComputeTime(current_time, 0, 0));
    	else
    		ComputeNextPlace(current_place, ComputeTime(current_time, TimetoNextPlace, PlayTime));

    	return; // terminate
    }

	// get data from database Basic Information
	public void atra(long current_place, String current_time, int traveldaysa, travel tv, Scheduler scheduler1, Model model1) {
		model = model1;
		scheduler = scheduler1;
		log.info("travel.java");
        arrival_time = current_time;
        arrival_place = current_place;
        traveldays = traveldaysa;
        tv.ComputeNextPlace(arrival_place, arrival_time);
	}

}