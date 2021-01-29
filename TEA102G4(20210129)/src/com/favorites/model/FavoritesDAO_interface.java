package com.favorites.model;

import java.util.List;

public interface FavoritesDAO_interface {
	public void insert(FavoritesVO favoritesVO);
    public void update(FavoritesVO favoritesVO);
    public void delete(String favoritesid);
    public FavoritesVO findByPrimaryKey(String favoritesid);
    public List<FavoritesVO> getAll();
    public List<FavoritesVO> findByFavoritesid(String custid);

}
