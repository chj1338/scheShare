package com.share.calendar.sys.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SysInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SysInterceptor.class);
        
		/**
		 * preHandle : Controller 실행 요청전
		 */
		@Override    
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {        	
			String URI = request.getRequestURI();
			String servletPath = request.getServletPath();        	
			String uri_target = servletPath.replaceAll(".do", "").replaceAll("/", "");
			
			logger.debug("===============================preHandler_URI : {}", URI);
			
			// 로그인 페이지가 아니면
			if( !URI.equals("/sysLoginProc.do") && !URI.equals("/login.do") ) {        		
			   
			// session검사
			HttpSession session = request.getSession();   
			String userId = (String)session.getAttribute("SCHE");
			logger.debug("===============================userId : {}", userId);
			
				if (userId == null || userId.equals("") || userId == "null" || userId.equals("null")) { 	    		   
					session.setAttribute("uri_target", uri_target);		// 로그인 후 페이지 이동을 위한 URI 저장
					response.sendRedirect("/login.do");  
					return false;
				}
			}
			
			return true;   
		}

		/**
		 * postHandle : view(jsp)로 forward되기 전에
		 */
/*
        @Override    
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {        
        	logger.debug("===============================postHandle {}", request.getRequestURI());

        }
*/
        
        
		/**
		 * afterCompletion : 모두 끝난뒤
		 */
/*
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {        
        	logger.debug("===============================afterCompletion {}", request.getRequestURI());            
        }
*/
}
