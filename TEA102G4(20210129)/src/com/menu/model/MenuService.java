package com.menu.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class MenuService {
	private MenuDAO_interface DAO;

	public MenuService() {
		this.DAO = new MenuDAOJDBC();
	}

	

	public MenuVO addMenu(String resid, String classname, String mealname, Integer mealprice, byte[] mealimg,
			String mealstatus, Timestamp adddate) {
		MenuVO menuVO = new MenuVO();
		menuVO.setResid(resid);
		menuVO.setClassname(classname);
		menuVO.setMealname(mealname);
		menuVO.setMealprice(mealprice);
		menuVO.setMealimg(mealimg);
		menuVO.setMealstatus(mealstatus);
		menuVO.setAdddate(adddate);
		DAO.insert(menuVO);
		return menuVO;
		
	};
	
	public MenuVO updateMenu(String mealid ,String resid, String classname, String mealname, Integer mealprice, byte[] mealimg,
			String mealstatus, Timestamp adddate) {
		MenuVO menuVO = new MenuVO();
		menuVO.setMealid(mealid);
		menuVO.setResid(resid);
		menuVO.setClassname(classname);
		menuVO.setMealname(mealname);
		menuVO.setMealprice(mealprice);
		menuVO.setMealimg(mealimg);
		menuVO.setMealstatus(mealstatus);
		menuVO.setAdddate(adddate);
		DAO.update(menuVO);
		return menuVO;
	}

	public void deleteMenu(String mealid) {
		DAO.delete(mealid);
	};

	public MenuVO getOneMenu(String mealid) {
		return DAO.findByPrimaryKey(mealid);
	};

	public List<MenuVO> getAll() {
		return DAO.getAll();
	};
	public List<MenuVO> getSpecificAll(String resid){

		List<MenuVO> menuList = DAO.getAll();
		List<MenuVO> newLis =menuList.stream().filter(m -> m.getResid().equals(resid))
		.collect(Collectors.toList());
		return newLis;
	}
	
	public List<MenuVO> getOneResMenu(String resid) {
		return DAO.getOneResMenu(resid);
	}
	
	public MenuVO addOneMenu(String resid) {
		return DAO.addOneMenu(resid);
	};
	
	
}
