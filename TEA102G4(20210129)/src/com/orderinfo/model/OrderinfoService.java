package com.orderinfo.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.orderdet.model.OrderdetJDBCDAO;
import com.orderdet.model.OrderdetVO;

public class OrderinfoService {
	
	private OrderinfoDAO_interface dao;
	
	public OrderinfoService() {
		dao = new OrderinfoJDBCDAO();
	}

	public OrderinfoVO addOrderinfo(String resid, String custid, String subscriber, String subphone, Timestamp ordertime, Timestamp subtime, Integer subnumber, String paystatus, String checkin, String seat, String orderstatus) {
		OrderinfoVO orderinfoVO = new OrderinfoVO();
		orderinfoVO.setResid(resid);
		orderinfoVO.setCustid(custid);
		orderinfoVO.setSubscriber(subscriber);
		orderinfoVO.setSubphone(subphone);
		orderinfoVO.setOrdertime(ordertime);
		orderinfoVO.setSubtime(subtime);
		orderinfoVO.setSubnumber(subnumber);
		orderinfoVO.setPaystatus(paystatus);
		orderinfoVO.setCheckin(checkin);
		orderinfoVO.setSeat(seat); 
		orderinfoVO.setOrderstatus(orderstatus); 
		dao.insert(orderinfoVO);
		return orderinfoVO;
	}
	
	//後台使用
	public OrderinfoVO addOrderinfo(String resid, String custid, String subscriber, String subphone, Timestamp ordertime, Timestamp subtime, Integer subnumber, String paystatus, String checkin, String seat) {
		OrderinfoVO orderinfoVO = new OrderinfoVO();
		orderinfoVO.setResid(resid);
		orderinfoVO.setCustid(custid);
		orderinfoVO.setSubscriber(subscriber);
		orderinfoVO.setSubphone(subphone);
		orderinfoVO.setOrdertime(ordertime);
		orderinfoVO.setSubtime(subtime);
		orderinfoVO.setSubnumber(subnumber);
		orderinfoVO.setPaystatus(paystatus);
		orderinfoVO.setCheckin(checkin);
		orderinfoVO.setSeat(seat); 
		dao.insert(orderinfoVO);
		return orderinfoVO;
	}
	
	public OrderinfoVO updateOrderinfo(String orderid, String resid, String custid, String subscriber, String subphone, Timestamp ordertime, Timestamp subtime, Integer subnumber, String paystatus, String checkin, String seat, String orderstatus) {
		OrderinfoVO orderinfoVO = new OrderinfoVO();
		orderinfoVO.setOrderid(orderid);
		orderinfoVO.setResid(resid);
		orderinfoVO.setCustid(custid);
		orderinfoVO.setSubscriber(subscriber);
		orderinfoVO.setSubphone(subphone);
		orderinfoVO.setOrdertime(ordertime);
		orderinfoVO.setSubtime(subtime);
		orderinfoVO.setSubnumber(subnumber);
		orderinfoVO.setPaystatus(paystatus);
		orderinfoVO.setCheckin(checkin);
		orderinfoVO.setSeat(seat); 
		orderinfoVO.setOrderstatus(orderstatus); 
		dao.update(orderinfoVO);
		return orderinfoVO;
	}
	
	//後台使用
	public OrderinfoVO updateOrderinfo(String orderid, String resid, String custid, String subscriber, String subphone, Timestamp ordertime, Timestamp subtime, Integer subnumber, String paystatus, String checkin, String seat) {
		OrderinfoVO orderinfoVO = new OrderinfoVO();
		orderinfoVO.setOrderid(orderid);
		orderinfoVO.setResid(resid);
		orderinfoVO.setCustid(custid);
		orderinfoVO.setSubscriber(subscriber);
		orderinfoVO.setSubphone(subphone);
		orderinfoVO.setOrdertime(ordertime);
		orderinfoVO.setSubtime(subtime);
		orderinfoVO.setSubnumber(subnumber);
		orderinfoVO.setPaystatus(paystatus);
		orderinfoVO.setCheckin(checkin);
		orderinfoVO.setSeat(seat); 
		dao.update(orderinfoVO);
		return orderinfoVO;
	}
	
	public void deteleOrderid(String orderid) {
		dao.delete(orderid);
	}
	
	public OrderinfoVO getOneOrderinfo(String orderid) {
		return dao.findByPrimaryKey(orderid);
	}
	
	public List<OrderinfoVO> getAll(){
		return dao.getAll();
	}
	public List<OrderinfoVO> getAllByOneResid(String resid){
		return dao.getAllByOneResid(resid);
	}
	public void insertWithOrderdets(OrderinfoVO orderinfoVO,List<OrderdetVO> list){
		
		dao.insertWithOrderdets(orderinfoVO, list);

	}
	
	public List<OrderinfoVO> getAllByCustid(String custid){
		return dao.findByCustid(custid);
	}
	
}
