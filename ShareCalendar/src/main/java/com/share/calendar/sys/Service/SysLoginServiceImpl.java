package com.share.calendar.sys.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.share.calendar.sys.Controller.SysLoginController;
import com.share.calendar.sys.Dao.SysLoginDao;
import com.share.calendar.sys.Dto.SysLoginDto;
import com.share.calendar.sys.Vo.SysLoginVo;

/**
 * 로그인 처리화면
 * @author 
 *
 */
@Service
public class SysLoginServiceImpl implements SysLoginService {

	private static final Logger logger = LoggerFactory.getLogger(SysLoginController.class);
	
    @Autowired
	private SysLoginDao sysLoginDao;
    
    /**
     * 
     */
    public List<SysLoginVo> selectSysLoginChk(SysLoginDto sysLoginDto) throws Exception {
    	logger.debug("========= SysLoginService  selectSysLoginChk");    	
    	return sysLoginDao.selectSysLoginChk(sysLoginDto);
    };
    
    public void insertLoginHistory(SysLoginDto sysLoginDto) throws Exception {
    	sysLoginDao.insertLoginHistory(sysLoginDto);
    	sysLoginDao.updateLastLoginDt(sysLoginDto);
    }

    
}
