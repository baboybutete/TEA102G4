package com.menu.model;

import java.util.List;

public interface MenuDAO_interface {
	public void insert(MenuVO menuVO);

	public void update(MenuVO menuVO);

	public void delete(String mealid);

	public MenuVO findByPrimaryKey(String mealid);

	public List<MenuVO> getAll();
	
	public List<MenuVO> getOneResMenu(String resid);
	
	public MenuVO addOneMenu(String resid);
}
