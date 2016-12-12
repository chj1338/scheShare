package com.share.calendar.shop.Controller;

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

import com.share.calendar.shop.Dto.ShopListDto;
import com.share.calendar.shop.Service.ShopListService;
import com.share.calendar.shop.Vo.ShopVo;


/**
 * Handles requests for the application home page.
 */
@Controller
public class ShopController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);	
	
	@Autowired
	private ShopListService shopListService;
	
	@Autowired
	private ShopListDto shopListDto;

	//////////////////////////////////  화면영역
	
	@RequestMapping(value = "/shopListM", method = RequestMethod.GET)
	public String shopListM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== ShopController shopListM");
		
		return "shop/shopListM";
	}

	@RequestMapping(value = "/shopGridM", method = RequestMethod.GET)
	public String shopGridM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== ShopController shopGridM");
		
		return "shop/shopGridM";
	}
	
	@RequestMapping(value = "/shopInsertM", method = RequestMethod.GET)
	public String shopInsertM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== ShopController shopInsertM");
		
		String shopId = request.getParameter("shopId");
		logger.info("===== ShopController shopInsertM {}", shopId);
		model.addAttribute("shopId", shopId);
		
		return "shop/shopInsertM";
	}
	

	//////////////////////////////////  API 영역
	
	@ResponseBody
    @RequestMapping(value = "/shop/getShopListData", method = RequestMethod.POST)
    public Map<String, Object> getShopeduleList (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= getShopListData Controller Start !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String keyword 	= new String(request.getParameter("keyword").getBytes("ISO-8859-1"), "UTF-8");
    		
    		shopListDto.setKeyword(keyword);
    		   		
    		List<ShopVo> list = shopListService.selectShopList(shopListDto);

            resultMap.put("resultData", list);
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
	 * 샵 상세조회 
	 */
	@ResponseBody
    @RequestMapping(value = "/shop/getShopDetailData", method = RequestMethod.POST)
    public Map<String, Object> getShopDetailData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= getShopeduleList Controller Start !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String shopId = new String(request.getParameter("shopId"));
    		logger.debug("===== shopId : {}", shopId);
    		
//    		shopListDto.setShopId(shopId);
    		
    		List<ShopVo> list = shopListService.selectShopDetail(shopListDto);
    		
            resultMap.put("resultData", list);
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
	 * 샵 등록/수정 
	 */
	@ResponseBody
    @RequestMapping(value = "/shop/shopInsertData", method = RequestMethod.POST)
    public Map<String, Object> shopInsertData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= ShopController shopInsertData !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String shopId = request.getParameter("shopId");
    		String shopDt = request.getParameter("shopDt");
    		String shopSe = request.getParameter("shopSe");
    		String shopTitle = new String(request.getParameter("shopTitle").getBytes("ISO-8859-1"), "UTF-8");
    		String shopContent = new String(request.getParameter("shopContent").getBytes("ISO-8859-1"), "UTF-8");
    		
    		HttpSession session = request.getSession();
    		String registId = session.getAttribute("loginId").toString();
    		
    		logger.debug("===== shopId : {}", shopId);
    		logger.debug("===== shopTitle : {}", shopTitle);
    		logger.debug("===== shopContent : {}", shopContent); 
/*    		
    		shopListDto.setShopId(shopId);
    		shopListDto.setShopDtFrom(shopDt);
    		shopListDto.setShopSe(shopSe);
    		shopListDto.setShopTitle(shopTitle);
    		shopListDto.setShopContent(shopContent);
    		shopListDto.setRegistId(registId);
    		*/
    		if(shopId.equals("") || shopId == null) {
    			shopId = shopListService.selectNewShopId();
//    			shopListDto.setShopId(shopId);
    			
    			shopListService.insertShopedule(shopListDto);
    		} else {
    			shopListService.updateShopedule(shopListDto);
    		}
    		
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
	 * 샵 삭제 
	 */
	@ResponseBody
    @RequestMapping(value = "/shop/shopDeleteData", method = RequestMethod.POST)
    public Map<String, Object> shopDeleteData (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= ShopController shopDeleteData !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
    		String shopId = request.getParameter("shopId");
    		
    		logger.debug("===== shopId : {}", shopId);

//    		shopListDto.setShopId(shopId);

   			shopListService.deleteShopedule(shopListDto);

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
