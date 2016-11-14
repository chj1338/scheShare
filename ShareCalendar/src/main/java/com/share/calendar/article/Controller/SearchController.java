package com.share.calendar.article.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.calendar.article.Vo.searchArticleListVo;


@Controller
public class SearchController {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@RequestMapping(value = "/searchArticleList", method = RequestMethod.GET)
	public String searchArticleList3(Locale locale, HttpServletRequest request, Model model) {
		logger.debug("========= searchArticleList Controller Start !!!");		
       
       return "article/searchArticleList";
    }

	
	@ResponseBody
    @RequestMapping(value = "/article/searchArticleData", method = RequestMethod.POST)
    public Map<String, Object> searchArticleData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= searchArticleData Controller Start !!!");

	       Map<String, Object> resultMap = new HashMap<String, Object>();
		
	       String target = new String(request.getParameter("keyword").getBytes("ISO-8859-1"), "UTF-8");
	       logger.debug("========================== {}", target);

	       String url[] = {"http://www.sportsseoul.com", "http://www.chosun.com", "http://www.stoo.co.kr",      "http://www.ezday.co.kr", "http://www.naver.com", 
	    		            "http://www.nate.com",          "http://www.hani.co.kr",    "http://joongang.joins.com", "http://www.donga.com", "http://www.daum.net"};
	       String title[] = {"스포츠조선", "조선일보", "스포츠투데이", "이지데이", "네이버", 
		            		 "네이트",       "한겨레",   "중앙일보",      "동아일보",  "다음"};

/*	       
	       String url[] = {"http://www.naver.com", "http://www.nate.com", "http://www.daum.net"};
	       String title[] = {"네이버", "네이트", "다음"};
*/
	 	   String filePath = "D:/TEMP/"; //읽어올 파일명
	 	   String inputFileName = "articleList.txt";
	       
			// 기존 파일이 존재할 경우 파일 삭제
			File uf = new File(filePath + inputFileName);
			if(uf.exists()) {
				uf.delete();
			}
	       
			// 기사 검색해서 파일로 쓰기
	       for(int i=0; i<url.length; i++) {
	    	   new SearchTask(title[i], url[i], target); 
	       }
		
        FileReader fr = null;
        BufferedReader br = null;
        
	   // 파일 write가 끝났는지 체크
       File f = new File(filePath + inputFileName);       
       while(!f.canWrite()) { ; }
       
        try {
            //목록 
            List<searchArticleListVo> list = new ArrayList<searchArticleListVo>();
            
            // 파일읽기
            fr = new FileReader(filePath + inputFileName); //파일읽기객체생성     
            br = new BufferedReader(fr); //버퍼리더객체생성
            
            String line = null;            
            int num = 0;

            while((line=br.readLine())!=null){ //라인단위 읽기
            	logger.debug("========== {}", line);
            	
            	num++;
            	String[] result = line.split("\\|");
				 
				String site = result[0];
				String article = result[1];
				String articleLink = result[2];

				if(article.length() > 100) article = article.substring(0, 100);

	           	searchArticleListVo salist = new searchArticleListVo();

	           	salist.setNum(num);
	           	salist.setSite(site);
	           	salist.setArticle(article);
	           	salist.setArticleLink(articleLink);

	           	list.add(salist);
            }
            
            logger.debug("========== num {}", num);

            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
            resultMap.put("resultData", list);
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error : {}", e.getMessage());
            
            resultMap.put("resultCd", "9999");
            resultMap.put("resultMsg", e.getMessage());
        } finally {
        	try {
        		br.close();
        	} catch(Exception e) {
        		
        	}
        }
        
        return resultMap;
    }


}	// class