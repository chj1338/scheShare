package com.share.calendar.sys.Controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.calendar.shop.Vo.ShopVo;
import com.share.calendar.sys.Service.TestService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	TestService testService;
	
	//////////////////////////////////  화면영역	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/main/sqlTestM.do", method = RequestMethod.GET)
	public String sqlTest(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("============= testController sqlTest");
		return "/main/sqlTestM";
	}

	
	//////////////////////////////////  API 영역
	
	@ResponseBody
    @RequestMapping(value = "/main/sqlSelectData.do", method = RequestMethod.POST)
    public Map<String, Object> sqlTestData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= testController sqlTestData");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String sqlContent 	= new String(request.getParameter("sqlContent").getBytes("ISO-8859-1"), "UTF-8");

    		Map<String, String> paramMap = new HashMap<String, String>();
    		paramMap.put("sqlContent", sqlContent);
    		
    		List list = testService.selectSqlTestData(paramMap);
    		
    		String resultContent = "";
    		
    		for(int i=0; i<list.size(); i++) {
    			HashMap temp = (HashMap) list.get(i);

   				if(i>0) resultContent += "\n";
   				resultContent += "----- row_" + i + " : " + temp;
    		}

            resultMap.put("resultData", resultContent);
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
	
}
