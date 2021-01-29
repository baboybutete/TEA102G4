package com.favorites.model;

import java.sql.Timestamp;
import java.util.List;

public class FavoritesService {
	private FavoritesDAO_interface dao;
	public FavoritesService() {
		dao = new FavoritesJDBCDAO();
	}
	public FavoritesVO insertFavorites(String custid,String resid,String favoritestatus,Timestamp addDate) {
		FavoritesVO favoritesVO = new FavoritesVO();
		favoritesVO.setCustid(custid);
		favoritesVO.setResid(resid);
		favoritesVO.setFavoritestatus(favoritestatus);
		favoritesVO.setAddDate(addDate);
		dao.insert(favoritesVO);
		return favoritesVO;
	}
	public FavoritesVO updateFavorites(String favoritesid,String custid,String resid,String favoritestatus,Timestamp addDate) {
		FavoritesVO favoritesVO = new FavoritesVO();
		favoritesVO.setFavoritesid(favoritesid);
		favoritesVO.setCustid(custid);
		favoritesVO.setResid(resid);
		favoritesVO.setFavoritestatus(favoritestatus);
		favoritesVO.setAddDate(addDate);
		dao.update(favoritesVO);
		return favoritesVO;
	}
	public void deleteFavorites(String favoritesid) {
		dao.delete(favoritesid);
	}

	public FavoritesVO getOneFavorites(String favoritesid) {
		return dao.findByPrimaryKey(favoritesid);
	}

	public List<FavoritesVO> getAll() {
		return dao.getAll();
	}
	public List<FavoritesVO> getOneAccount(String custid) {
		return dao.findByFavoritesid(custid);
	}

}
