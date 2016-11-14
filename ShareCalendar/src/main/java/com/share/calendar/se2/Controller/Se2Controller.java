package com.share.calendar.se2.Controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class Se2Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Se2Controller.class);
	
	/**
	 * 스마트에디터2 화면 띄우기.
	 */
	@RequestMapping(value = "/SmartEditor2.do", method = RequestMethod.GET)
	public String SmartEditor2(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) {
		logger.debug("***** Welcome to the SmartEditor2", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
//		return "/resources/smart_editor2/SmartEditor2.html";
		return "/se2/SmartEditor2";
	}
	
	
	/**
	 * 스마트에디터2 화면 띄우기.
	 */
	@RequestMapping(value = "/SmartEditor2.html", method = RequestMethod.GET)
	public String SmartEditor2html(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) {
		logger.debug("***** Welcome to the SmartEditor2", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "/se2/SmartEditor2";
	}
	
	
	/**
	 * 스마트에디터2 내용 저장
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/SmartEditor2_Save.do", method = RequestMethod.POST)
	public String SmartEditor2_Save(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws UnsupportedEncodingException {
		String content = "";

		// check IE url encoding - 젠장할 익스플로러
	    String ua = request.getHeader("User-Agent");

	    logger.debug("2 ***** 익스플로러 : {}", request.getHeader("User-Agent"));
	    
	    boolean isMSIE = ( ua != null && (ua.indexOf("MSIE") != -1 || ua.indexOf("Trident") != -1) );
	     
	    if(isMSIE) {
	    	logger.debug("***** 젠장할 익스플로러");
//	        request.setCharacterEncoding("KSC5601");
	        request.setCharacterEncoding("UTF-8");
	        content = request.getParameter("ir1");
	    }
	    else {
	    	logger.debug("***** 익스플로러 아님.");
	        request.setCharacterEncoding("UTF-8");
	        content = request.getParameter("ir1");
	    }
		
		logger.debug("***** ir1 : {}", content);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "/se2/SmartEditor2";
	}

		
}
