package com.share.calendar.amt.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.calendar.amt.Vo.amtFactorySaleAmtVO;
import com.share.calendar.common.Util.commonUtil;

@Controller
@RequestMapping(value = "/amt")
public class amtController {

	private final static Logger logger = Logger.getLogger(amtController.class);
	
    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    commonUtil comUtil;
	
    @Autowired 
    Properties gProp;

    
    @RequestMapping(value = "/amtLoadSaleAmt", method = RequestMethod.GET)
	public String amtLoadSaleAmt(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
    	return "/amt/amtLoadSaleAmt";
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/amtLoadSaleAmtData", method = RequestMethod.GET)
	public void amtLoadSaleAmtData() {
		logger.debug("===== amtController amtLoadSaleAmt()");

		String DIRECTORY = gProp.getProperty("amt.eai.receive.dir").trim();			// 수신 폴더
		String FILE_NAME = gProp.getProperty("amt.eai.receive.file.initial").trim();	// 읽을 파일
		String RESULT_DIR = gProp.getProperty("amt.eai.result.dir").trim();			// 완료 폴더
		String filepath = "";
		
		try {
			// 대상 파일 찾기
			File dir = new File(DIRECTORY);

			for (File file : dir.listFiles()) {			
				filepath = DIRECTORY + "/" + file.getName();
				logger.debug("===== Target File : " + filepath);
				
				if(comUtil.exists(filepath)){
					ArrayList<amtFactorySaleAmtVO> mergedList = this.createVOList(filepath);	// 파일 내용 load
					if(mergedList != null && mergedList.size() > 0) {		// 파일내용 업데이트
						int mergedCnt = 0;
						for(amtFactorySaleAmtVO vo : mergedList)
							mergedCnt += sqlSession.update("amtLoadSaleAmtMapper.mergeFactorySaleAmt", vo);
	
						logger.debug("===== MergedList Size = " + mergedList.size() + "& Merged Excute Count = " + mergedCnt);
					} else {
						logger.error("***** Updated Data is not Exist *****");
					}
				}
			}
			
			comUtil.moveFileToDirectory(DIRECTORY, FILE_NAME, RESULT_DIR);	//완료후 파일 이동
//				statusUpdate("20"); // 작업중 상태 업데이트
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("***** 오류발생", e);
		}

	}
	    
    
    
	/**
	 * 상태 업데이트 Query 수행
	 * @param mergedList
	 */
	private  void statusUpdate(String status) {

		try { 
			    HashMap<String, String> map = new HashMap<String, String>();
			    map.put("STATUS", status);
			    sqlSession.update("amtLoadSaleAmtMapper.updateJobStatus", map);
			 	logger.debug("===== Status CODE Update : " + status);
		} catch (Exception e) {
			logger.error("***** EXCEPTION OCCUR During EXCUTING statusUpdate *******", e);
		}
	}
	
	
	/**
	 * SAM FILE을 열어 구분자 단위로 VO를 생성 후 ArrayList에 VO 객체를 담은 후 Return 한다.
	 * @param filepath
	 * @return 
	 */
	private ArrayList<amtFactorySaleAmtVO> createVOList(String filepath) {
		ArrayList<amtFactorySaleAmtVO> _addlist = new ArrayList<amtFactorySaleAmtVO>();

		try {
			File file = new File(filepath);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "EUC-KR"));
			String line;
			int lineCnt = 0;
		    while((line = br.readLine()) != null) {
		    	lineCnt++;
		    	
		    	StringTokenizer tokenizer = new StringTokenizer(line, "|");
		    	if(tokenizer.countTokens()==5){//정상데이타
			    	//logger.debug("Batch File Line  = " + line);
			    	tokenizer.nextToken().trim();// 첫필드 skip
			    	amtFactorySaleAmtVO amtVo = new amtFactorySaleAmtVO();
			    		amtVo.setMODEL_CD(tokenizer.nextToken().trim());
			    		amtVo.setEFF_STA_DT(tokenizer.nextToken().trim());
			    		amtVo.setEFF_END_DT(tokenizer.nextToken().trim());
			    		amtVo.setSALE_AMT(Integer.toString(Integer.parseInt(tokenizer.nextToken().trim())));
			    	_addlist.add(amtVo);
		    	}
		    }
		    br.close();
		    logger.debug("===== File Line Count = " + lineCnt);
		} catch (Exception e) {
			 logger.error("===== ERROR EXEPTION  :",e);
		}

		return _addlist;
	}	

}
