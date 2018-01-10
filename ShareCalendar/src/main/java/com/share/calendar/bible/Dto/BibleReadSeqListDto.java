package com.share.calendar.bible.Dto;

import org.springframework.stereotype.Repository;



/**
 * 
 */
@Repository
public class BibleReadSeqListDto {
	
	private String readSeq;
	private String userId;
	
	public String getReadSeq() {
		return readSeq;
	}
	public void setReadSeq(String readSeq) {
		this.readSeq = readSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}
