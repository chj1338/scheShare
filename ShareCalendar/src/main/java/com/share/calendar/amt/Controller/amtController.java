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

		String DIRECTORY = gProp.getProperty("amt.eai.receive.dir").trim();			// ���� ����
		String FILE_NAME = gProp.getProperty("amt.eai.receive.file.initial").trim();	// ���� ����
		String RESULT_DIR = gProp.getProperty("amt.eai.result.dir").trim();			// �Ϸ� ����
		String filepath = "";
		
		try {
			// ��� ���� ã��
			File dir = new File(DIRECTORY);

			for (File file : dir.listFiles()) {			
				filepath = DIRECTORY + "/" + file.getName();
				logger.debug("===== Target File : " + filepath);
				
				if(comUtil.exists(filepath)){
					ArrayList<amtFactorySaleAmtVO> mergedList = this.createVOList(filepath);	// ���� ���� load
					if(mergedList != null && mergedList.size() > 0) {		// ���ϳ��� ������Ʈ
						int mergedCnt = 0;
						for(amtFactorySaleAmtVO vo : mergedList)
							mergedCnt += sqlSession.update("amtLoadSaleAmtMapper.mergeFactorySaleAmt", vo);
	
						logger.debug("===== MergedList Size = " + mergedList.size() + "& Merged Excute Count = " + mergedCnt);
					} else {
						logger.error("***** Updated Data is not Exist *****");
					}
				}
			}
			
			comUtil.moveFileToDirectory(DIRECTORY, FILE_NAME, RESULT_DIR);	//�Ϸ��� ���� �̵�
//				statusUpdate("20"); // �۾��� ���� ������Ʈ
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("***** �����߻�", e);
		}

	}
	    
    
    
	/**
	 * ���� ������Ʈ Query ����
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
	 * SAM FILE�� ���� ������ ������ VO�� ���� �� ArrayList�� VO ��ü�� ���� �� Return �Ѵ�.
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
		    	if(tokenizer.countTokens()==5){//������Ÿ
			    	//logger.debug("Batch File Line  = " + line);
			    	tokenizer.nextToken().trim();// ù�ʵ� skip
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
