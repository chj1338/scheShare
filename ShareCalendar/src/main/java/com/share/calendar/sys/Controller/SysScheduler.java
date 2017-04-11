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
	 * cron = "  초    분   시    날    달   요일"
	 * cron = "0-59 0-59 0-23 1-31 1-12 0-6"
	 * cron = "   *     20    2     *     *    6"		// 매주 토요일 새벽 2:20
	 * cron = "   *      0  4-6     *     *    *"		// 매일 오후 4,5,6시
	 * cron = "   *      5  0/2     *     *    *"		// 매일 2시간간격으로 5분대에
	 * cron = "   *     15    1     1     *    *"		// 매월 1일 새벽 1:15
     * cron = "   *     30    0     1   1,7    *"		// 1,7월 1일 새벽 0:30
     * 
     * @Scheduled(fixedRate=60000)				// 매 60분마다 실행
	 * fixedDelay - 모든 실행이 끝난 후 설정된 시간 후에 메소드를 동작시킴 1000 = 1초~
	 * fixedRate -  정해진 시간마다 반복해서 실행함 역시 1000 = 1초~
	 * @Scheduled(cron="45 * * * * MON-FRI")
	 * "0 0 12 * * ?" 매일 12시(정오)에 실행
	 * "0 15 10 ? * *" 매일 오전 10시 15분에 실행
	 * "0 15 10 * * ? 2005" 2005년의 매일 오전 10시 15분에 실행
	 * "0 0/5 14,18 * * ?" 매일 오후 2시부터 오후 2시 55분까지 매 5분마다 실행 그리고 오후 6시부터 6시 55까지 매5분마다 실행
	 * "0 15 10 ? * MON-FRI" 매주 월화수목금 오전 10시 15분에 실행
	 * "0 15 10 L * ?" 매월 마지막날 오전 10시 15분에 실행
	 * "0 15 10 ? * 6L" 매월 마지막 금요일 오전 10시 15분에 실행
	 * "0 15 10 ? * 6L 2002-2005" 2002년부터 2005년까지 매월 마지막 금요일 오전 10시 15분에 실행
	 * "0 15 10 ? * 6#3" 매월 세번째 금요일 오전 10시 15분에 실행
	 * 
	 * - CronTrigger에서 작업주기 설정하기
	 * 1. Seconde(0-59) - * /
	 * 2. Minutes (0-59) - * /
	 * 3. Hours (0-23) - * /
	 * 4. Day-of-month (1-31) - * ? / L W C
	 * 5. Month (1-23 or JAN-DEC) - * /
	 * 6. Day-of-week (1-7 or SUN-SAT) - * ? / L C #
	 * 7. Year (optional, empty, 1970-2099) - * / 
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
