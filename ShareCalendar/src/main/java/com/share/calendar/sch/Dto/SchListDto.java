package com.share.calendar.sch.Dto;

import org.springframework.stereotype.Repository;



/**
 * 
 */
@Repository
public class SchListDto {
	
	private String schTitle;
	private String schContent;
	private String schDtFrom;
	private String schDtTo;
	private String schId;
	private String schSe;
	private String registId;
	
	public String getSchTitle() {
		return schTitle;
	}
	public void setSchTitle(String schTitle) {
		this.schTitle = schTitle;
	}
	public String getSchContent() {
		return schContent;
	}
	public void setSchContent(String schContent) {
		this.schContent = schContent;
	}
	public String getSchDtFrom() {
		return schDtFrom;
	}
	public void setSchDtFrom(String schDtFrom) {
		this.schDtFrom = schDtFrom;
	}
	public String getSchDtTo() {
		return schDtTo;
	}
	public void setSchDtTo(String schDtTo) {
		this.schDtTo = schDtTo;
	}
	public String getSchId() {
		return schId;
	}
	public void setSchId(String schId) {
		this.schId = schId;
	}
	public String getSchSe() {
		return schSe;
	}
	public void setSchSe(String schSe) {
		this.schSe = schSe;
	}
	public String getRegistId() {
		return registId;
	}
	public void setRegistId(String registId) {
		this.registId = registId;
	}
		
}
