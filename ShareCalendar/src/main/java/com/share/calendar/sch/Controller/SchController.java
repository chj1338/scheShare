package com.share.calendar.sch.Controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.calendar.sch.Dto.SchListDto;
import com.share.calendar.sch.Service.SchListService;
import com.share.calendar.sch.Vo.SchDutyVo;
import com.share.calendar.sch.Vo.SchVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SchController {
	
	private static final Logger logger = LoggerFactory.getLogger(SchController.class);	
	
	@Autowired
	private SchListService schListService;
	
	@Autowired
	private SchListDto schListDto;

	//////////////////////////////////  화면영역
	
	@RequestMapping(value = "/schListM", method = RequestMethod.GET)
	public String schListM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController schListM");
		
		return "sch/schListM";
	}
	
	@RequestMapping(value = "/schInsertM", method = RequestMethod.GET)
	public String schInsertM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController schInsertM");
		
		String schId = request.getParameter("schId");
		logger.info("===== SchController schInsertM {}", schId);
		model.addAttribute("schId", schId);
		
		return "sch/schInsertM";
	}
	
	@RequestMapping(value = "/schDutyM.do", method = RequestMethod.GET)
	public String schDutyM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController schDutyM");
		
		return "sch/schDutyM";
	}

	//////////////////////////////////  API 영역
	
	@ResponseBody
    @RequestMapping(value = "/sch/getScheduleList", method = RequestMethod.POST)
    public Map<String, Object> getScheduleList (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= getScheduleList Controller Start !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String schDtFrom = new String(request.getParameter("schDtFrom").getBytes("ISO-8859-1"), "UTF-8");
    		String schDtTo 	= new String(request.getParameter("schDtTo").getBytes("ISO-8859-1"), "UTF-8");
    		String schTitle 		= new String(request.getParameter("schTitle").getBytes("ISO-8859-1"), "UTF-8");
    		String schContent 	= new String(request.getParameter("schContent").getBytes("ISO-8859-1"), "UTF-8");
    		
    		logger.debug("===== schDtFrom : {}", schDtFrom);
    		logger.debug("===== schDtTo : {}", schDtTo);
    		logger.debug("===== schTitle : {}", schTitle);
    		logger.debug("===== schContent : {}", schContent); 
    		
    		schListDto.setSchDtFrom(schDtFrom);
    		schListDto.setSchDtTo(schDtTo);
    		schListDto.setSchTitle(schTitle);
    		schListDto.setSchContent(schContent);
    		
    		List<SchVo> list = schListService.selectSchList(schListDto);

            resultMap.put("resultData", list);
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error : {}", e.getMessage());
            
            resultMap.put("resultCd", "9999");
            resultMap.put("resultMsg", e.getMessage());
        } finally {
        	;
        }
        
        return resultMap;
	}
	
	
	/*
	 * 스케쥴 상세조회 
	 */
	@ResponseBody
    @RequestMapping(value = "/sch/getSchDetailData", method = RequestMethod.POST)
    public Map<String, Object> getSchDetailData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= getScheduleList Controller Start !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String schId = new String(request.getParameter("schId"));
    		logger.debug("===== schId : {}", schId);
    		
    		schListDto.setSchId(schId);
    		
    		List<SchVo> list = schListService.selectSchDetail(schListDto);
    		
            resultMap.put("resultData", list);
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error : {}", e.getMessage());
            
            resultMap.put("resultCd", "9999");
            resultMap.put("resultMsg", e.getMessage());
        } finally {
        	;
        }
        
        return resultMap;
	}
	
	
	/*
	 * 스케쥴 등록/수정 
	 */
	@ResponseBody
    @RequestMapping(value = "/sch/schInsertData", method = RequestMethod.POST)
    public Map<String, Object> schInsertData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= SchController schInsertData !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String schId = request.getParameter("schId");
    		String schDt = request.getParameter("schDt");
    		String schSe = request.getParameter("schSe");
    		String schTitle = new String(request.getParameter("schTitle").getBytes("ISO-8859-1"), "UTF-8");
    		String schContent = new String(request.getParameter("schContent").getBytes("ISO-8859-1"), "UTF-8");
    		
    		HttpSession session = request.getSession();
    		String registId = session.getAttribute("loginId").toString();
    		
    		logger.debug("===== schId : {}", schId);
    		logger.debug("===== schTitle : {}", schTitle);
    		logger.debug("===== schContent : {}", schContent); 
    		
    		schListDto.setSchId(schId);
    		schListDto.setSchDtFrom(schDt);
    		schListDto.setSchSe(schSe);
    		schListDto.setSchTitle(schTitle);
    		schListDto.setSchContent(schContent);
    		schListDto.setRegistId(registId);
    		
    		if(schId.equals("") || schId == null) {
    			schId = schListService.selectNewSchId();
    			schListDto.setSchId(schId);
    			
    			schListService.insertSchedule(schListDto);
    		} else {
    			schListService.updateSchedule(schListDto);
    		}
    		
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error : {}", e.getMessage());
            
            resultMap.put("resultCd", "9999");
            resultMap.put("resultMsg", e.getMessage());
        } finally {
        	;
        }
        
        return resultMap;
	}
		
	
	/*
	 * 스케쥴 삭제 
	 */
	@ResponseBody
    @RequestMapping(value = "/sch/schDeleteData", method = RequestMethod.POST)
    public Map<String, Object> schDeleteData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= SchController schDeleteData !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String schId = request.getParameter("schId");
    		
    		logger.debug("===== schId : {}", schId);

    		schListDto.setSchId(schId);

   			schListService.deleteSchedule(schListDto);

            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error : {}", e.getMessage());
            
            resultMap.put("resultCd", "9999");
            resultMap.put("resultMsg", e.getMessage());
        } finally {
        	;
        }
        
        return resultMap;
	}

	/*
	 * 스케쥴 달력 
	 */
	@ResponseBody
	@RequestMapping(value = "/sch/schDutyData.do", method = RequestMethod.POST)
	public Map<String, Object> schDutyData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===== SchController schDutyData");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	String paramYear = request.getParameter("paramYear");
        	String paramMonth = request.getParameter("paramMonth");
        	
    		Calendar cal = Calendar.getInstance();
    		int thisYear = cal.get(Calendar.YEAR);
    		int thisMonth = cal.get(Calendar.MONTH) + 1;
    		int thisDay = cal.get(Calendar.DATE);
    		
    		if(!paramYear.equals("") && paramYear != null) {
    			thisYear = Integer.parseInt(paramYear);
    		}
    		
    		if(!paramMonth.equals("") && paramMonth != null) {
    			thisMonth = Integer.parseInt(paramMonth);
    		}
        	
        	String schDtFrom = thisYear + "" + thisMonth + "01";
        	String schDtTo = thisYear + "" + thisMonth + "31";
        	if(thisMonth < 0) {
        		schDtFrom = thisYear + "0" + thisMonth + "01";
            	schDtTo = thisYear + "0" + thisMonth + "31";
        	}
        	
        	logger.info("===== schDtFrom {}", schDtFrom);
        	logger.info("===== schDtTo {}", schDtTo);
        	
        	SchListDto schListDto = new SchListDto();
    		schListDto.setSchDtFrom(schDtFrom);
    		schListDto.setSchDtTo(schDtTo);
    		schListDto.setSchTitle("");
    		schListDto.setSchContent("");
    		
    		List<SchVo> schList = schListService.selectSchList(schListDto);				// 스케쥴 리스트
        	List<SchDutyVo> resultList = setCalendar(thisYear, thisMonth, thisDay);	// 달력 리스트
        	List<SchDutyVo> tempList = setCalendar(thisYear, thisMonth, thisDay);	// 달력 리스트
        	
        	for(int i=0; i<tempList.size(); i++)  {
        		for(int j=0; j<schList.size(); j++) {
        			String yearMonthDay = ""; 
        					
        			yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getSun());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setSun( resultList.get(i).getSun() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + " target='_blank' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}

            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getMon());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setMon( resultList.get(i).getMon() + "<br>" 
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + " target='new' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"          					
            					+ schList.get(j).getScheTitle() + "</a>"); 
            		}
            		
            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getTue());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setTue( resultList.get(i).getTue() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + " target='newwin' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}

            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getWed());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setWed( resultList.get(i).getWed() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + " target='newWin' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}
            		
            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getThu());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setThu( resultList.get(i).getThu() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + " target='_blank' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}
            		
            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getFri());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setFri( resultList.get(i).getFri() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + " target='_blank' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}
            		
            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getSat());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setSat( resultList.get(i).getSat() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + " target='_blank' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}

        		}
        	}
    		
    		
            resultMap.put("resultData", resultList);
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error : {}", e.getMessage());
            
            resultMap.put("resultCd", "9999");
            resultMap.put("resultMsg", e.getMessage());
        } finally {
        	;
        }
        
        return resultMap;
	}
	
	
	/*
	 * 파라메터로 달력 만들기 
	 */
	public List<SchDutyVo> setCalendar(int thisYear, int thisMonth, int thisDay) {
		int endDay = 0;		// 매월 마지막일
		int sumDay = 0;		// 오늘까지 경과일자(매월 1일의 요일을 구하기위해)
		int firstWeek = 0;	// 매월 1일의 요일
		
		String[] dayWeek = {"", "", "", "", "", "", ""};
		
		// 지금까지 경과한 년도로 경과일수를 추가
		for(int i=1; i<thisYear; i++) {
			sumDay += 365;
			if( (i%4 == 0 && i%100 != 0) || i%400 == 0) {
				sumDay++;
			}
		}
		
		// 올해 경과한 월수로 경과일수를 추가
		for(int i=1; i<thisMonth; i++) {
    		if(i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
    			endDay = 31;
    		} else if(i == 2) {
    			if( (thisYear%4 == 0 && thisYear%100 != 0) || thisYear%400 == 0) {	// 윤년이면
        			endDay = 29;
    			} else {
        			endDay = 28;
    			}
    		} else {
    			endDay = 30;
    		}
    		
    		sumDay += endDay;		// 여기까지가 지난달 마지막 일자
		}
		
		//	당월 경과한 날짜 추가(오늘날짜 구할때만)
		//	sumDay += thisDay;

		/*
		 * 드디어 이달 1일의 요일이 나왔다.
		 * firstWeek이 다음 숫자이면 해당 월의 1일은
		 *  0  1  2  3  4  5  6
		 * 일 월 화 수 목 금 토 
		 */
		firstWeek = ( (sumDay % 7) + 1) % 7;	//	지난달 마지막 요일 + 1 = 이번달 1일... 의 요일

		// 이번달 마직말 날짜
		if(thisMonth == 1 || thisMonth == 3 || thisMonth == 5 || thisMonth == 7 || thisMonth == 8 || thisMonth == 10 || thisMonth == 12) {
			endDay = 31;
		} else if(thisMonth == 2) {
			if( (thisYear%4 == 0 && thisYear%100 != 0) || thisYear%400 == 0) {	// 윤년이면
    			endDay = 29;
			} else {
    			endDay = 28;
			}
		} else {
			endDay = 30;
		}

		// 달력 맨앞 빈칸
		int checkWeek = 0;

		for(int i=0; i<firstWeek; i++) {
			dayWeek[checkWeek] = "";
			checkWeek++;
		}
		
    	SchDutyVo schDutyVo = new SchDutyVo();
    	List<SchDutyVo> calList = new ArrayList<SchDutyVo>();

		// 요일별 날짜
		for(int i=1; i<=endDay; i++) {
			dayWeek[checkWeek] = i + "";
			checkWeek++;
			
			if(checkWeek == 7) {
	    		schDutyVo.setThisYear(thisYear + "");
	    		schDutyVo.setThisMonth(thisMonth + "");
	    		schDutyVo.setThisDay(thisDay + "");

				schDutyVo.setSun(dayWeek[0]);
				schDutyVo.setMon(dayWeek[1]);
				schDutyVo.setTue(dayWeek[2]);
				schDutyVo.setWed(dayWeek[3]);
				schDutyVo.setThu(dayWeek[4]);
				schDutyVo.setFri(dayWeek[5]);
				schDutyVo.setSat(dayWeek[6]);

				calList.add(schDutyVo);
				schDutyVo = new SchDutyVo();
				checkWeek = 0;
			}
		}

		// 마지막주 셋팅
		if(checkWeek < 7 && checkWeek != 0) {
    		while(checkWeek < 7) {
    			dayWeek[checkWeek] = "";
    			checkWeek++;
    		}
    		
    		schDutyVo.setThisYear(thisYear + "");
    		schDutyVo.setThisMonth(thisMonth + "");
    		schDutyVo.setThisDay(thisDay + "");
    		
			schDutyVo.setSun(dayWeek[0]);
			schDutyVo.setMon(dayWeek[1]);
			schDutyVo.setTue(dayWeek[2]);
			schDutyVo.setWed(dayWeek[3]);
			schDutyVo.setThu(dayWeek[4]);
			schDutyVo.setFri(dayWeek[5]);
			schDutyVo.setSat(dayWeek[6]);
    		
			calList.add(schDutyVo);    				
		}
		
		return calList;		
	}
	
	//YY, M, D 를 받아서 YYMMDD 형대로 변경
	public String listSum(int thisYear, int thisMonth, String thisDay) {
		String yearMonthDay = "";

		if(thisDay.equals("") || thisDay == null) {
			yearMonthDay = "";
		} else {
			if(thisMonth < 10) {
				yearMonthDay = thisYear + "0" + thisMonth;
			} else {
				yearMonthDay = thisYear + "" + thisMonth + "";
			}
			
			if(Integer.parseInt(thisDay) < 10) {
				yearMonthDay = yearMonthDay + "0" + thisDay;
			} else {
				yearMonthDay = yearMonthDay + "" + thisDay;
			}
		}
		
		return yearMonthDay;
	}
}
