package com.share.calendar.map.Controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
@RequestMapping(value = "/map")
public class MapController {
	
	private static final Logger logger = LoggerFactory.getLogger(MapController.class);	

	//////////////////////////////////  화면영역
	
	@RequestMapping(value = "/googleMap", method = RequestMethod.GET)
	public String googleMap(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController googleMap");
		
		return "/map/googleMap";
	}
	
	
	@RequestMapping(value = "/naverMap", method = RequestMethod.GET)
	public String naverMap(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController naverMap");
		
		return "/map/naverMap";
	}
	
}
