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

class PlaceList {
	private long place;		// 이 장소의 ID
	private String start_time;	// 이 장소에서 시작하느 시간
	private int play_time;	// 이 장소에서 노는 시간
	private String end_time;	// 이 장소에서 마치는 시간
	private int take_time;	// 다음 장소까지 걸리는 시간
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

	final resultRepository resultrepository;
	final TakenRepository takenRepository;
	final LunchRepository lunchRepository;
	
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
 
    
    private int ChangetoMiniute(String time){
        String[] temp = time.split(":");
        int hour = Integer.parseInt(temp[0]);
        int min = Integer.parseInt(temp[1]);

        return hour * 60 + min;
    }
    
    private String ComputeTime(String CurrentTime, int taketime, int PlayTime) {
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
    
    private int checkTime(String startasd, String endasd) {
		String returntime1 = scheduler.getReturn_time();
		String[] str = returntime1.split(":");
		returntime = Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]);
    	int startTime1 = ChangetoMiniute(startasd);
    	int endTime1 = ChangetoMiniute(endasd);
		if(endTime1<=720) { // 관광지 11시에 시작하는데 여기서 고른 관광지가 만약 15시에 끝나 그러면 12 - 14는 속하는 게 없으니까
			return 0;
		} else if(endTime1>=720 && endTime1<=840 && day_meal[days] < 1) {
			return 1;
		} else if(startTime1>=720 && startTime1<=840 && day_meal[days] < 1) { // 점심
			return 1;
		} else if(endTime1>=720 && endTime1<=840 && startTime1>=720 && startTime1<=840 && day_meal[days] < 1) { // 점심
			return 1;
		} else if(startTime1<=720 && endTime1>=840 && day_meal[days] < 1) { // 점심 12시 - 14시
			return 4;
		} else if(startTime1>=1080 && startTime1<=1200 && day_meal[days] < 2) { // 저녁 18시 - 20시
			return 2;
		} else if(endTime1>=1080 && endTime1<=1200 && day_meal[days] < 2) { // 저녁
			return 2;
		} else if(endTime1>=1080 && endTime1<=1200 && startTime1>=1080 && startTime1<=1200 && day_meal[days] < 2) { // 저녁
			return 2;
		} else if(startTime1<=1080 && endTime1>=1200 && day_meal[days] < 2) { // 저녁
			return 5;
		} else if(endTime1>=(returntime+30)) { // 집
			return 3;
		} else if(startTime1>=returntime) {
			return 3;
		} else {
			return 0;
		}
	}
    
    private void PrintPlaceList(PlaceList[] list, int z) {
    	//System.out.println("[" + place_list[0].getday() + "]");
    	//System.out.println(place_list[0].getplace() + "에서 " + place_list[0].gettaketime() + "분 이동");  
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
    		if(place_list[i].getstarttime().equals(scheduler.getDeparture_time()) && zdxc == 0) { // 11:00을 거르는데 쟤가 걸림 그러면
    			log.info("[" + place_list[i].getday() + "]");
				zdxc = 1;
    		}
    		else {
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
		List<result> results = resultrepository.findAll();
		log.info(results.toString());
		model.addAttribute("resultEntity", results);
    }

    public void ComputeNextPlace(long current_place, String current_time) {   // 시간을 통해 다음으로 할 행동을 정함, 현재 시간 또는 다음장소로 이동했을때의 시간을 조건으로 삼는다
        // 데이터베이스를 통해서 현재 위치에서 가장 가까운 장소를 찾고 거기까지 가는 시간을 받아옴(String or int 형식), (일단 int형식으로 받아온다고 가정)
    	// 방문여부가 Yes라면 패스    	
		int asd = 1;
    	long NextPlace; // 다음으로 이동할 장소의 ID를 받아옴
    	int TimetoNextPlace; // 이러면 30분이 걸린다고 가정 (실제 코드에서는 함수를 통해 시간을 받아옴)
    	int PlayTime; // 데이터베이스에서 다음 장소에서 노는 시간을 받아옴 (실제 코드에서는 함수를 통해 시간을 받아옴, NextPlace를 인자로 넘김, sql사용)
		
		if(count == 0) { // 처음 도착지점에서 출발할 때
			List<Taken> takenEntity = takenRepository.findFirsttourlocation(current_place);
			Taken taken = takenEntity.get(0);
			NextPlace = taken.getLunch2().getId();
			TimetoNextPlace = taken.getTime();
			PlayTime = taken.getLunch1().getTime();
		}
		else { // 나머지 전부. 현재 지점에서 제일 가까운 관광지
			List<Taken> takenEntity = takenRepository.findtourlocation(current_place);
			Taken taken;
			try {
				taken = takenEntity.get(0);
			} catch (Exception e) { // 점심 저녁은 남았는데 관광지가 없는 상황.
				while(true) {
					if(day_meal[days] < 1) { // 점심 먹어야함.
						Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
						PlayTime = lunch.getTime();
						if(ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) < 720) { // 12시 이전
							List<Taken> takenEntity1 = takenRepository.findFirstLunchlocation(current_place);
							Taken taken1;
							try {
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) { // 점심 먹어야하는데 갈 곳이 없는 상황
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
				
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for lunch = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							//데이터베이스에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
							takenRepository.updateLunchlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = "12:00";
						} else if(ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= 720 && ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) <= 840) { // 12시 - 14시
							List<Taken> takenEntity1 = takenRepository.findFirstLunchlocation(current_place);
							Taken taken1;
							try {
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) { // 점심 먹어야하는데 갈 곳이 없는 상황.
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
				
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for lunch = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							//데이터베이스에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
							takenRepository.updateLunchlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = ComputeTime(current_time, TimetoNextPlace, PlayTime);
						} else { // 14시 이후
							List<Taken> takenEntity1 = takenRepository.findFirstLunchlocation(current_place);
							Taken taken1;
							try {
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) { // 점심 먹어야하는데 갈 곳이 없는 상황.
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
				
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for lunch = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							//데이터베이스에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
							takenRepository.updateLunchlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = ComputeTime(current_time, TimetoNextPlace, PlayTime);
						}
					} else if(day_meal[days] < 2) { // 저녁 먹어야함.
						Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
						PlayTime = lunch.getTime();
						if(ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) < 1080) { // 18시 이전
							// 데이터테이블에서 정보를 뽑아옴(Dinner만)
							List<Taken> takenEntity1 = takenRepository.findFirstDinnerlocation(current_place);
							Taken taken1;
							try {
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) { // 저녁 먹어야하는데 갈 곳이 없는 상황.
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
							
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for dinner = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							// DB에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
							takenRepository.updateDinnerlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = "18:00";
							days++;
						} else if(ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= 1080 && ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) <= 1200) { // 18시 - 20시
							// 데이터테이블에서 정보를 뽑아옴(Dinner만)
							List<Taken> takenEntity1 = takenRepository.findFirstDinnerlocation(current_place);
							Taken taken1;
							try {
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) { // 저녁 먹어야하는데 갈 곳이 없는 상황.
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
							
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for dinner = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							// DB에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
							takenRepository.updateDinnerlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							days++;
						} else { // 20시 이후
							// 데이터테이블에서 정보를 뽑아옴(Dinner만)
							List<Taken> takenEntity1 = takenRepository.findFirstDinnerlocation(current_place);
							Taken taken1;
							try {
								taken1 = takenEntity1.get(0);
							} catch (Exception e1) { // 저녁 먹어야하는데 갈 곳이 없는 상황.
								break;
							}
							NextPlace = taken1.getLunch2().getId();
							TimetoNextPlace = taken1.getTime();
							PlayTime = taken1.getLunch1().getTime();
							
							String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							log.info("for dinner = " + current_time);
							log.info(ExpectTime);
							day_meal[days]++;
							// DB에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
							takenRepository.updateDinnerlocation(current_place);
	
							place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
							current_place = NextPlace;
							current_time = ComputeTime(current_time, TimetoNextPlace, PlayTime);
							days++;
						}
					} else { // 먹을 거 없음.
						break;
					}
				}
				Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 2);
					return;
				}
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 0); // no tour
    			return;
			}
			NextPlace = taken.getLunch2().getId();
			TimetoNextPlace = taken.getTime();
			PlayTime = taken.getLunch1().getTime();
		}
		
    	String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime); // 현재 시간이랑 걸리는 시간, 노는 시간을 함수로 넘겨서 다음 장소에서의 끝나는 시간을 구함
		log.info(current_time);
		log.info(ExpectTime);
    	
		if(days == traveldays) { // 마지막 날일경우
			List<Taken> takenEntity = takenRepository.findNotvisit(current_place);
			try {
				Taken taken = takenEntity.get(0);
			} catch (Exception e) { // 마지막 날인데 전부 visit이 0이 아님 전부 다 갔음.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 2);
					return;
				}
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 1); // no tour in last day
    			return;
			}
			
    		if(checkTime(current_time, ExpectTime) == 3) { // 출력문 // 마지막 날 아직 갈 곳이 남았는데 시간이 늦은 상황.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 2);
					return;
				}
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 2); // no time 
    			return;
    		}
    	}
		
		if(jud_day != days) { // 다음날 이동할 첫 장소 알고리즘만 추가==========================================================================================================================
			if((checkTime(current_time, ExpectTime) == 1)){ // 현재 시간 또는 다음 장소에 도착할 시간이 점심 또는 저녁시간이라면(1이면 점심, 2면 저녁, 0이면 패스)
				// 데이터테이블에서 정보를 뽑아옴(Lunch만)
				List<Taken> takenEntity = takenRepository.findFirstLunchlocation(current_place);
				Taken taken;
				try {
					taken = takenEntity.get(0);
				} catch (Exception e) { // 점심 먹어야하는데 갈 곳이 없는 상황.
					Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
					PlayTime = lunch.getTime();
					if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
						PrintPlaceList(place_list, 3);
						return;
					}
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
    	
		if((checkTime(current_time, ExpectTime) == 1)){ // 현재 시간 또는 다음 장소에 도착할 시간이 점심 또는 저녁시간이라면(1이면 점심, 2면 저녁, 0이면 패스)
        	// 데이터테이블에서 정보를 뽑아옴(Lunch만)
			List<Taken> takenEntity = takenRepository.findFirstLunchlocation(current_place);
			Taken taken;
			try {
				taken = takenEntity.get(0);
			} catch (Exception e) { // 점심 먹어야하는데 갈 곳이 없는 상황.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 3);
					return;
				}
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
    		//데이터베이스에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
			takenRepository.updateLunchlocation(current_place);
        }
    	else if((checkTime(current_time, ExpectTime) == 2)) {
    		// 데이터테이블에서 정보를 뽑아옴(Dinner만)
			List<Taken> takenEntity = takenRepository.findFirstDinnerlocation(current_place);
			Taken taken;
			try {
				taken = takenEntity.get(0);
			} catch (Exception e) { // 저녁 먹어야하는데 갈 곳이 없는 상황.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 4);
					return;
				}
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
    		// DB에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
			takenRepository.updateDinnerlocation(current_place);
			if(ChangetoMiniute(ComputeTime(current_time, TimetoNextPlace, PlayTime))>returntime) {
				days++;
				TimetoNextPlace = 0;
				current_time = scheduler.getDeparture_time(); // 시작시간으로 초기화
				asd = 0;
			}
        }
        else if (checkTime(current_time, ExpectTime) == 3) { // 숙소로 돌아갈 시간이 되었다면
			Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
			if(lunch.getIndex()==2) {
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
				takenRepository.updateTourlocation(current_place);
			}
            days++;	// 여행 날짜를 하나 증가시킴
            TimetoNextPlace = 0;
            current_time = scheduler.getDeparture_time(); // 시작시간으로 초기화
			asd = 0;
			log.info("1 day left ------------------------------------------");
        }
		else if (checkTime(current_time, ExpectTime) == 4) {
			List<Taken> takenEntity = takenRepository.findFirstLunchlocation(current_place);
			Taken taken;
			try {
				taken = takenEntity.get(0);
			} catch (Exception e) { // 점심 먹어야하는데 갈 곳이 없는 상황.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 3);
					return;
				}
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 3); // no lunch
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
    		// 데이터테이블에서 정보를 뽑아옴(Dinner만)
			List<Taken> takenEntity = takenRepository.findFirstDinnerlocation(current_place);
			Taken taken;
			try {
				taken = takenEntity.get(0);
			} catch (Exception e) { // 저녁 먹어야하는데 갈 곳이 없는 상황.
				Lunch lunch = lunchRepository.findById(current_place).orElse(null); // nextid 가진 row값 챙겨오면
				PlayTime = lunch.getTime();
				if(ChangetoMiniute(current_time)>=returntime || ChangetoMiniute(ComputeTime(current_time, 0, PlayTime)) >= returntime+30) {
					PrintPlaceList(place_list, 4);
					return;
				}
				place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), 0, days-1);
    			PrintPlaceList(place_list, 4); // no dinner
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
        	// DB에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
			takenRepository.updateTourlocation(current_place);
			if(ChangetoMiniute(ComputeTime(current_time, TimetoNextPlace, PlayTime))>returntime) {
				days++;
				TimetoNextPlace = 0;
				current_time = scheduler.getDeparture_time(); // 시작시간으로 초기화
				asd = 0;
			}
        }

    	place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
		
		if(TimetoNextPlace != 0) { // 날짜 바뀔 때 현재 장소가 다음 장소로 바뀌면, 이제 다음 날 현재 장소 기준으로 서치 할 텐데 다음 날 넥스트 플레이스보다 현재 플레이스가 맞을 거 같아서
			current_place = NextPlace;
		}

		log.info("---------------------------------------------------------------------one place---------------------------------------");
    	
    	if (asd == 0)
    		ComputeNextPlace(current_place, ComputeTime(current_time, 0, 0));
    	else
    		ComputeNextPlace(current_place, ComputeTime(current_time, TimetoNextPlace, PlayTime));
    	return;
    }

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