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
import com.share.calendar.sch.Vo.SchDutyListVo;
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

	//////////////////////////////////  ȭ�鿵��
	
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

	//////////////////////////////////  API ����
	
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
	 * ������ ����ȸ 
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
	 * ������ ���/���� 
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
	 * ������ ���� 
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
	 * ������ �޷� 
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
    		
    		List<SchVo> schList = schListService.selectSchList(schListDto);				// ������ ����Ʈ
        	List<SchDutyVo> resultList = setCalendar(thisYear, thisMonth, thisDay);	// �޷� ����Ʈ
        	List<SchDutyVo> tempList = setCalendar(thisYear, thisMonth, thisDay);	// �޷� ����Ʈ
        	
        	for(int i=0; i<tempList.size(); i++)  {
        		for(int j=0; j<schList.size(); j++) {
        			String yearMonthDay = ""; 
        					
        			yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getSun());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setSun( resultList.get(i).getSun() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + "' target='_blank' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}

            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getMon());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setMon( resultList.get(i).getMon() + "<br>" 
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + "' target='new' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"          					
            					+ schList.get(j).getScheTitle() + "</a>"); 
            		}
            		
            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getTue());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setTue( resultList.get(i).getTue() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + "' target='newwin' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}

            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getWed());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setWed( resultList.get(i).getWed() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + "' target='newWin' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}
            		
            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getThu());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setThu( resultList.get(i).getThu() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + "' target='_blank' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}
            		
            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getFri());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setFri( resultList.get(i).getFri() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + "' target='_blank' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
            					+ schList.get(j).getScheTitle() );
            		}
            		
            		yearMonthDay = listSum( thisYear, thisMonth, tempList.get(i).getSat());
            		if(yearMonthDay.equals( schList.get(j).getScheDt() )) {
            			resultList.get(i).setSat( resultList.get(i).getSat() + "<br>"  
            					+ "<a href=/schInsertM.do?schId=" + schList.get(j).getScheNo() + "' target='_blank' onClick='window.open(this.href, '', 'width=500, height=480'); return false;'>"
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
	 * �Ķ���ͷ� �޷� ����� 
	 */
	public List<SchDutyVo> setCalendar(int thisYear, int thisMonth, int thisDay) {
		int endDay = 0;		// �ſ� ��������
		int sumDay = 0;		// ���ñ��� �������(�ſ� 1���� ������ ���ϱ�����)
		int firstWeek = 0;	// �ſ� 1���� ����
		
		String[] dayWeek = {"", "", "", "", "", "", ""};
		
		// ���ݱ��� ����� �⵵�� ����ϼ��� �߰�
		for(int i=1; i<thisYear; i++) {
			sumDay += 365;
			if( (i%4 == 0 && i%100 != 0) || i%400 == 0) {
				sumDay++;
			}
		}
		
		// ���� ����� ������ ����ϼ��� �߰�
		for(int i=1; i<thisMonth; i++) {
    		if(i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
    			endDay = 31;
    		} else if(i == 2) {
    			if( (thisYear%4 == 0 && thisYear%100 != 0) || thisYear%400 == 0) {	// �����̸�
        			endDay = 29;
    			} else {
        			endDay = 28;
    			}
    		} else {
    			endDay = 30;
    		}
    		
    		sumDay += endDay;		// ��������� ������ ������ ����
		}
		
		//	��� ����� ��¥ �߰�
		//	sumDay += thisDay;

		/*
		 * ���� �̴� 1���� ������ ���Դ�.
		 * firstWeek�� ���� �����̸� �ش� ���� 1����
		 *  0  1  2  3  4  5  6
		 * �� �� ȭ �� �� �� �� 
		 */
		firstWeek = ( (sumDay % 7) + 1) % 7;	//	������ ������ ���� + 1 = �̹��� 1��... �� ����

		// �̹��� ������ ��¥
		if(thisMonth == 1 || thisMonth == 3 || thisMonth == 5 || thisMonth == 7 || thisMonth == 8 || thisMonth == 10 || thisMonth == 12) {
			endDay = 31;
		} else if(thisMonth == 2) {
			if( (thisYear%4 == 0 && thisYear%100 != 0) || thisYear%400 == 0) {	// �����̸�
    			endDay = 29;
			} else {
    			endDay = 28;
			}
		} else {
			endDay = 30;
		}

		// �޷� �Ǿ� ��ĭ
		int checkWeek = 0;

		for(int i=0; i<firstWeek; i++) {
			dayWeek[checkWeek] = "";
			checkWeek++;
		}
		
    	SchDutyVo schDutyVo = new SchDutyVo();
    	List<SchDutyVo> calList = new ArrayList<SchDutyVo>();

		// ���Ϻ� ��¥
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

		// �������� ����
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
	
	//YY, M, D �� �޾Ƽ� YYMMDD ����� ����
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
