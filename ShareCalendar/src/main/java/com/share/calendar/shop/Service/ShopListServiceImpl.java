package com.share.calendar.shop.Service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.share.calendar.shop.Dto.ShopListDto;
import com.share.calendar.shop.Vo.ShopVo;

/**
 * 
 * @author 
 *
 */
@Service
public class ShopListServiceImpl implements ShopListService {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopListServiceImpl.class);
	
    @Autowired
    private SqlSession sqlSession;
	
    /**
     * 
     */
    public List<ShopVo> selectShopList(ShopListDto shopListDto) throws Exception {   	
    	return sqlSession.selectList("shopListMapper.selectShopList", shopListDto);
    }
    
    public List<ShopVo> selectShopDetail(ShopListDto shopListDto) throws Exception {   	
    	return sqlSession.selectList("shopListMapper.selectShopDetail", shopListDto);
    }
    
    public String selectNewShopId() throws Exception {
    	return sqlSession.selectOne("shopListMapper.selectNewShopId");
    }
    
	public void insertShopedule(ShopListDto shopListDto) throws Exception {
		sqlSession.insert("shopListMapper.insertShopedule", shopListDto);
	}
	
	public void updateShopedule(ShopListDto shopListDto) throws Exception {
		sqlSession.update("shopListMapper.updateShopedule", shopListDto);
	}
	
	public void deleteShopedule(ShopListDto shopListDto) throws Exception {
		sqlSession.delete("shopListMapper.deleteShopedule", shopListDto);
	}

}
