package com.share.calendar.sys.Service;

import java.util.List;
import java.util.Map;

import com.share.calendar.sys.Dto.SysLoginDto;
import com.share.calendar.sys.Vo.SysLoginVo;


/**
 * 로그인 처리화면
 * @author
 *
 */
public interface TestService {
    

    /**
     * 
     */
    public List selectSqlTestData(Map<String, String> paramMap) throws Exception;
    
}
