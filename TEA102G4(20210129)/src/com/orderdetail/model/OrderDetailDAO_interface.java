package com.orderdetail.model;

import java.util.List;

import com.menu.model.MenuVO;
import com.orderdet.model.OrderdetVO;

public interface OrderDetailDAO_interface {
	
//    public List<OrderDetailVO> getAll();
    
    public List<OrderDetailVO> getOrderInfo(String resid);
    
    public List<OrderDetailVO> getAllDetail(String resid);
    
    public List<OrderDetailVO> getOneDetail(String orderid);
    
    public void update(OrderDetailVO orderDetailVO);
}
