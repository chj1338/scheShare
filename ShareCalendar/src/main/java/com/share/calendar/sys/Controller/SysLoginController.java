package com.share.calendar.sys.Controller;

import java.text.DateFormat;
import java.util.Date;
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

import com.share.calendar.sys.Dto.SysLoginDto;
import com.share.calendar.sys.Service.SysLoginService;
import com.share.calendar.sys.Vo.SysLoginVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SysLoginController  {
	
	private static final Logger logger = LoggerFactory.getLogger(SysLoginController.class);
	
	@Autowired
	private SysLoginService sysLoginService;
	
	@Autowired
	private SysLoginDto sysLoginDto;
	
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "login";
	}
	
	
	@RequestMapping(value = "/sysLoginProc.do", method = RequestMethod.POST)
	public String sysLoginProc(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		logger.debug("========= SysLoginController  sysLoginProc");

		// 세션체크
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(60*60);
		
		try {
			String loginId = new String(request.getParameter("loginId").getBytes("ISO-8859-1"), "UTF-8");
			String loginPW = new String(request.getParameter("loginPW").getBytes("ISO-8859-1"), "UTF-8");
			String clientIp = request.getParameter("clientIp");
			
			logger.debug("========= SysLoginController  loginId : " + loginId);
			logger.debug("========= SysLoginController  loginPW : " + loginPW);
			logger.debug("========= SysLoginController  clientIp : " + clientIp);

			sysLoginDto.setLoginId(loginId);
			sysLoginDto.setLoginPW(loginPW);
			sysLoginDto.setClientIp(clientIp);

			// ID가 없으면 로그인 화면으로
			if(loginId == null || loginId.equals("") || loginPW == null || loginPW.equals("")) {
				model.addAttribute("SYS_MSG", "ID와 PW를 모두 입력하셔야 합니다.");
				return "login";
			}
/*
			// 세션이 살아있으면 통과
			try {
				String userSession = session.getAttribute("SCHE").toString();
	
				if(userSession.equals(loginId)) {
					model.addAttribute("userId", loginId );					
					return "/main/mainBoard";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
*/

			// ID/PW 체크
			List<SysLoginVo> list = sysLoginService.selectSysLoginChk(sysLoginDto);
			
			int user_chk = Integer.parseInt( list.get(0).getUserCnt() );
			String userNm = list.get(0).getUserNm();
			String lastLoginDt = list.get(0).getLastLoginDt();
			
			logger.debug("========= user_chk : {}", user_chk);
			logger.debug("========= userNm : {}", userNm);
			logger.debug("========= lastLoginDt : {}", lastLoginDt);
			
			if( user_chk == 0 )  {
				model.addAttribute("SYS_MSG", "ID나 PW가 잘못되었습니다.");
				return "login";
			}

			sysLoginService.insertLoginHistory(sysLoginDto);	// 로그인 이력저장

			//신규 세션 생성
			session.removeAttribute("SCHE");
			session.setAttribute("SCHE", loginId);
			session.setAttribute("loginId", loginId);
			session.setAttribute("userNm", userNm);
			session.setAttribute("lastLoginDt", lastLoginDt);
			
			model.addAttribute("userId", loginId );
			model.addAttribute("userNm", userNm );
			model.addAttribute("lastLoginDt", lastLoginDt );

		}	
		catch (Exception e)
		{
			e.printStackTrace();
			model.addAttribute("SYS_MSG", "오류가 발생했습니다.");
			return "login";
		} 
		finally
		{
			
		}
		
		return "/main/mainBoard";
	}
	
	
	@RequestMapping(value = "/logout.do", method = RequestMethod.POST)
	public String logout(HttpServletRequest request, Locale locale, Model model) {
		logger.debug("============== logout !!!");
		
		HttpSession session = request.getSession();  
		//세션 유지시간(초))
		session.setMaxInactiveInterval(1);
		
		return "login";
	}
		
}
