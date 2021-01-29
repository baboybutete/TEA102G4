package com.resseatoracle.model;

import java.util.List;

interface ResSeatOracleDAO_interface {
	void insert(ResSeatOracleVO resSeatOracleVO);
	void update(ResSeatOracleVO resSeatOracleVO);
	void delete(String resid,String time);
	ResSeatOracleVO findByPrimaryKey(String resid,String time);
	public List<ResSeatOracleVO> getAll();
	
}
