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

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.calendar.bible.Dto.BibleReadDto;
import com.share.calendar.bible.Dto.BibleReadSeqListDto;
import com.share.calendar.bible.Vo.bibleComboListVo;
import com.share.calendar.bible.Vo.bibleListVo;
import com.share.calendar.bible.Vo.bibleReadSeqListVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BibleController {
	
	private static final Logger logger = LoggerFactory.getLogger(BibleController.class);	
	
	static String fileName = "C:/temp/bible.txt";	
//	static String fileName = "C:/temp/KJV.txt";	
	
    @Autowired
    private SqlSession sqlSession;
	
	//////////////////////////////////  화면영역

	// 성경읽기
	@RequestMapping(value = "/bible/bibleViewM.do", method = RequestMethod.GET)
	public String bibleViewM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController bibleViewM");
		
		return "/bible/bibleViewM";
	}

	// 성경읽기 표
	@RequestMapping(value = "/bible/bibleReadM.do", method = RequestMethod.GET)
	public String bibleReadM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController bibleReadM");
		
		return "/bible/bibleReadM";
	}
	
	//////////////////////////////////  API 영역
	
	/*
	 * Combo용 성경리스트 및  최대 page 찾기
	 */
	@ResponseBody
	@RequestMapping(value = "/bible/bookComboList.do", method = RequestMethod.POST)
	public Map<String, Object> bookComboList (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===== SchController bookComboList");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		List<bibleComboListVo> list = sqlSession.selectList("BibleMapper.selectBibleComboList");
       	
            resultMap.put("resultData", list);
//            resultMap.put("resultTotal", list.size());
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
	
	
	/*
	 * 성경 최대 page 찾기
	 */
	@ResponseBody
	@RequestMapping(value = "/bible/bookCount.do", method = RequestMethod.POST)
	public Map<String, Object> bookCount (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===== SchController bookCount");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	//String paramBook = request.getParameter("paramBook");
        	String paramBook = new String(request.getParameter("paramBook").getBytes("ISO-8859-1"), "UTF-8");
    		String thisBook = "창";
    		
    		if(!paramBook.equals("") && paramBook != null) {
    			thisBook = paramBook;
    		}
     	
        	logger.info("===== thisBook {}", thisBook);

        	String resultData = maxPageCount(thisBook);
        	logger.debug("==== line 수 : {}", resultData);
        	
            resultMap.put("resultData", resultData);
            resultMap.put("thisBook", thisBook);
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
	
	/*
	 * 성경찾기
	 */
	@ResponseBody
	@RequestMapping(value = "/bible/bibleSearchData.do", method = RequestMethod.POST)
	public Map<String, Object> bibleSearchData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===== SchController bibleSearchData");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List resultList = null; 
		
        try {
        	String paramBook = new String(request.getParameter("paramBook").getBytes("ISO-8859-1"), "UTF-8");
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
            resultMap.put("thisBook", thisBook);
            resultMap.put("thisPage", thisPage);
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
	
	/*
	 * 검색한 단어로 성경찾기
	 */
	@ResponseBody
	@RequestMapping(value = "/bible/bibleSearchWord.do", method = RequestMethod.POST)
	public Map<String, Object> bibleSearchWord (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===== SchController bibleSearchWord");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List resultList = null; 
		
        try {
        	String paramBook = new String(request.getParameter("paramBook").getBytes("ISO-8859-1"), "UTF-8");
        	String paramPage = request.getParameter("paramPage");
        	String searchWord = new String(request.getParameter("searchWord").getBytes("ISO-8859-1"), "UTF-8");
        	
    		String thisBook = "";
    		int thisPage = 0;
    		
    		if(!paramBook.equals("") && paramBook != null) {
    			thisBook = paramBook;
    		}
    		
    		if(!paramPage.equals("") && paramPage != null) {
    			thisPage = Integer.parseInt(paramPage);
    		}
        	        	
        	logger.info("===== thisBook {}", thisBook);
        	logger.info("===== thisPage {}", thisPage);
        	logger.info("===== searchWord {}", searchWord);
        	
        	resultMap.put("thisBook", thisBook);
        	resultMap.put("thisPage", thisPage);
        	resultMap.put("searchWord", searchWord);
        	
        	resultList = fileReaderWord(thisBook, thisPage, searchWord);
        	logger.debug("==== 전체 page 수 : {}", resultList.size());
        	
            resultMap.put("resultData", resultList);
            resultMap.put("thisBook", thisBook);
            resultMap.put("thisPage", thisPage);
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

	public String maxPageCount(String thisBook) throws Exception {
    	logger.debug("================ 파일읽기 시작");

		FileInputStream fis = null;
		BufferedReader br = null;

        String pageCnt = "0";
        String maxPage = "0";		
        
	    // 파일 읽기
	    try {
	    	fis = new FileInputStream(fileName); 
	        br = new BufferedReader(new InputStreamReader(fis, "EUC-KR"));

	        String line = new String();	        

	        while( (line=br.readLine()) != null) {
    			String rowData[] = line.split(" ");
    			String colData[] = rowData[0].split(":");
    			
    			if(!maxPage.equals(colData[0]) && colData[0].indexOf(thisBook) > -1) {
    				maxPage = colData[0];
    			}
	        }
	        
	        pageCnt = maxPage.replaceAll(thisBook, "");

	        logger.debug("================ 정상종료");

	    } catch (IOException e) { 
	    	logger.debug("================ 파일읽기 오류 \n " + e.getMessage());
	    	e.printStackTrace();
	    } finally {
	        br.close();
	    }
	    
	    return pageCnt;
	}
	
	/*
	 * 성경읽기 기록 저장
	 */
	@ResponseBody
	@RequestMapping(value = "/bible/bibleSaveData.do", method = RequestMethod.POST)
	public Map<String, Object> bibleSaveData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===== SchController bibleSaveData");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	HttpSession session = request.getSession();
    		String registId = session.getAttribute("loginId").toString();
    		
        	String paramBook = new String(request.getParameter("paramBook").getBytes("ISO-8859-1"), "UTF-8");
        	String paramPage = request.getParameter("paramPage");
        	
        	BibleReadDto bibleReadDto = new BibleReadDto(); 
        	bibleReadDto.setParamBook(paramBook);
        	bibleReadDto.setParamPage(paramPage);
        	bibleReadDto.setUserId(registId);
        	
        	sqlSession.insert("BibleMapper.insertBibleReadData", bibleReadDto);

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
	
	/*
	 * 성경읽기표
	 */
	@ResponseBody
	@RequestMapping(value = "/bible/bibleRead.do", method = RequestMethod.POST)
	public Map<String, Object> bibleRead (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===== SchController bibleRead");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	HttpSession session = request.getSession();
    		String userId = session.getAttribute("loginId").toString();
    		
        	String paramSeq = request.getParameter("paramSeq");
        	if(paramSeq==null || paramSeq.equals("")) paramSeq="1";
        	
        	BibleReadSeqListDto bibleReadSeqListDto = new BibleReadSeqListDto();        	
        	bibleReadSeqListDto.setBookSeq(paramSeq);
        	bibleReadSeqListDto.setUserId(userId);
        	
    		List<bibleComboListVo> list = sqlSession.selectList("BibleMapper.selectBibleComboList");
        	List<bibleReadSeqListVo> readList = sqlSession.selectList("BibleMapper.selectBibleReadSeqList", bibleReadSeqListDto);
       	
            resultMap.put("resultData", list);
            resultMap.put("resultReadData", readList);
            
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
	
	// 책, 장 찾기
	public List fileReader(String thisBook, int thisPage) throws Exception {
    	logger.debug("================ 파일읽기 시작");

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
        			//logger.debug("=================" + rowData[0] + " " + colData[0] + " " + colData[1]);

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
	
	// 전체에서 문자 찾기
	public List fileReaderWord(String thisBook, int thisPage, String searchWord) throws Exception {
    	logger.debug("================ 파일읽기 시작");

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

//    			if(colData[0].equals(thisBook + thisPage)) {
    			if(line.indexOf(searchWord) > -1) {
        			//logger.debug("=================" + rowData[0] + " " + colData[0] + " " + colData[1]);

    				bibleListVo bl = new bibleListVo();
    				cnt++;

    				String tempContent = line.replaceAll(rowData[0]+" ", "");
    				//tempContent = tempContent.replaceAll(searchWord, "["+searchWord+"]");
    				tempContent = tempContent.replaceAll(searchWord, "<font color='red'><b>"+searchWord+"</b></font>");
    				
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
