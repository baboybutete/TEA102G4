package com.admininfo.model;

import java.util.List;

public interface AdmininfoDAO_interface {
	public void insert(AdmininfoVO admininfoVO);
	public void update(AdmininfoVO admininfoVO);
	public void delete(String manid);
	public AdmininfoVO findByPrimaryKey(String manid);
	public List<AdmininfoVO> getAll();
	public AdmininfoVO findByManmail(String manmail);
}
