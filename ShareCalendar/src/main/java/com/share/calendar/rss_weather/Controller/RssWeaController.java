package com.share.calendar.rss_weather.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.calendar.common.Util.commonUtil;
import com.share.calendar.rss_weather.Vo.RssWeaListVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RssWeaController {
	
	private static final Logger logger = LoggerFactory.getLogger(RssWeaController.class);
	
    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    private commonUtil comUtil;
	
    @Autowired 
    private Properties gProp;
    
	//////////////////////////////////  화면영역
	
	@RequestMapping(value = "/rss/rssWeaSelectM.do", method = RequestMethod.GET)
	public String rssWeaSelectM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== RssWeaController rssWeaSelectM");
		
		return "/rss/rssWeaSelectM";
	}
	
	
	//////////////////////////////////  API 영역
	
	@ResponseBody
	@RequestMapping(value = "/rssWeaSelectData.do", method = RequestMethod.POST)
	public Map<String, Object> rssWeaSelectData(Locale locale, 
														Model model, 
														HttpServletRequest request, 
														HttpSession session) throws Exception {
		logger.debug("===== RssWeaController rssWeaSelectData()");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
//        	String keyword = new String(request.getParameter("keyword").getBytes("ISO-8859-1"), "UTF-8");

			List<RssWeaListVo> resultList = new ArrayList<RssWeaListVo>();
			
			resultList.addAll( getMessage("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1114060500") );

            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
            resultMap.put("resultData", resultList);
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
	
	
	// 기사 발췌 및 키워드 검색
	public List<RssWeaListVo> getMessage(String URI) {
		List<RssWeaListVo> resultList = new ArrayList<RssWeaListVo>();
		
		try {
	        RssWeaFeedParser parser = new RssWeaFeedParser(URI);		// 정치
	        RssWeaFeed feed = parser.readFeed();
			
		    for (RssWeaFeedMessage message : feed.getMessages()) {
		    	logger.debug("===== RssController message : " + message);
		    	
		    	RssWeaListVo rssWeaListVo = new RssWeaListVo();
		    	
		    	rssWeaListVo.setHour(message.getHour());
		    	rssWeaListVo.setDay(message.getDay());
		    	rssWeaListVo.setTemp(message.getTemp());
		    	rssWeaListVo.setTmx(message.getTmx());
		    	rssWeaListVo.setTmn(message.getTmn());
		    	rssWeaListVo.setSky(message.getSky());
		    	rssWeaListVo.setPty(message.getPty());
		    	rssWeaListVo.setPop(message.getPop());
		    	rssWeaListVo.setWs(message.getWs());
		    	rssWeaListVo.setWd(message.getWd());
		    	rssWeaListVo.setReh(message.getReh());
		    	rssWeaListVo.setR12(message.getR12());
		    	rssWeaListVo.setS12(message.getS12());
		    	rssWeaListVo.setR06(message.getR06());
		    	rssWeaListVo.setS06(message.getS06());
		    	rssWeaListVo.setWfKor(message.getWfKor());
		    	rssWeaListVo.setWfEn(message.getWfEn());
		    	rssWeaListVo.setWdKor(message.getWdKor());
		    	rssWeaListVo.setWdEn(message.getWdEn());
		    	rssWeaListVo.setPubDate(message.getPubDate());
		    	
		    	resultList.add(rssWeaListVo);
		    }
		    
		    return resultList;
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error : {}", e.getMessage());
        } finally {
        	;
        }
        
	    return resultList;
	}
}