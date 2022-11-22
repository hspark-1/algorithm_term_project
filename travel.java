import java.util.*;
import java.io.*;

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

public class travel{
	private static String arrival_time;
	private static int arrival_place;
	private static int traveldays;
	public int days = 1;
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
    	
    	time = Integer.toString(hour) + ":" + Integer.toString(min);
    	return time;
    }
    
    private int TimeCheck(String time) {
    	int intTime = ChangetoMiniute(time);
		if(intTime > 720 &&intTime < 840 )		// 점심시간 (12:00 ~ 14:00)
			return 1;
		else if (intTime >1080 && intTime < 1200)  // 저녁시간 (18:00 ~ 20:00)
			return 2;
		else if (intTime >1260)	// 복귀시간 (21:00 ~)
			return 3;
		else 
			return 0;
    }
    
    private void PrintPlaceList(PlaceList[] list) {
    	//System.out.println("[" + place_list[0].getday() + "]");
    	//System.out.println(place_list[0].getplace() + "에서 " + place_list[0].gettaketime() + "분 이동");  	
    	
    	for (int i = 0; i < count; i++) {
    		if(place_list[i].getstarttime().equals("09:00")) {
    			System.out.println("[" + place_list[i].getday() + "]");
    		}
    		else {
    			System.out.println(place_list[i].getplace() + " (" + place_list[i].getstarttime() + " ~ " + place_list[i].getendtime() + ")");
       			System.out.println(place_list[i].gettaketime() + "분 이동");
    		}
    	}
    }

    private void ComputeNextPlace(long current_place, String current_time) {   // 시간을 통해 다음으로 할 행동을 정함, 현재 시간 또는 다음장소로 이동했을때의 시간을 조건으로 삼는다
        // 데이터베이스를 통해서 현재 위치에서 가장 가까운 장소를 찾고 거기까지 가는 시간을 받아옴(String or int 형식), (일단 int형식으로 받아온다고 가정)
    	// 방문여부가 Yes라면 패스    	
    	long NextPlace = 1; // 다음으로 이동할 장소의 ID를 받아옴
    	int TimetoNextPlace = 30; // 이러면 30분이 걸린다고 가정 (실제 코드에서는 함수를 통해 시간을 받아옴)
    	int PlayTime = 30; // 데이터베이스에서 다음 장소에서 노는 시간을 받아옴 (실제 코드에서는 함수를 통해 시간을 받아옴, NextPlace를 인자로 넘김, sql사용)
    	String ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime); // 현재 시간이랑 걸리는 시간, 노는 시간을 함수로 넘겨서 다음 장소에서의 끝나는 시간을 구함
    	
    	if(count == 0) {
    		TimetoNextPlace = 30;
    		PlayTime = 0;
    		ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
    	}
    	else if(days == traveldays) { // 마지막 날일경우
    		if(TimeCheck(ExpectTime) == 3) {
    			PrintPlaceList(place_list);
    			return;
    		}
    	}
    	else if((TimeCheck(current_time) == 1 || TimeCheck(ExpectTime) == 1) && day_meal[days] != 1){ // 현재 시간 또는 다음 장소에 도착할 시간이 점심 또는 저녁시간이라면(1이면 점심, 2면 저녁, 0이면 패스)
        	// 데이터테이블에서 정보를 뽑아옴(Lunch만)
    		NextPlace = 2;
    		TimetoNextPlace = 20;

    		ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
    		day_meal[days]++;
    		//데이터베이스에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
        }
    	else if((TimeCheck(current_time) == 2 || TimeCheck(ExpectTime) == 2) && day_meal[days] != 2) {
    		// 데이터테이블에서 정보를 뽑아옴(Dinner만)
    		NextPlace = 3;
    		TimetoNextPlace = 15;
    		ExpectTime = ComputeTime(current_time, TimetoNextPlace, PlayTime);
    		day_meal[days]++;
    		// DB에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
        }
        else if (TimeCheck(current_time) == 3 || TimeCheck(ComputeTime(current_time, PlayTime, 0)) == 3){ // 숙소로 돌아갈 시간이 되었다면
            days++;	// 여행 날짜를 하나 증가시킴
            TimetoNextPlace = 0;
            current_time = "09:00"; // 시작시간으로 초기화
        }
        else {
        	// DB에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
        }
    	   
    	place_list[count++] = new PlaceList(current_place, current_time, ComputeTime(current_time, 0, PlayTime), TimetoNextPlace, days);
    	current_place = NextPlace;
    	
    	if (current_time.equals("09:00"))
    		ComputeNextPlace(current_place, ComputeTime(current_time, 0, 0));
    	else
    		ComputeNextPlace(current_place, ComputeTime(current_time, TimetoNextPlace, PlayTime));
    	return;
    }

    public static void main(String args[]) throws IOException {
        // gui에서 버튼을 누르면 알고리즘 작동 시작
        // ㄴ> travel tv = new travel();    
        // 데이터베이스에서 입력받은 값들을 불러옴 (시작시간, 시작지점, 여행일수, 끝나는시간등)
        arrival_time = "09:00";
        arrival_place = 0;
        traveldays = 4;
           
        travel tv = new travel();
        tv.ComputeNextPlace(arrival_place, arrival_time);
   }
}
