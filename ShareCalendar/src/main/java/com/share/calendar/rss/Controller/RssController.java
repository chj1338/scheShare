package com.share.calendar.rss.Controller;

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
import com.share.calendar.rss.Vo.RssListVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RssController {
	
	private static final Logger logger = LoggerFactory.getLogger(RssController.class);
	
    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    private commonUtil comUtil;
	
    @Autowired 
    private Properties gProp;
    
	//////////////////////////////////  화면영역
	
	@RequestMapping(value = "/rss/rssSelectM.do", method = RequestMethod.GET)
	public String lotSelectM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== RssController rssSelectM");
		
		return "/rss/rssSelectM";
	}
	
	
	//////////////////////////////////  API 영역
	
	@ResponseBody
	@RequestMapping(value = "/rssSelectData.do", method = RequestMethod.POST)
	public Map<String, Object> rssSelectData(Locale locale, 
														Model model, 
														HttpServletRequest request, 
														HttpSession session) throws Exception {
		logger.debug("===== RssController rssSelectData()");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	String keyword = new String(request.getParameter("keyword").getBytes("ISO-8859-1"), "UTF-8");
        	
        	String ciokorea = request.getParameter("ciokorea");
        	String jtbc_newsflash = request.getParameter("jtbc_newsflash");
        	String jtbc_politics = request.getParameter("jtbc_politics");
        	String jtbc_economy = request.getParameter("jtbc_economy");
        	String jtbc_international = request.getParameter("jtbc_international");
        	String jtbc_society = request.getParameter("jtbc_society");
        	String jtbc_culture = request.getParameter("jtbc_culture");
        	String jtbc_sports = request.getParameter("jtbc_sports");
        	String jtbc_entertainment = request.getParameter("jtbc_entertainment");
        	String jtbc_newsrank = request.getParameter("jtbc_newsrank");
        	String jtbc_newssite = request.getParameter("jtbc_newssite");
        	String jtbc_politicaldesk = request.getParameter("jtbc_politicaldesk");
        	String jtbc_morningand = request.getParameter("jtbc_morningand");
        	String jtbc_fullvideo = request.getParameter("jtbc_fullvideo");
        	String joins_news_list = request.getParameter("joins_news_list");
        	String nocutnews = request.getParameter("nocutnews");
        	String donga = request.getParameter("donga");
        	String chosun = request.getParameter("chosun");
        	String hani = request.getParameter("hani");
        	String khan_rss = request.getParameter("khan_rss");
        	String joins_ilgan_list = request.getParameter("joins_ilgan_list");

			List<RssListVo> resultList = new ArrayList<RssListVo>();
			
			logger.debug("===== RssController jtbc_newsflash : {}", jtbc_newsflash);
			
			if(ciokorea.equals("true"))				resultList.addAll( getMessage("http://www.ciokorea.com/rss/feed/index.php", 	keyword, "CIO Korea") );
			
			if(jtbc_newsflash.equals("true")) 		resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/newsflash.xml", 		keyword, "JTBC 속보") );
			if(jtbc_politics.equals("true"))			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/politics.xml", 			keyword, "JTBC 정치") );
			if(jtbc_economy.equals("true"))		resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/economy.xml", 			keyword, "JTBC 경제") );
			if(jtbc_international.equals("true"))	resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/international.xml", 		keyword, "JTBC 국제") );
			if(jtbc_society.equals("true"))			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/society.xml", 			keyword, "JTBC 사회") );
			if(jtbc_culture.equals("true"))			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/culture.xml", 			keyword, "JTBC 문화") );
			if(jtbc_sports.equals("true"))			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/sports.xml", 				keyword, "JTBC 스포츠") );
			if(jtbc_entertainment.equals("true"))	resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/entertainment.xml", 	keyword, "JTBC 연예") );
			if(jtbc_newsrank.equals("true"))		resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/newsrank.xml", 			keyword, "JTBC 뉴스랭킹") );
			if(jtbc_newssite.equals("true"))			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/newssite.xml", 			keyword, "JTBC 뉴스현장") );
			if(jtbc_politicaldesk.equals("true"))	resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/politicaldesk.xml", 		keyword, "JTBC 정치부회의") );
			if(jtbc_morningand.equals("true"))	resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/morningand.xml", 		keyword, "JTBC 아침&") );
			if(jtbc_fullvideo.equals("true"))			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/fullvideo.xml", 			keyword, "JTBC 풀영상") );
			
			if(joins_news_list.equals("true"))		resultList.addAll( getMessage("http://rss.joins.com/joins_news_list.xml", 			keyword, "중앙일보 전체기사") );
/*	
			resultList.addAll( getMessage("http://rss.joins.com/joins_money_list.xml", 			keyword, "중앙일보 머니") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_topic_list.xml", 			keyword, "중앙일보 화제") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_sports_list.xml", 			keyword, "중앙일보 스포츠") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_star_list.xml", 				keyword, "중앙일보 스타") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_life_list.xml", 				keyword, "중앙일보 생활") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_politics_list.xml", 			keyword, "중앙일보 정치") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_world_list.xml", 			keyword, "중앙일보 지구촌") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_it_list.xml", 				keyword, "중앙일보 IT") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_opinion_list.xml", 			keyword, "중앙일보 사설") );
*/			
			if(nocutnews.equals("true"))			resultList.addAll( getMessage("http://rss.cbs.co.kr/nocutnews.xml", 					keyword, "노컷뉴스") );
			if(donga.equals("true")) 				resultList.addAll( getMessage("http://rss.donga.com/total.xml", 						keyword, "동아일보") );
			if(chosun.equals("true"))				resultList.addAll( getMessage("http://www.chosun.com/site/data/rss/rss.xml", 	keyword, "조선일보") );
			if(hani.equals("true"))					resultList.addAll( getMessage("http://www.hani.co.kr/rss/", 							keyword, "한겨레") );
			if(khan_rss.equals("true"))				resultList.addAll( getMessage("http://www.khan.co.kr/rss/rssdata/total_news.xml", keyword, "경향신문") );
					
//			resultList.addAll( getMessage("http://rss.hankooki.com/economy/sk00_list.xml", 	keyword, "서울경제") );		// 2015년 이후 업데이트 안됨
//			resultList.addAll( getMessage("http://rss.hankyung.com/economy.xml", 			keyword, "한국경제") );		// 2012년 이후 업데이트 안됨
			if(joins_ilgan_list.equals("true"))		resultList.addAll( getMessage("http://rss.joins.com/joins_ilgan_list.xml", 			keyword, "일간스포츠") );

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
	public List<RssListVo> getMessage(String URI, String keyword, String guid) {
		List<RssListVo> resultList = new ArrayList<RssListVo>();
		
		try {
	        RssFeedParser parser = new RssFeedParser(URI);		// 정치
	        RssFeed feed = parser.readFeed();
			
		    for (RssFeedMessage message : feed.getMessages()) {
		    	RssListVo rssListVo = new RssListVo();
		    	
		    	rssListVo.setTitle(message.getTitle());
		    	rssListVo.setDiscription(message.getDescription());
		    	rssListVo.setLink(message.getLink());
		    	rssListVo.setAuthor(message.getAuthor());
		    	rssListVo.setGuid(guid);
		    	rssListVo.setPubDate(message.getPubDate());
	
		    	if(keyword == null) {
		    		resultList.add(rssListVo);
		    	} else if(rssListVo.getTitle().indexOf(keyword) >= 0) {
		    		resultList.add(rssListVo);
		    	}
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