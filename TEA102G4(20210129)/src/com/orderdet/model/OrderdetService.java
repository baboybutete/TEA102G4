package com.orderdet.model;

import java.util.List;

public class OrderdetService {
	
	private OrderdetDAO_interface dao;
	
	public OrderdetService() {
		dao = new OrderdetJDBCDAO();
		
	}
	
	public OrderdetVO addOrderdet(String orderid, String mealid, Integer mealcount) {
		OrderdetVO orderdetVO = new OrderdetVO();
		orderdetVO.setOrderid(orderid);
		orderdetVO.setMealid(mealid);
		orderdetVO.setMealcount(mealcount);
		
		dao.insert(orderdetVO);
		
		return orderdetVO;
	}
	
	public OrderdetVO updateOrderdet(String orderid, String mealid, Integer mealcount) {
		OrderdetVO orderdetVO = new OrderdetVO();
		orderdetVO.setOrderid(orderid);
		orderdetVO.setMealid(mealid);
		orderdetVO.setMealcount(mealcount);
		
		dao.update(orderdetVO);
		
		return orderdetVO;
	}
	
	public void deleteOrderdet(String orderid, String mealid) {
		dao.delete(orderid,mealid);
	}
	
	public OrderdetVO getOneOrderdet(String orderid, String mealid) {
		return dao.findByPrimaryKey(orderid,mealid);
	}
	
	public List<OrderdetVO> getAll(){
		return dao.getAll();
	}
	
	public List<OrderdetVO> finOne(String orderid){
		return dao.findOne(orderid);
	}
	

}
