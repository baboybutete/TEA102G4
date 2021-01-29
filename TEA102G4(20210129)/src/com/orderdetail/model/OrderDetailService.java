package com.orderdetail.model;

import java.util.List;

public class OrderDetailService {
	
	private OrderDetailDAO_interface dao;
	
	public OrderDetailService() {
		dao = new OrderDetailJDBCDAO();
	}
	
	public List<OrderDetailVO> getOrderInfo(String resid){
		return dao.getOrderInfo(resid);
	}
	
	public List<OrderDetailVO> getAllDetail(String resid){
		return dao.getAllDetail(resid);
	}
	
	public List<OrderDetailVO> getOneDetail(String orderid){
		return dao.getOneDetail(orderid);
	}
	
	public OrderDetailVO update(String checkin ,String orderid) {
		OrderDetailVO orderDetailVO = new OrderDetailVO();
		orderDetailVO.setCheckin(checkin);
		orderDetailVO.setOrderid(orderid);
		dao.update(orderDetailVO);
		return orderDetailVO;
	}
}
