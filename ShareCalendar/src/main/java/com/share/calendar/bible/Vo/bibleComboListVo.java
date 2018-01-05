package com.share.calendar.bible.Vo;

public class bibleComboListVo {    
	private int bookSeq;
	private String bookNmKor;
	private String bookNmKorAbr;
	private String bookNmEng;
	private String bookNmEngAbr;
	private int lastPage;
	
	public int getBookSeq() {
		return bookSeq;
	}
	public void setBookSeq(int bookSeq) {
		this.bookSeq = bookSeq;
	}
	public String getBookNmKor() {
		return bookNmKor;
	}
	public void setBookNmKor(String bookNmKor) {
		this.bookNmKor = bookNmKor;
	}
	public String getBookNmKorAbr() {
		return bookNmKorAbr;
	}
	public void setBookNmKorAbr(String bookNmKorAbr) {
		this.bookNmKorAbr = bookNmKorAbr;
	}
	public String getBookNmEng() {
		return bookNmEng;
	}
	public void setBookNmEng(String bookNmEng) {
		this.bookNmEng = bookNmEng;
	}
	public String getBookNmEngAbr() {
		return bookNmEngAbr;
	}
	public void setBookNmEngAbr(String bookNmEngAbr) {
		this.bookNmEngAbr = bookNmEngAbr;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
}