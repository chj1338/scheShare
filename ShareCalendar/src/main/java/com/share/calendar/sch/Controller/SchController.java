package com.share.calendar.sch.Controller;

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

            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
            resultMap.put("resultData", list);
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
	
	
	/* 스케쥴 상세조회 팝업
	 * 
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
    		
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
            resultMap.put("resultData", list);
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
	
	
	/* 스케쥴 상세조회 팝업
	 * 
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
		
}
