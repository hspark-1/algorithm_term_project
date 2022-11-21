import java.util.*;
import java.io.*;

class PlaceList {
	private String place;
	private int day;
	
	public PlaceList(String place, int day) {
		this.place = place;
		this.day = day;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
}

public class travel{
    private String current_time;
    private String current_place;
    private int traveldays;
    private int days = 1;
    private int count = 0;

    private PlaceList[] place_list;
    
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

    private int isEating(String time) {   // 점심 or 저녁을 먹어야되냐
    	return 1;
    }

    private boolean isReturn(String time) {   // 숙소로 돌아가야되냐
    	return true;
    }
    
    private boolean isGoHome(String time) {  // 여행을 끝내야하나
    	return true;
    }

    private PlaceList[] ComputeNextPlace(String current_time) {   // 시간을 통해 다음으로 할 행동을 정함, 현재 시간 또는 다음장소로 이동했을때의 시간을 조건으로 삼는다
        // 데이터베이스를 통해서 현재 위치에서 가장 가까운 장소를 찾고 거기까지 가는 시간을 받아옴(String or int 형식), (일단 int형식으로 받아온다고 가정)
    	// 방문여부가 Yes라면 패스
    	String NextPlace = ""; // 다음으로 이동할 장소를 받아옴
    	int TimetoNextPlace = 30; // 이러면 30분이 걸린다고 가정 (실제 코드에서는 함수를 통해 시간을 받아옴)
    	String PlayTime = ""; // 데이터베이스에서 다음 장소에서 노는 시간을 받아옴 (실제 코드에서는 함수를 통해 시간을 받아옴, NextPlace를 인자로 넘김, sql사용)
    	String ExpectTime = ComputeTime(current_time, TimetoNextPlace, ChangetoMiniute(PlayTime)); // 현재 시간이랑 걸리는 시간, 노는 시간을 함수로 넘겨서 다음 장소에서의 끝나는 시간을 구함
    	
    	
    	if(days == traveldays) { // 마지막 날일경우
    		if(isGoHome(ExpectTime)) {
    			
    		}
    	}
    	else if(isEating(current_time) == 1 || isEating(ExpectTime) == 1){ // 현재 시간 또는 다음 장소에 도착할 시간이 점심 또는 저녁시간이라면(1이면 점심, 2면 저녁, 0이면 패스)
        	// 데이터테이블에서 정보를 뽑아옴(Lunch만)
    		NextPlace = "";
    		TimetoNextPlace = 30;
    		PlayTime = "";
    		ExpectTime = ComputeTime(current_time, TimetoNextPlace, ChangetoMiniute(PlayTime));
    		
    		//데이터베이스에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
    		
    		current_place = NextPlace;
    		current_time = ExpectTime;
        }
    	else if(isEating(current_time) == 2 || isEating(ExpectTime) == 2) {
    		// 데이터테이블에서 정보를 뽑아옴(Dinner만)
    		NextPlace = "";
    		TimetoNextPlace = 30;
    		PlayTime = "";
    		ExpectTime = ComputeTime(current_time, TimetoNextPlace, ChangetoMiniute(PlayTime));
    		
    		// DB에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
    		
    		current_place = NextPlace;
    		current_time = ExpectTime;
        }
        else if (isReturn(current_time) || isReturn(ExpectTime)){ // 숙소로 돌아갈 시간이 되었다면
            days++;	// 여행 날짜를 하나 증가시킴
            current_time = "09:00"; // 시작시간으로 초기화
        }
        else {
        	// DB에서 NextPlace의 정보 중에서 방문여부를 Yes로 바꿈
        	
        	current_place = NextPlace;
    		current_time = ExpectTime;
        }
    	
    	place_list[count].setPlace(current_place);
    	place_list[count].setDay(days);
    	count++;
    	return place_list; // 방문한 장소를 반환 
    }

    public static void main(String args[]) throws IOException {
        // gui에서 버튼을 누르면 알고리즘 작동 시작
    	// ㄴ> travel tv = new travel();   
    	// 데이터베이스에서 입력받은 값들을 불러옴 (시작시간, 시작지점, 여행일수, 끝나는시간등)
    	String arrival_time = "";
    	String current_place = "";
    	int traveldays = 0;
    	String departure_time = ""; // 끝나는 시간은 이 클래스에서보다는 주홍님의 클래스(현 클래스에서의 함수는 isReturn)에서 사용하는 것이 편할 것으로 판단
    	
    	String current_time = arrival_time;
    }
}
