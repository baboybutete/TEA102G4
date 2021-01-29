package com.custinfo.model;

import java.util.List;

public interface CustinfoDAO_interface {
	public void insert(CustinfoVO custinfoVO);

	public void update(CustinfoVO custinfoVO);

	public void delete(String custid);

	public CustinfoVO findByPrimaryKey(String custid);
	
	public CustinfoVO findByAccount(String custaccount);

	public List<CustinfoVO> getAll();
}
