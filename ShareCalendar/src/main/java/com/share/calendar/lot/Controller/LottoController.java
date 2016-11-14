package com.share.calendar.lot.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

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
import com.share.calendar.lot.Vo.lotLotteryVO;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/lot")
public class LottoController {
	
	private static final Logger logger = LoggerFactory.getLogger(LottoController.class);	

    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    private commonUtil comUtil;
	
    @Autowired 
    private Properties gProp;
	
	
	//////////////////////////////////  화면영역
	
	@RequestMapping(value = "/lotSelectM.do", method = RequestMethod.GET)
	public String lotSelectM(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController lotSelectM");
		
		return "/lot/lotSelectM";
	}
	
	@RequestMapping(value = "/duty.do", method = RequestMethod.GET)
	public String duty(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("===== SchController lotSelectM");
		
		return "/lot/duty";
	}
	

	//////////////////////////////////  API 영역
	
	@ResponseBody
	@RequestMapping(value = "/lotSelectData.do", method = RequestMethod.POST)
	public Map<String, Object> lotSelectData(Locale locale, 
														Model model, 
														HttpServletRequest request, 
														HttpSession session) throws Exception {
		logger.debug("===== LottoController lotSelectData()");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	Random random = new Random();
        	int[] lottery = {0,0,0,0,0,0};
        	
        	for(int i=0; i<6; i++) {
        		int chk_num = 0;
        		int temp_num = random.nextInt(45) + 1;
        		
        		for(int j=0; j<=i; j++) {
            		if(temp_num == lottery[j]) {
        				chk_num++;
        			}
        		}
        		
        		if(chk_num == 0) {
        			lottery[i] = temp_num;
        		} else {
        			i--;
        		}

        	}
        	
        	logger.debug("==================== : {}", lottery);

            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
            resultMap.put("resultData", lottery);
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
    @RequestMapping(value = "/lotLotteryMerge.do", method = RequestMethod.GET)
	public void lotLotteryMerge() {
		logger.debug("===== LottoController lotLotteryMerge()");

		String DIRECTORY = gProp.getProperty("amt.eai.receive.dir").trim();			// 수신 폴더
		String FILE_NAME = gProp.getProperty("lot.eai.receive.file.lottery").trim();	// 읽을 파일
		String filepath = "";
		
		try {
			// 대상 파일 찾기
			File dir = new File(DIRECTORY);

			for (File file : dir.listFiles()) {		
				filepath = DIRECTORY + "/" + file.getName();
				
				if(comUtil.exists(filepath) && file.getName().startsWith(FILE_NAME)){
					ArrayList<lotLotteryVO> mergedList = this.createVOList(filepath);	// 파일 내용 load

					if(mergedList != null && mergedList.size() > 0) {		// 파일내용 업데이트
						int mergedCnt = 0;
						for(lotLotteryVO vo : mergedList)
							mergedCnt += sqlSession.update("lotLotteryMapper.mergeLottery", vo);
	
						logger.debug("===== MergedList Size = " + mergedList.size() + "& Merged Excute Count = " + mergedCnt);
					} else {
						logger.error("***** Updated Data is not Exist *****");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("***** 오류발생", e);
		}

	}
    
    
    @ResponseBody
    @RequestMapping(value = "/lotSelectLotteryAll.do", method = RequestMethod.GET)
	public Map<String, Object> lotSelectLotteryAll() {
		logger.debug("===== LottoController lotSelectLotteryAll()");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	int[] lottery1 = {0,0,0,0,0,0};
        	int[] lottery2 = {0,0,0,0,0,0};
        	int[] lotteryCntAll_1 = {0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0};	// 보너스번호 미포함
        	int[] lotteryCntAll_2 = {0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0};	// 보너스번호 포함
        	
        	List<lotLotteryVO> mergedList = sqlSession.selectList("lotLotteryMapper.selectLotteryAll");
        	
        	logger.debug("=========== mergedList : " +mergedList.get(0) );
        	
        	for(lotLotteryVO vo : mergedList) {
        		lotteryCntAll_1[ vo.getnUM_1() ]++;
        		lotteryCntAll_1[ vo.getnUM_2() ]++;
        		lotteryCntAll_1[ vo.getnUM_3() ]++;
        		lotteryCntAll_1[ vo.getnUM_4() ]++;
        		lotteryCntAll_1[ vo.getnUM_5() ]++;
        		lotteryCntAll_1[ vo.getnUM_6() ]++;
        		
        		lotteryCntAll_2[ vo.getnUM_1() ]++;
        		lotteryCntAll_2[ vo.getnUM_2() ]++;
        		lotteryCntAll_2[ vo.getnUM_3() ]++;
        		lotteryCntAll_2[ vo.getnUM_4() ]++;
        		lotteryCntAll_2[ vo.getnUM_5() ]++;
        		lotteryCntAll_2[ vo.getnUM_6() ]++;
        		lotteryCntAll_2[ vo.getnUM_B() ]++;
        	}
        	
        	//정렬1
        	for(int j=0; j<lottery1.length; j++) {
	        	for(int i=0; i<lotteryCntAll_1.length; i++) {
	        		if(lotteryCntAll_1[i] > lotteryCntAll_1[ lottery1[j] ]) {
        				int temp_k = 0;
	        			for(int k=0; k<j; k++) {
	        				if(lottery1[k] == i) {
	        					temp_k++;
	        				}
	        			}
	        			
	        			if(temp_k == 0) {
	        				lottery1[j] = i;
	        			}
	        		}
	        	}
        	}
        	
        	//정렬2
        	for(int j=0; j<lottery2.length; j++) {
	        	for(int i=0; i<lotteryCntAll_2.length; i++) {
	        		if(lotteryCntAll_2[i] > lotteryCntAll_2[ lottery2[j] ]) {
        				int temp_k = 0;
	        			for(int k=0; k<j; k++) {
	        				if(lottery2[k] == i) {
	        					temp_k++;
	        				}
	        			}
	        			
	        			if(temp_k == 0) {
	        				lottery2[j] = i;
	        			}
	        		}
	        	}
        	}

        	logger.debug("===================lottery1[j] : " + lottery1);
        			
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
            resultMap.put("lotteryData1", lottery1);
            resultMap.put("lotteryData2", lottery2);
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
    @RequestMapping(value = "/lotSelectLotteryAll2.do", method = RequestMethod.POST)
	public Map<String, Object> lotSelectLotteryAll2() {
		logger.debug("===== LottoController lotSelectLotteryAll()");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	int[] lottery1 = {0,0,0,0,0,0};
        	int[] lottery2 = {0,0,0,0,0,0};
        	int[] lotteryCntAll_1 = {0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0};	// 보너스번호 미포함
        	int[] lotteryCntAll_2 = {0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0};	// 보너스번호 포함
        	
        	List mergedList = sqlSession.selectList("lotLotteryMapper.selectLotteryAll2");
        	
        	logger.debug("=========== mergedList : " +mergedList );
        	
        	for(int i=0; i<mergedList.size(); i++) {
        		Map<String, Object> tempMap = new HashMap<String, Object>();
        		tempMap = (Map<String, Object>) mergedList.get(i);
        		
        		int tempIdx = Integer.parseInt(tempMap.get("NUM_1").toString());
        		lotteryCntAll_1[ tempIdx ]++;
        		lotteryCntAll_2[ tempIdx ]++;
        		tempIdx = Integer.parseInt(tempMap.get("NUM_2").toString());
        		lotteryCntAll_1[ tempIdx ]++;
        		lotteryCntAll_2[ tempIdx ]++;
        		tempIdx = Integer.parseInt(tempMap.get("NUM_3").toString());
        		lotteryCntAll_1[ tempIdx ]++;
        		lotteryCntAll_2[ tempIdx ]++;
        		tempIdx = Integer.parseInt(tempMap.get("NUM_4").toString());
        		lotteryCntAll_1[ tempIdx ]++;
        		lotteryCntAll_2[ tempIdx ]++;
        		tempIdx = Integer.parseInt(tempMap.get("NUM_5").toString());
        		lotteryCntAll_1[ tempIdx ]++;
        		lotteryCntAll_2[ tempIdx ]++;
        		tempIdx = Integer.parseInt(tempMap.get("NUM_6").toString());
        		lotteryCntAll_1[ tempIdx ]++;
        		lotteryCntAll_2[ tempIdx ]++;
        		tempIdx = Integer.parseInt(tempMap.get("NUM_B").toString());
        		lotteryCntAll_2[ tempIdx ]++;
        	}
        	
        	//정렬1
        	for(int j=0; j<lottery1.length; j++) {
	        	for(int i=0; i<lotteryCntAll_1.length; i++) {
	        		if(lotteryCntAll_1[i] > lotteryCntAll_1[ lottery1[j] ]) {
        				int temp_k = 0;
	        			for(int k=0; k<j; k++) {
	        				if(lottery1[k] == i) {
	        					temp_k++;
	        				}
	        			}
	        			
	        			if(temp_k == 0) {
	        				lottery1[j] = i;
	        			}
	        		}
	        	}
        	}
        	
        	//정렬2
        	for(int j=0; j<lottery2.length; j++) {
	        	for(int i=0; i<lotteryCntAll_2.length; i++) {
	        		if(lotteryCntAll_2[i] > lotteryCntAll_2[ lottery2[j] ]) {
        				int temp_k = 0;
	        			for(int k=0; k<j; k++) {
	        				if(lottery2[k] == i) {
	        					temp_k++;
	        				}
	        			}
	        			
	        			if(temp_k == 0) {
	        				lottery2[j] = i;
	        			}
	        		}
	        	}
        	}
		
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
            resultMap.put("lotteryData1", lottery1);
            resultMap.put("lotteryData2", lottery2);
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
    
    
	/**
	 * SAM FILE을 열어 구분자 단위로 VO를 생성 후 ArrayList에 VO 객체를 담은 후 Return 한다.
	 * @param filepath
	 * @return 
	 */
	private ArrayList<lotLotteryVO> createVOList(String filepath) {
		ArrayList<lotLotteryVO> _addlist = new ArrayList<lotLotteryVO>();

		try {
			File file = new File(filepath);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "EUC-KR"));
			
			String line;
			int lineCnt = 0;
		    while((line = br.readLine()) != null) {
		    	lineCnt++;
		    	
		    	StringTokenizer tokenizer = new StringTokenizer(line, "|");
		    	if(tokenizer.countTokens() == 9){//정상데이타
			    	lotLotteryVO lotVo = new lotLotteryVO();

			    		lotVo.setlOT_NUM(tokenizer.nextToken().trim());
			    		lotVo.setlOT_DT(tokenizer.nextToken().trim());
			    		lotVo.setnUM_1(Integer.parseInt(tokenizer.nextToken().trim()));
			    		lotVo.setnUM_2(Integer.parseInt(tokenizer.nextToken().trim()));
			    		lotVo.setnUM_3(Integer.parseInt(tokenizer.nextToken().trim()));
			    		lotVo.setnUM_4(Integer.parseInt(tokenizer.nextToken().trim()));
			    		lotVo.setnUM_5(Integer.parseInt(tokenizer.nextToken().trim()));
			    		lotVo.setnUM_6(Integer.parseInt(tokenizer.nextToken().trim()));
			    		lotVo.setnUM_B(Integer.parseInt(tokenizer.nextToken().trim()));

			    	_addlist.add(lotVo);
		    	}
		    }
		    
		    br.close();
		    
		    logger.debug("===== File Line Count = " + _addlist.size() + " : " + lineCnt);
		} catch (Exception e) {
			 logger.error("===== ERROR EXEPTION  :",e);
		}

		return _addlist;
	}	
	
}
