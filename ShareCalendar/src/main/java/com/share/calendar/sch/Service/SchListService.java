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
    
//	public void insertLoginHistory(SysLoginDto sysLoginDto) throws Exception;

    
}
