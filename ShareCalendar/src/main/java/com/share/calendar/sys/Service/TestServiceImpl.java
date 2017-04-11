package com.share.calendar.sys.Service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.share.calendar.sys.Dao.SysLoginDaoImpl;
import com.share.calendar.sys.Dto.SysLoginDto;
import com.share.calendar.sys.Vo.SysLoginVo;

/**
 * 로그인 처리화면
 * @author 
 *
 */
@Repository
public class TestServiceImpl implements TestService {
	
	private static final Logger logger = LoggerFactory.getLogger(SysLoginDaoImpl.class);

    @Autowired
    private SqlSession sqlSession;
	
    /**
     * 
     */
    public List selectSqlTestData(Map<String, String> paramMap) throws Exception {
    	logger.debug("========= testServiceImpl  selectSqlData");    	
    	
    	return sqlSession.selectList("testMapper.selectSqlTestData", paramMap);
    };
    
    public void sinmungoSave(Map<String, String> paramMap) throws Exception {
    	sqlSession.insert("testMapper.sinmungoSave", paramMap);
    	sqlSession.insert("testMapper.sinmungoSaveCom", paramMap);
    }
    
}
