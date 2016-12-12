package com.share.calendar.shop.Vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShopVo {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopVo.class);	
	
	private String prodId;
	private String prodNm;
	private String prodPrice;
	private String prodCn;
	private String prodMakerId;
	private String prodMakerNm;
	private String prodImg;
	
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(String prodPrice) {
		this.prodPrice = prodPrice;
	}
	public String getProdCn() {
		return prodCn;
	}
	public void setProdCn(String prodCn) {
		this.prodCn = prodCn;
	}
	public String getProdMakerId() {
		return prodMakerId;
	}
	public void setProdMakerId(String prodMakerId) {
		this.prodMakerId = prodMakerId;
	}
	public String getProdMakerNm() {
		return prodMakerNm;
	}
	public void setProdMakerNm(String prodMakerNm) {
		this.prodMakerNm = prodMakerNm;
	}
	public static Logger getLogger() {
		return logger;
	}
	public String getProdImg() {
		return prodImg;
	}
	public void setProdImg(String prodImg) {
		this.prodImg = prodImg;
	}
	
	
}
