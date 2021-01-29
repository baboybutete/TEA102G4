package com.resinfo.model;

import java.util.List;

//ok
public interface ResInfoDAO_interface {
	public void insert(ResInfoVO resinfovo);
	
	public void update(ResInfoVO resinfovo);

	public void delete(String resid);

	public ResInfoVO findByPrimaryKey(String resid);

	public List<ResInfoVO> getAll();
	
	public ResInfoVO checkEmail(String resmail, String respassid);
}