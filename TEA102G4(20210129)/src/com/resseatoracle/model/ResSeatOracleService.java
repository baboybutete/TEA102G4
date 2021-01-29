package com.resseatoracle.model;

import java.util.List;

public class ResSeatOracleService {
	private ResSeatOracleDAO_interface dao;
	
	public ResSeatOracleService() {
		dao = new ResSeatOracleJDBCDAO();
		
	}
	public ResSeatOracleVO addResSeatOracle(String resid,String time,String seatData) {

		ResSeatOracleVO resSeatOracleVO = new ResSeatOracleVO();
		resSeatOracleVO.setResid(resid);
		resSeatOracleVO.setTime(time);
		resSeatOracleVO.setSeatData(seatData);
		dao.insert(resSeatOracleVO);
		return resSeatOracleVO;
	}
	
	public ResSeatOracleVO updateResSeatOracle(String resid,String time,String seatData) {
		ResSeatOracleVO resSeatOracleVO = new ResSeatOracleVO();
		resSeatOracleVO.setResid(resid);
		resSeatOracleVO.setTime(time);
		resSeatOracleVO.setSeatData(seatData);
		dao.update(resSeatOracleVO);
		return dao.findByPrimaryKey(resid,time);
	}

	
	
	public List<ResSeatOracleVO> getAll() {
		return dao.getAll();
	}

	public ResSeatOracleVO getOneResSeatOracleVO(String resid,String time) {
	
		return dao.findByPrimaryKey(resid,time);
	}

	
	
}
