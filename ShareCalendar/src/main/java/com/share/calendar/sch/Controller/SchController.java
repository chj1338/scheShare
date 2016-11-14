package com.share.calendar.sch.Controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/sch")
public class SchController {
	
	private static final Logger logger = LoggerFactory.getLogger(SchController.class);	

	//////////////////////////////////  화면영역
	
	@RequestMapping(value = "/schListM", method = RequestMethod.GET)
	public String schListM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController schListM");
		
		return "/sch/schListM";
	}
	
	@RequestMapping(value = "/schInsertM", method = RequestMethod.GET)
	public String schInsertM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController schInsertM");
		
		return "/sch/schInsertM";
	}
	

	//////////////////////////////////  API 영역
	
	@ResponseBody
	@RequestMapping(value = "/schInsertData", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> schInsertData(Locale locale, 
														Model model, 
														HttpServletRequest request, 
														HttpSession session) throws Exception {
		logger.debug("===== SchController schInsertData()");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String sch_dt = request.getParameter("sch_dt");
    		String sch_title = request.getParameter("sch_title");
    		String sch_content = request.getParameter("sch_content");
    		
    		logger.debug("===== sch_dt : {}", sch_dt);
    		logger.debug("===== sch_title : {}", sch_title);
    		logger.debug("===== sch_content : {}", sch_content); 

            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
            resultMap.put("resultData", "resultData");
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
	
	@ResponseBody
	@RequestMapping(value = "/getScheduleList", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> getScheduleList(Locale locale, 
														Model model, 
														HttpServletRequest request, 
														HttpSession session) throws Exception {
		logger.debug("===== SchController getScheduleList()");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String sch_dt = request.getParameter("sch_dt");
    		String sch_title = request.getParameter("sch_title");
    		String sch_content = request.getParameter("sch_content");
    		
    		logger.debug("===== sch_dt : {}", sch_dt);
    		logger.debug("===== sch_title : {}", sch_title);
    		logger.debug("===== sch_content : {}", sch_content); 

            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
            resultMap.put("resultData", "resultData");
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
