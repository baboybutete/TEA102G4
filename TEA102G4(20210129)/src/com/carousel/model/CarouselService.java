package com.carousel.model;

import java.sql.Timestamp;
import java.util.List;

import com.menu.model.MenuVO;

//import com.reshours.model.ResHoursVO;

public class CarouselService {
	private CarouselDAO_interface dao;

	public CarouselService() {
		dao = new CarouselJDBCDAO();
	}

	public CarouselVO addCarousel(String resid, byte[] carouselpic, Timestamp adddate) {

		CarouselVO carouselVO = new CarouselVO();

		carouselVO.setResid(resid);
		carouselVO.setCarouselpic(carouselpic);
		carouselVO.setAdddate(adddate);
		dao.insert(carouselVO);

		return carouselVO;
	}

	public CarouselVO updateCarousel(String carouselid, String resid, byte[] carouselpic, Timestamp adddate) {

		CarouselVO carouselVO = new CarouselVO();

		carouselVO.setCarouselid(carouselid);
		carouselVO.setResid(resid);
		carouselVO.setCarouselpic(carouselpic);
		carouselVO.setAdddate(adddate);
		dao.update(carouselVO);

		return carouselVO;
	}

	public void deleteCarousel(String carouselid) {
		dao.delete(carouselid);
	}

	public CarouselVO getOneCarousel(String carouselid) {
		return dao.findByPrimaryKey(carouselid);
	}

	public List<CarouselVO> getAll() {
		return dao.getAll();
	}
	
	public List<CarouselVO> getOneRes(String resid) {
		return dao.getOneRes(resid);
	}
	
	public CarouselVO addOneCarousel(String resid) {
		return dao.addOneCarousel(resid);
	};
}
