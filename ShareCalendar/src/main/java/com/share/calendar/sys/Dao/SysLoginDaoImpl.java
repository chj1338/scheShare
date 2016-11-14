package com.share.calendar.sys.Dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.share.calendar.sys.Controller.SysLoginController;
import com.share.calendar.sys.Dto.SysLoginDto;
import com.share.calendar.sys.Vo.SysLoginVo;

/**
 * 로그인 처리화면
 * @author 
 *
 */
@Repository
public class SysLoginDaoImpl implements SysLoginDao {
	
	private static final Logger logger = LoggerFactory.getLogger(SysLoginController.class);

    @Autowired
    private SqlSession sqlSession;
	
    /**
     * 
     */
    public List<SysLoginVo> selectSysLoginChk(SysLoginDto sysLoginDto) throws Exception {
    	logger.debug("========= SysLoginDto  selectSysLoginChk");    	
    	
    	return sqlSession.selectList("sysLoginMapper.selectSysLoginChk", sysLoginDto);
    };

    public void insertLoginHistory(SysLoginDto sysLoginDto) throws Exception {
    	sqlSession.insert("sysLoginMapper.insertLoginHistory", sysLoginDto);
    }
    
    public void updateLastLoginDt(SysLoginDto sysLoginDto) throws Exception {
    	sqlSession.insert("sysLoginMapper.updateLastLoginDt", sysLoginDto);
    }
    
}
