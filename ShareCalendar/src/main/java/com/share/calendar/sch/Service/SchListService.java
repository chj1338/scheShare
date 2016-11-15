package com.share.calendar.sch.Service;

import java.util.List;

import com.share.calendar.sch.Dto.SchListDto;
import com.share.calendar.sch.Vo.SchVo;

/**
 * 로그인 처리화면
 * @author 
 *
 */
public interface SchListService {


    /**
     * 
     */
	public List<SchVo> selectSchList(SchListDto schListDto) throws Exception;
	
	public List<SchVo> selectSchDetail(SchListDto schListDto) throws Exception;
    
	public String selectNewSchId() throws Exception;
	
	public void insertSchedule(SchListDto schListDto) throws Exception;
	
	public void updateSchedule(SchListDto schListDto) throws Exception;
	
	public void deleteSchedule(SchListDto schListDto) throws Exception;
    
}
