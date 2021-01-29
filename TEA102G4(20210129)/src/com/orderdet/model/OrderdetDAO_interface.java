package com.orderdet.model;

import java.util.List;

public interface OrderdetDAO_interface {
	public void insert(OrderdetVO orderdetVO);
	public void update(OrderdetVO orderdetVO);
	public void delete(String orderid, String mealid);
	public OrderdetVO findByPrimaryKey(String orderid, String mealid);
	public List<OrderdetVO> findOne(String orderid);
	public List<OrderdetVO> getAll();
	
	public void insert2 (OrderdetVO orderdetVO , java.sql.Connection con);
}
