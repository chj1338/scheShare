package com.share.calendar.bible.Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.calendar.article.Vo.searchArticleListVo;
import com.share.calendar.bible.Vo.bibleListVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BibleController {
	
	private static final Logger logger = LoggerFactory.getLogger(BibleController.class);	

	//////////////////////////////////  화면영역

	
	@RequestMapping(value = "/bible/bibleViewM.do", method = RequestMethod.GET)
	public String bibleViewM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController bibleViewM");
		
		return "/bible/bibleViewM";
	}

	//////////////////////////////////  API 영역

	/*
	 * 스케쥴 달력 
	 */
	@ResponseBody
	@RequestMapping(value = "/bible/bibleSearchData.do", method = RequestMethod.POST)
	public Map<String, Object> bibleSearchData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===== SchController bibleSearchData");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List resultList = null; 
		
        try {
        	String paramBook = request.getParameter("paramBook");
        	String paramPage = request.getParameter("paramPage");
        	
    		String thisBook = "창";
    		int thisPage = 1;
    		
    		if(!paramBook.equals("") && paramBook != null) {
    			thisBook = paramBook;
    		}
    		
    		if(!paramPage.equals("") && paramPage != null) {
    			thisPage = Integer.parseInt(paramPage);
    		}
        	        	
        	logger.info("===== thisBook {}", thisBook);
        	logger.info("===== thisPage {}", thisPage);
        	
        	resultMap.put("thisBook", thisBook);
        	resultMap.put("thisPage", thisPage);
        	
        	resultList = fileReader(thisBook, thisPage);
        	logger.debug("==== 전체 page 수 : {}", resultList.size());
        	
            resultMap.put("resultData", resultList);
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
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
	

	public List fileReader(String thisBook, int thisPage) throws Exception {
    	logger.debug("================ 파일읽기 시작");
		       
//	    String fileName = "C:/업무자료/개역한글판성경.txt";	
	    String fileName = "C:/temp/bible.txt";	

		FileInputStream fis = null;
		BufferedReader br = null;

    	List<bibleListVo> resultList = new ArrayList<bibleListVo>();
		
	    // 파일 읽기
	    try {
	    	fis = new FileInputStream(fileName); 
	        br = new BufferedReader(new InputStreamReader(fis, "EUC-KR"));

	        String line = new String();	        
	        int cnt = 0;

	        while( (line=br.readLine()) != null) {
    			String rowData[] = line.split(" ");
    			String colData[] = rowData[0].split(":");
    			
    			if(colData[0].equals(thisBook + thisPage)) {
        			logger.debug("=================" + rowData[0] + " " + colData[0] + " " + colData[1]);
        			
    				bibleListVo bl = new bibleListVo();
    				cnt++;

    				String tempContent = line.replaceAll(rowData[0]+" ", "");
    				
    				bl.setNum(cnt);
    				bl.setBookIndex(rowData[0]);
    				bl.setBibleContent(tempContent);

        			resultList.add(bl);
    			}
	        }

	        logger.debug("================ 정상종료");

	    } catch (IOException e) { 
	    	logger.debug("================ 파일읽기 오류 \n " + e.getMessage());
	    	e.printStackTrace();
	    } finally {
	        br.close();
	    }
	    
	    return resultList;

	}


}
