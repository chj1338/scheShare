package com.share.calendar.etc.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
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
public class webParsing {
	private final static Logger logger = LoggerFactory.getLogger(webParsing.class);
	
    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    commonUtil comUtil;
	
    @Autowired 
    Properties gProp;
    
    
	//////////////////////////////////  화면영역
    
    @RequestMapping(value = "/webPars", method = RequestMethod.GET)
	public String webPars(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
    	return "/etc/webParsM";
    }
    

	@ResponseBody
    @RequestMapping(value = "/webParsData", method = RequestMethod.GET)
    public void webParsData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= webParsData Controller Start !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		URL url = new URL("http://www.daekwang.org/");
    		URLConnection con = (URLConnection)url.openConnection();
        	InputStreamReader reader = new InputStreamReader(con.getInputStream(), "euc-kr");
    		BufferedReader br = new BufferedReader(reader);
    		String pageContent = null;
    		
    		int maxNo = 0;
    		
    		while((pageContent = br.readLine()) != null) {  			
    			if(pageContent.indexOf("www.daekwang.org/board/dgboard.php") > -1) {
        			String tempStr[] = pageContent.split("=");
        			String tempNo = tempStr[4].replace("&call", "");
//        			System.out.println("=======" + tempNo);
        			
        			if(maxNo < Integer.parseInt(tempNo)) maxNo = Integer.parseInt(tempNo);
    			}
    		}
    		

    		
    		System.out.println("======= maxNo : " + maxNo);
    		
    		for(int j=(maxNo-10); j<=maxNo; j++) {
    			url = new URL("http://www.daekwang.org/board/dgboard.php?board=dk_02&mode=view&no=" + j + "&call=y");
        		con = (URLConnection)url.openConnection();
            	reader = new InputStreamReader(con.getInputStream(), "euc-kr");
        		br = new BufferedReader(reader);
        		String line = null;
        		
        		int checkNum = 0;
        		while((line = br.readLine()) != null) {
        			if(checkNum == 1) {
        				String lineStr = line.replaceAll("<br>","").replaceAll("</td>","").replaceAll("\t","").replaceAll("  ","").replaceAll("  ","");
        				String arrayStr1[] = lineStr.split("/");
        				String arrayStr2[] = arrayStr1[0].split(":");
        				//System.out.println(j + "==========" + lineStr);
        				
        				if(arrayStr1.length == 2) {
        					System.out.println(j + "==========" + arrayStr2[0] + " | " + arrayStr2[1] + " | " + arrayStr1[1]);
        				} else {
        					System.out.println(j + "==========" + arrayStr2[0] + " | " + arrayStr2[1]);
        				}        				
        				
        				checkNum = 0;
        			}
        			
        			if(line.indexOf("<td colspan=2 height=100 valign=top>") > -1) {
        				checkNum = 1;
        			} else {
        				checkNum = 0;
        			}
        		}
    		}
        	
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error : {}", e.getMessage());
            
            resultMap.put("resultCd", "9999");
            resultMap.put("resultMsg", e.getMessage());
        } finally {
        	;
        }
        
	}
}
    