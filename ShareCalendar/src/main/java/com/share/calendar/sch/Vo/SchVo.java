package com.share.calendar.sch.Vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SchVo {
	
	private static final Logger logger = LoggerFactory.getLogger(SchListVo.class);	
	
	private String scheNo;
	private String scheDt;
	private String scheTitle;
	private String scheContent;
	private String scheSe;
	private String registId;
	private String registDt;
	
	public String getScheNo() {
		return scheNo;
	}
	public void setScheNo(String scheNo) {
		this.scheNo = scheNo;
	}
	public String getScheDt() {
		return scheDt;
	}
	public void setScheDt(String scheDt) {
		this.scheDt = scheDt;
	}
	public String getScheTitle() {
		return scheTitle;
	}
	public void setScheTitle(String scheTitle) {
		this.scheTitle = scheTitle;
	}
	public String getScheContent() {
		return scheContent;
	}
	public void setScheContent(String scheContent) {
		this.scheContent = scheContent;
	}
	public String getScheSe() {
		return scheSe;
	}
	public void setScheSe(String scheSe) {
		this.scheSe = scheSe;
	}
	public String getRegistId() {
		return registId;
	}
	public void setRegistId(String registId) {
		this.registId = registId;
	}
	public String getRegistDt() {
		return registDt;
	}
	public void setRegistDt(String registDt) {
		this.registDt = registDt;
	}
	public static Logger getLogger() {
		return logger;
	}
	
	
	
}
