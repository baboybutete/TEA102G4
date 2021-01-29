package com.addetail.model;

import java.util.List;

public interface AddetailDAO_interface {
	public void insert(AddetailVO addetailVO);
	public void update(AddetailVO addetailVO);
	public void delete(String adid);
	public AddetailVO findByPrimaryKey(String adid);
	public List<AddetailVO> getAll();
	public List<AddetailVO> getOneResAddetail(String resid);
	public AddetailVO addOneAddetail(String resid);
}
