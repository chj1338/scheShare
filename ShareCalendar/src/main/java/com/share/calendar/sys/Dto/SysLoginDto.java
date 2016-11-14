package com.share.calendar.sys.Dto;

import org.springframework.stereotype.Repository;


/**
 * 
 */
@Repository
public class SysLoginDto {
    
    /* 사용자 ID */
    private String loginId;
    
    /* 사용자 PW */
    private String loginPW;
    
    /* 사용자 IP */
    private String clientIp;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPW() {
		return loginPW;
	}

	public void setLoginPW(String loginPW) {
		this.loginPW = loginPW;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
    
}
