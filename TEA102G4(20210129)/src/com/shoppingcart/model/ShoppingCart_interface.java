package com.shoppingcart.model;

import java.util.List;

public interface ShoppingCart_interface {
	public void insert(ShoppingCartVO shoppingCartVO);
    public void update(ShoppingCartVO shoppingCartVO);
    public void delete(String shoppingcartid);
    public ShoppingCartVO findByPrimaryKey(String shoppingcartid);
    public ShoppingCartVO findShoppingCartid(String custid,String mealid);
    public List<ShoppingCartVO> getAll();
    public List<ShoppingCartVO> getAllForCustid(String custid);

}
