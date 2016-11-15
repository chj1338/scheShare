package com.share.calendar.sch.Dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.share.calendar.sch.Dto.SchListDto;
import com.share.calendar.sch.Vo.SchVo;

/**
 * 로그인 처리화면
 * @author 
 *
 */
@Repository
public class SchListDaoImpl implements SchListDao {
	
	private static final Logger logger = LoggerFactory.getLogger(SchListDaoImpl.class);

    @Autowired
    private SqlSession sqlSession;
	
    /**
     * 
     */
    public List<SchVo> selectSchList(SchListDto schListDto) throws Exception {   	
    	return sqlSession.selectList("schListMapper.selectSchList", schListDto);
    }
    
    public List<SchVo> selectSchDetail(SchListDto schListDto) throws Exception {   	
    	return sqlSession.selectList("schListMapper.selectSchDetail", schListDto);
    }
    
    public String selectNewSchId() throws Exception {
    	return sqlSession.selectOne("schListMapper.selectNewSchId");
    }
    
	public void insertSchedule(SchListDto schListDto) throws Exception {
		sqlSession.insert("schListMapper.insertSchedule", schListDto);
	}
	
	public void updateSchedule(SchListDto schListDto) throws Exception {
		sqlSession.update("schListMapper.updateSchedule", schListDto);
	}
	
	public void deleteSchedule(SchListDto schListDto) throws Exception {
		sqlSession.delete("schListMapper.deleteSchedule", schListDto);
	}
	
}
