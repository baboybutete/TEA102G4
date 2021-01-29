package com.addetail.model;

import java.sql.Timestamp;
import java.util.List;

public class AddetailService {
	private AddetailDAO_interface dao;
	
	public AddetailService() {
		dao = new AddetailJDBCDAO();
	}
	
	public AddetailVO addAddetail(String adtitle, String adcontent,  byte[] adimg, Timestamp addate, String adtype,
			String reviewstatus, Timestamp onshelftime, Timestamp offshelftime, String resid) {
		AddetailVO addetailVO = new AddetailVO();
		addetailVO.setAdtitle(adtitle);
		addetailVO.setAdcontent(adcontent);
		addetailVO.setAdimg(adimg);
		addetailVO.setAddate(addate);
		addetailVO.setAdtype(adtype);
		addetailVO.setReviewstatus(reviewstatus);
		addetailVO.setOnshelftime(onshelftime);
		addetailVO.setOffshelftime(offshelftime);
		addetailVO.setResid(resid);
		dao.insert(addetailVO);
		return addetailVO;
	}

	public AddetailVO updateAddetail(String adid, String adtitle, String adcontent,  byte[] adimg, Timestamp addate, String adtype, 
			String reviewstatus, Timestamp onshelftime, Timestamp offshelftime, String resid) {
		AddetailVO addetailVO = new AddetailVO();
		addetailVO.setAdid(adid);
		addetailVO.setAdtitle(adtitle);
		addetailVO.setAdcontent(adcontent);
		addetailVO.setAdimg(adimg);
		addetailVO.setAddate(addate);
		addetailVO.setAdtype(adtype);
		addetailVO.setReviewstatus(reviewstatus);
		addetailVO.setOnshelftime(onshelftime);
		addetailVO.setOffshelftime(offshelftime);
		addetailVO.setResid(resid);
		dao.update(addetailVO);
		return addetailVO;
	}

	public void deleteAddetail(String adid) {
		dao.delete(adid);
	}
	
	public AddetailVO getOneAddetail(String adid) {
		return dao.findByPrimaryKey(adid);
	}
	
	public List<AddetailVO> getAll(){
		return dao.getAll();
	}
	
	public List<AddetailVO> getOneResAddetail(String resid){
		return dao.getOneResAddetail(resid);
	}

	public AddetailVO addOneAddetail(String resid) {
		return dao.addOneAddetail(resid);
	}
}
