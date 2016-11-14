package com.share.calendar.sch.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.share.calendar.sch.Dao.SchListDao;
import com.share.calendar.sch.Dto.SchListDto;
import com.share.calendar.sch.Vo.SchVo;

/**
 * 
 * @author 
 *
 */
@Service
public class SchListServiceImpl implements SchListService {
	
	private static final Logger logger = LoggerFactory.getLogger(SchListDao.class);

    @Autowired
    private SchListDao schListDao;
	
    /**
     * 
     */
    public List<SchVo> selectSchList(SchListDto schListDto) throws Exception {
        return schListDao.selectSchList(schListDto);
    }
    
    public List<SchVo> selectSchDetail(SchListDto schListDto) throws Exception {
        return schListDao.selectSchDetail(schListDto);
    }
    
    
	public void insertSchedule(SchListDto schListDto) throws Exception {
		schListDao.insertSchedule(schListDto);
	}
	
	public void updateSchedule(SchListDto schListDto) throws Exception {
		schListDao.updateSchedule(schListDto);
	}
	
	public void deleteSchedule(SchListDto schListDto) throws Exception {
		schListDao.deleteSchedule(schListDto);
	}

}
