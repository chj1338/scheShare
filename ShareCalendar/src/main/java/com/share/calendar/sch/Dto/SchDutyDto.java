package com.share.calendar.sch.Dto;

import java.util.Calendar;

import org.springframework.stereotype.Repository;



/**
 * 
 */
@Repository
public class SchDutyDto {
	
	private String thisYear;
	private String thisMonth;
	
	public String getThisYear() {
		return thisYear;
	}
	public void setThisYear(String thisYear) {
		this.thisYear = thisYear;
	}
	public String getThisMonth() {
		return thisMonth;
	}
	public void setThisMonth(String thisMonth) {
		this.thisMonth = thisMonth;
	};
	
}
