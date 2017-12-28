package com.share.calendar.etc.Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

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

import com.share.calendar.common.Util.commonUtil;

@Controller
@RequestMapping(value = "/etc")
public class manCountController {
	private final static Logger logger = LoggerFactory.getLogger(manCountController.class);
	
    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    commonUtil comUtil;
	
    @Autowired 
    Properties gProp;
    
    
	//////////////////////////////////  화면영역
    
    @RequestMapping(value = "/manCount", method = RequestMethod.GET)
	public String manCount(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
    	return "/etc/manCountM";
    }
    
    
	//////////////////////////////////  API 영역
	@ResponseBody
    @RequestMapping(value = "/initData", method = RequestMethod.POST)
    public Map<String, Object> initData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= initData Controller Start !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {    		
    		List<Object> companyList	= sqlSession.selectList("manCountMapper.selectCompanyList");
    		List<Object> gradeList		= sqlSession.selectList("manCountMapper.selectGradeList");
    		List<Object> midList		= sqlSession.selectList("manCountMapper.selectMidList");
    		List<Object> coclcdList		= sqlSession.selectList("manCountMapper.selectCoclcdList");

            resultMap.put("companyData",	companyList);
            resultMap.put("gradeData",		gradeList);
            resultMap.put("midData",			midList);
            resultMap.put("coclcdData",		coclcdList);
            
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
	
	
	@ResponseBody
    @RequestMapping(value = "/manSearchList", method = RequestMethod.POST)
    public Map<String, Object> manSearchList (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= manSearchList Controller Start !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String company		= new String(request.getParameter("company").getBytes("ISO-8859-1"), "UTF-8");
    		String name 			= new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
    		String grade 		= new String(request.getParameter("grade").getBytes("ISO-8859-1"), "UTF-8");
    		String mid 			= new String(request.getParameter("mid").getBytes("ISO-8859-1"), "UTF-8");
    		String experience	= new String(request.getParameter("experience").getBytes("ISO-8859-1"), "UTF-8");
    		String coclcd			= new String(request.getParameter("coclcd").getBytes("ISO-8859-1"), "UTF-8");
    		String capa			= new String(request.getParameter("capa").getBytes("ISO-8859-1"), "UTF-8");
    		
    		String page			= new String(request.getParameter("page").getBytes("ISO-8859-1"), "UTF-8");
    		String rowNum		= new String(request.getParameter("rowNum").getBytes("ISO-8859-1"), "UTF-8");

			Map<String, String> map = new HashMap<String, String>();
			
			map.put("company", company);
			map.put("num", name);
			map.put("name", name);
			map.put("grade", grade);
			map.put("experience", experience);
			map.put("mid", mid);
			map.put("coclcd", coclcd);
			map.put("capa", capa);
			
			map.put("page", page);
			map.put("rowNum", rowNum);
    		
    		List list = sqlSession.selectList("manCountMapper.selectManList", map);
    		Map<String, Object> tempMap = new HashMap<String, Object>();
    		tempMap = (Map<String, Object>) list.get(0);
    		String tot_cnt = (String) tempMap.get("TOT_CNT");

    		logger.debug("==== page : {}", page);
    		logger.debug("==== rowNum : {}", rowNum);
    		
            resultMap.put("resultData", list);
            resultMap.put("resultRecords", list.size());
            resultMap.put("resultPage", page);
            resultMap.put("resultTotal", tot_cnt);
            
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

    // 대원 자료 업로드 용
    @ResponseBody
    @RequestMapping(value = "/manInsertData", method = RequestMethod.GET)
	public Map<String, Object> manInsertData(Locale locale, Model model, HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	logger.debug("================ 파일읽기 시작");
		       
	    String fileName = "D:/test/1.txt";
	    String company = "대원";
		int COL = 11;
		

		FileInputStream fis = null;
		BufferedReader br = null;

	    // 파일 읽기
	    try {
	    	fis = new FileInputStream(fileName); 
	        br = new BufferedReader(new InputStreamReader(fis, "EUC-KR"));

	        String line = new String();
	        String lastLine = "";
	        
	        int cnt = 0;
	        
	        br.readLine(); // 첫줄은 Header
	        while( (line=br.readLine()) != null) {
	        	if(cnt == 0) {
	        		if(line.length() > 3) {
		        		lastLine += line;
			        	cnt = 10;		        			
	        		} else {
		        		lastLine += line;
			        	cnt++;	
	        		}
	        	} else if(cnt < COL) {
        			lastLine += ";" + line;
		        	cnt++;	        		
	        	} else {
	        		if(line.equals("비고") || line.equals("퇴사") || line.equals("철수")) {
		        		lastLine += ";" + line;
			        	cnt++;
	        		} else {
	        			logger.debug("lastLine : " + lastLine);
		        		
	        			String rowData[] = lastLine.split(";");
	        			
	        			Map<String, String> map = new HashMap<String, String>();
	        			
	        			map.put("company", company);
	        			map.put("num", rowData[0]);
	        			map.put("name", rowData[1]);
	        			map.put("job_se", rowData[2]);
	        			map.put("grade", rowData[3]);
	        			map.put("career_year", rowData[4]);
	        			map.put("experience", rowData[5]);
//	        			map.put("job_se_2", rowData[6]);
	        			map.put("capability", rowData[7]);
	        			map.put("mid", rowData[8]);
	        			map.put("location", rowData[9]);
	        			map.put("employ_se", rowData[10]);

	        			if( rowData[0].equals("이름") ) {
	        				sqlSession.delete("manCountMapper.manDelete", map);
	        			} else {
	        				sqlSession.insert("manCountMapper.manInsert", map);
	        			}

			        	// 처리후 초기화
			        	lastLine = line;
		        		cnt = 1;
	        		}
	        	}       	
	        }

	        logger.debug("================ 정상종료");
	        
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
	    } catch (IOException e) { 
	    	logger.debug("================ 파일읽기 오류 \n " + e.getMessage());
	    	e.printStackTrace();
	    	
            resultMap.put("resultCd", "9999");
            resultMap.put("resultMsg", e.getMessage());
	    } finally {
	        br.close();
	    }
	    
	    return resultMap;
	}

}