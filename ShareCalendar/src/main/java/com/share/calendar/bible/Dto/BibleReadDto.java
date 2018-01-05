package com.share.calendar.bible.Dto;

import org.springframework.stereotype.Repository;



/**
 * 
 */
@Repository
public class BibleReadDto {
	
	private String paramBook;
	private String paramPage;
	private String userId;
	
	public String getParamBook() {
		return paramBook;
	}
	public void setParamBook(String paramBook) {
		this.paramBook = paramBook;
	}
	public String getParamPage() {
		return paramPage;
	}
	public void setParamPage(String paramPage) {
		this.paramPage = paramPage;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
