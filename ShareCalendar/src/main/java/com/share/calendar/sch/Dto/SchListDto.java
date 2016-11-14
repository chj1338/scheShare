package com.share.calendar.sch.Dto;

import org.springframework.stereotype.Repository;



/**
 * 
 */
@Repository
public class SchListDto {
	
	private String sch_title;
	private String sch_content;
	private String templDtFrom;
	private String templDtTo;
	public String getSch_title() {
		return sch_title;
	}
	public void setSch_title(String sch_title) {
		this.sch_title = sch_title;
	}
	public String getSch_content() {
		return sch_content;
	}
	public void setSch_content(String sch_content) {
		this.sch_content = sch_content;
	}
	public String getTemplDtFrom() {
		return templDtFrom;
	}
	public void setTemplDtFrom(String templDtFrom) {
		this.templDtFrom = templDtFrom;
	}
	public String getTemplDtTo() {
		return templDtTo;
	}
	public void setTemplDtTo(String templDtTo) {
		this.templDtTo = templDtTo;
	}

}
