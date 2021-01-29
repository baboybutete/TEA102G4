package com.shoppingcart.model;

import java.sql.Timestamp;
import java.util.List;

public class ShoppingCartService {
	private ShoppingCart_interface dao;
	public ShoppingCartService() {
		dao = new ShoppingCartJDBCDAO();
	} 
	public ShoppingCartVO insertShoppingCart(String custid,String mealid,Integer mealcount,Timestamp addDate) {
		ShoppingCartVO shoppingcartVO = new ShoppingCartVO();
		shoppingcartVO.setCustid(custid);
		shoppingcartVO.setMealid(mealid);
		shoppingcartVO.setMealcount(mealcount);
		shoppingcartVO.setAddDate(addDate);
		dao.insert(shoppingcartVO);
		return shoppingcartVO; 
	}
	public ShoppingCartVO updateShoppingCart(String shoppingcartid,String custid,String mealid,Integer mealcount,Timestamp addDate) {
		ShoppingCartVO shoppingcartVO = new ShoppingCartVO();
		shoppingcartVO.setShoppingcartid(shoppingcartid);
		shoppingcartVO.setCustid(custid);
		shoppingcartVO.setMealid(mealid);
		shoppingcartVO.setMealcount(mealcount);
		shoppingcartVO.setAddDate(addDate);
		dao.update(shoppingcartVO);

		return shoppingcartVO;
	}
	public void deleteShoppingCart(String shoppingcartid) {
		dao.delete(shoppingcartid);
	}

	public ShoppingCartVO getOneShoppingCart(String shoppingcartid) {
		return dao.findByPrimaryKey(shoppingcartid);
	}
	
	public ShoppingCartVO getOneShoppingCartid(String custid,String mealid) {
		return dao.findShoppingCartid(custid, mealid);
	}

	public List<ShoppingCartVO> getAll() {
		return dao.getAll();
	}
	
	public List<ShoppingCartVO> getAllForCustid(String custid) {
		return dao.getAllForCustid(custid);
	}

}
