package com.share.calendar.shop.Service;

import java.util.List;

import com.share.calendar.shop.Dto.ShopListDto;
import com.share.calendar.shop.Vo.ShopVo;

/**
 * 로그인 처리화면
 * @author 
 *
 */
public interface ShopListService {


    /**
     * 
     */
	public List<ShopVo> selectShopList(ShopListDto shopListDto) throws Exception;
	
	public List<ShopVo> selectShopDetail(ShopListDto shopListDto) throws Exception;
    
	public String selectNewShopId() throws Exception;
	
	public void insertShopedule(ShopListDto shopListDto) throws Exception;
	
	public void updateShopedule(ShopListDto shopListDto) throws Exception;
	
	public void deleteShopedule(ShopListDto shopListDto) throws Exception;
    
}
