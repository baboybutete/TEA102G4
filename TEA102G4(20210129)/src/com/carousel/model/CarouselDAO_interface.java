package com.carousel.model;

import java.util.List;

import com.menu.model.MenuVO;

public interface CarouselDAO_interface {
	public void insert(CarouselVO carouselvo);

	public void update(CarouselVO carouselvo);

	public void delete(String carouselid);

	public CarouselVO findByPrimaryKey(String carouselid);

	public List<CarouselVO> getAll();
	
	public List<CarouselVO> getOneRes(String resid);
	
	public CarouselVO addOneCarousel(String resid);
}
