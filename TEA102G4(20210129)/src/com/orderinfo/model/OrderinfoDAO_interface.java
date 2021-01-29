package com.orderinfo.model;

import java.util.List;

import com.orderdet.model.OrderdetVO;

public interface OrderinfoDAO_interface {
	/*餐廳會員中心*/
	public void insert(OrderinfoVO orderinfoVO);
    public void update(OrderinfoVO orderinfoVO);
    public void delete(String orderid);
    public OrderinfoVO findByPrimaryKey(String orderid);
    public List<OrderinfoVO> getAllByOneResid(String resid);
    public List<OrderinfoVO> getAll();
    public void insertWithOrderdets (OrderinfoVO orderinfoVO , List<OrderdetVO> list);
    public List<OrderinfoVO> findByCustid(String custid);
}
