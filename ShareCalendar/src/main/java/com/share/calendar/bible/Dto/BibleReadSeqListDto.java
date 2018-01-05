package com.share.calendar.bible.Dto;

import org.springframework.stereotype.Repository;



/**
 * 
 */
@Repository
public class BibleReadSeqListDto {
	
	private String bookSeq;
	private String userId;
	
	public String getBookSeq() {
		return bookSeq;
	}
	public void setBookSeq(String bookSeq) {
		this.bookSeq = bookSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}
