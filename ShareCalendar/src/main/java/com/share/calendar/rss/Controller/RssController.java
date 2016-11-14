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
        	logger.debug("===== keyword : " + keyword);

			List<RssListVo> resultList = new ArrayList<RssListVo>();
			
//			resultList.addAll( getMessage("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1114060500", 		keyword, "날씨") );
			
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/newsflash.xml", 		keyword, "JTBC 속보") );

			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/politics.xml", 			keyword, "JTBC 정치") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/economy.xml", 			keyword, "JTBC 경제") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/international.xml", 		keyword, "JTBC 국제") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/society.xml", 			keyword, "JTBC 사회") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/culture.xml", 			keyword, "JTBC 문화") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/sports.xml", 				keyword, "JTBC 스포츠") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/entertainment.xml", 	keyword, "JTBC 연예") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/newsrank.xml", 			keyword, "JTBC 뉴스랭킹") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/newssite.xml", 			keyword, "JTBC 뉴스현장") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/politicaldesk.xml", 		keyword, "JTBC 정치부회의") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/morningand.xml", 		keyword, "JTBC 아침&") );
			resultList.addAll( getMessage("http://fs.jtbc.joins.com/RSS/fullvideo.xml", 			keyword, "JTBC 풀영상") );
			
			resultList.addAll( getMessage("http://rss.joins.com/joins_news_list.xml", 			keyword, "중앙일보 전체기사") );
			
			resultList.addAll( getMessage("http://rss.joins.com/joins_money_list.xml", 			keyword, "중앙일보 머니") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_topic_list.xml", 			keyword, "중앙일보 화제") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_sports_list.xml", 			keyword, "중앙일보 스포츠") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_star_list.xml", 				keyword, "중앙일보 스타") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_life_list.xml", 				keyword, "중앙일보 생활") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_politics_list.xml", 			keyword, "중앙일보 정치") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_world_list.xml", 			keyword, "중앙일보 지구촌") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_it_list.xml", 				keyword, "중앙일보 IT") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_opinion_list.xml", 			keyword, "중앙일보 사설") );
			
			resultList.addAll( getMessage("http://rss.cbs.co.kr/nocutnews.xml", 					keyword, "노컷뉴스") );
			resultList.addAll( getMessage("http://rss.donga.com/total.xml", 						keyword, "동아일보") );
			resultList.addAll( getMessage("http://www.chosun.com/site/data/rss/rss.xml", 	keyword, "조선일보") );
			resultList.addAll( getMessage("http://www.hani.co.kr/rss/", 							keyword, "한겨레") );
			resultList.addAll( getMessage("http://www.khan.co.kr/rss/rssdata/total_news.xml", keyword, "경향신문") );
					
			resultList.addAll( getMessage("http://rss.hankooki.com/economy/sk00_list.xml", 	keyword, "서울경제") );
			resultList.addAll( getMessage("http://rss.hankyung.com/economy.xml", 			keyword, "한국경제") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_ilgan_list.xml", 			keyword, "일간스포츠") );
			resultList.addAll( getMessage("http://rss.joins.com/joins_ilgan_list.xml", 			keyword, "일간스포츠 전체기사") );

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