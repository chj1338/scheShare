package com.share.calendar.sys.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SysScheduler {
	private static final Logger logger = LoggerFactory.getLogger(SysScheduler.class);	

	/*
	 * @Scheduled(cron = "0 50 21 * * 1-5") //월~금까지 오후 9시50분에 실행
	 * cron = "초 분 시 날 달 요일"
	 * cron = "0-59 0-59 0-23 1-31 1-12 0-6"
	 * */	
	@Scheduled(cron = "0 0/30 * * * * ")
	public void cronTest1() {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss"); 
			String str=sdf.format(new Date()); 
//			logger.debug("================ 자동 스케쥴 테스트 : {}", str);
			
			// 로또 추천
        	Random random = new Random();
        	int[] lottery = {0,0,0,0,0,0};
        	
        	for(int i=0; i<6; i++) {
        		int chk_num = 0;
        		int temp_num = random.nextInt(45) + 1;
        		
        		for(int j=0; j<=i; j++) {
            		if(temp_num == lottery[j]) {
        				chk_num++;
        			}
        		}
        		
        		if(chk_num == 0) {
        			lottery[i] = temp_num;
        		} else {
        			i--;
        		}
        	}
        	logger.debug("[{}]자동 스케쥴 테스트===== 추천 : {}", str, lottery);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
