package com.share.calendar.sys.Service;

import java.util.List;

import com.share.calendar.sys.Dto.SysLoginDto;
import com.share.calendar.sys.Vo.SysLoginVo;

/**
 * 로그인 처리화면
 * @author 
 *
 */
public interface SysLoginService {


    /**
     * 
     */
	public List<SysLoginVo> selectSysLoginChk(SysLoginDto sysLoginDto) throws Exception;
    
	public void insertLoginHistory(SysLoginDto sysLoginDto) throws Exception;

    
}
