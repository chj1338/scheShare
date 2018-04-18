package com.share.calendar.sys.Controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class SysController {
	
	private static final Logger logger = LoggerFactory.getLogger(SysController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		session = request.getSession(false);
		if(session == null){
			logger.error("로그인 세션 없음"); 
			return "/login";
		}
		
		return "login";
	}

	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "login";
	}

	@RequestMapping(value = "/leftMenu", method = RequestMethod.GET)
	public String leftMenu(Locale locale, Model model) {
		logger.debug("SysController leftMenu");

		return "/main/leftMenu";
	}

	@RequestMapping(value = "/rightMenu", method = RequestMethod.GET)
	public String rightMenu(Locale locale, Model model) {
		logger.debug("SysController leftMenu");

		return "/main/rightMenu";
	}
	
	@RequestMapping(value = "/body", method = RequestMethod.GET)
	public String body(HttpServletRequest request, HttpServletResponse response, Locale locale, Model model) {
		logger.debug("SysController body");
		
		return "/main/body";
	}
	
	@RequestMapping(value = "/mainBoard", method = RequestMethod.GET)
	public String mainBoard(HttpServletRequest request, HttpServletResponse response, Locale locale, Model model) {
		logger.debug("SysController body");
		
		HttpSession session = request.getSession(true);
		String loginId = (String)session.getAttribute("loginId");
		String userNm = (String)session.getAttribute("userNm");
		String lastLoginDt = (String)session.getAttribute("lastLoginDt");
		
		model.addAttribute("userId", loginId );
		model.addAttribute("userNm", userNm );
		model.addAttribute("lastLoginDt", lastLoginDt );
		
		return "/main/mainBoard";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(Locale locale, Model model) {
		logger.debug("SysController leftMenu");

		return "/test";
	}
}
