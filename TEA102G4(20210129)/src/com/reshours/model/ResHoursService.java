package com.reshours.model;

import java.sql.Timestamp;
import java.util.List;

public class ResHoursService {
	private ResHoursDAO_interface dao;

	public ResHoursService() {
		dao = new ResHoursJDBCDAO();
	}

	public ResHoursVO addResHours(String resid, Timestamp opening, Timestamp closing) {

		ResHoursVO resHoursVO = new ResHoursVO();

		resHoursVO.setResid(resid);
		resHoursVO.setOpening(opening);
		resHoursVO.setClosing(closing);
		dao.insert(resHoursVO);

		return resHoursVO;
	}

	public ResHoursVO updateResHours(String reshourid,  String resid, Timestamp opening, Timestamp closing) {

		ResHoursVO resHoursVO = new ResHoursVO();

		resHoursVO.setReshourid(reshourid);
		resHoursVO.setResid(resid);
		resHoursVO.setOpening(opening);
		resHoursVO.setClosing(closing);
		dao.update(resHoursVO);

		return resHoursVO;
	}

	public void deleteResHours(String reshourid) {
		dao.delete(reshourid);
	}

	public ResHoursVO getOneResHours(String reshourid) {
		return dao.findByPrimaryKey(reshourid);
	}
	
	public List<ResHoursVO> getAll() {
		return dao.getAll();
	}
	
	public List<ResHoursVO> getAllRes(String resid) {
		return dao.getAllRes(resid);
	}
}
