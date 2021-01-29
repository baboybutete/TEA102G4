package com.resempmanager.model;

import java.util.List;

public interface ResempmanagerDAO_interface {
	public void insert(ResempmanagerVO resempmanagerVO);
    public void update(ResempmanagerVO resempmanagerVO);
    public void delete(String empid);
    public ResempmanagerVO findByPrimaryKey(String empid);
    public List<ResempmanagerVO> getAll();
}
