package com.reshours.model;

import java.util.List;

public interface ResHoursDAO_interface {
	public void insert(ResHoursVO reshoursvo);

	public void update(ResHoursVO reshoursvo);

	public void delete(String reshourid);

	public ResHoursVO findByPrimaryKey(String reshourid);

	public List<ResHoursVO> getAll();
	
	public List<ResHoursVO> getAllRes(String resid);
}
