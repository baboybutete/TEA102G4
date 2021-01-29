package com.resinfo.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.menu.model.MenuService;


//OK
public class ResInfoService {
	
	private ResInfoDAO_interface dao;
	
	public ResInfoService() {
		dao = new ResInfoJDBCDAO();
	}
	
	public ResInfoVO addResInfo(String resname, String resaddid, byte[] resimg, String barrierfree, String parentchild, String traffic, String parking, String payinfo, String notifcont, String resemail, String respassid, Integer currentwaitingnum, Integer subtimediff, Integer waitdistance, String contact, String contactphon, String corrdinate, Timestamp adddate) {

		ResInfoVO resInfoVO = new ResInfoVO();

		resInfoVO.setResname(resname);
		resInfoVO.setResaddid(resaddid);
		resInfoVO.setResimg(resimg);
		resInfoVO.setBarrierfree(barrierfree);
		resInfoVO.setParentchild(parentchild);
		resInfoVO.setTraffic(traffic);
		resInfoVO.setParking(parking);
		resInfoVO.setPayinfo(payinfo);
		resInfoVO.setNotifcont(notifcont);
		resInfoVO.setResemail(resemail);
		resInfoVO.setRespassid(respassid);
		resInfoVO.setCurrentwaitingnum(currentwaitingnum);
		resInfoVO.setSubtimediff(subtimediff);
		resInfoVO.setWaitdistance(waitdistance);
		resInfoVO.setContact(contact);
		resInfoVO.setContactphon(contactphon);
		resInfoVO.setCorrdinate(corrdinate);
		resInfoVO.setAdddate(adddate);
//		resInfoVO.setStatus(status);
//		resInfoVO.setLat(lat);
//		resInfoVO.setLng(lng);
		dao.insert(resInfoVO);

		return resInfoVO;
	}
	
	public ResInfoVO addResInfo(String resname, String resaddid, byte[] resimg, String barrierfree, String parentchild, String traffic, String parking, String payinfo, String notifcont, String resemail, String respassid, Integer currentwaitingnum, Integer subtimediff, Integer waitdistance, String contact, String contactphon, String corrdinate, Timestamp adddate,String status, String lat, String lng) {

		ResInfoVO resInfoVO = new ResInfoVO();

		resInfoVO.setResname(resname);
		resInfoVO.setResaddid(resaddid);
		resInfoVO.setResimg(resimg);
		resInfoVO.setBarrierfree(barrierfree);
		resInfoVO.setParentchild(parentchild);
		resInfoVO.setTraffic(traffic);
		resInfoVO.setParking(parking);
		resInfoVO.setPayinfo(payinfo);
		resInfoVO.setNotifcont(notifcont);
		resInfoVO.setResemail(resemail);
		resInfoVO.setRespassid(respassid);
		resInfoVO.setCurrentwaitingnum(currentwaitingnum);
		resInfoVO.setSubtimediff(subtimediff);
		resInfoVO.setWaitdistance(waitdistance);
		resInfoVO.setContact(contact);
		resInfoVO.setContactphon(contactphon);
		resInfoVO.setCorrdinate(corrdinate);
		resInfoVO.setAdddate(adddate);
		resInfoVO.setStatus(status);
		resInfoVO.setLat(lat);
		resInfoVO.setLng(lng);
		dao.insert(resInfoVO);

		return resInfoVO;
	}

	public ResInfoVO updateResInfo(String resid, String resname, String resaddid, byte[] resimg, String barrierfree, String parentchild, String traffic, String parking, String payinfo, String notifcont, String resemail, String respassid, Integer currentwaitingnum, Integer subtimediff, Integer waitdistance, String contact, String contactphon, String corrdinate, Timestamp adddate, String status, String lat, String lng) {

		ResInfoVO resInfoVO = new ResInfoVO();
		
		resInfoVO.setResid(resid);
		resInfoVO.setResname(resname);
		resInfoVO.setResaddid(resaddid);
		resInfoVO.setResimg(resimg);
		resInfoVO.setBarrierfree(barrierfree);
		resInfoVO.setParentchild(parentchild);
		resInfoVO.setTraffic(traffic);
		resInfoVO.setParking(parking);
		resInfoVO.setPayinfo(payinfo);
		resInfoVO.setNotifcont(notifcont);
		resInfoVO.setResemail(resemail);
		resInfoVO.setRespassid(respassid);
		resInfoVO.setCurrentwaitingnum(currentwaitingnum);
		resInfoVO.setSubtimediff(subtimediff);
		resInfoVO.setWaitdistance(waitdistance);
		resInfoVO.setContact(contact);
		resInfoVO.setContactphon(contactphon);
		resInfoVO.setCorrdinate(corrdinate);
		resInfoVO.setAdddate(adddate);
		resInfoVO.setStatus(status);
		resInfoVO.setLat(lat);
		resInfoVO.setLng(lng);
		dao.update(resInfoVO);

		return resInfoVO;
	}

	public void deleteResInfo(String resid) {
		dao.delete(resid);
	}

	public ResInfoVO getOneResInfo(String resid) {
		return dao.findByPrimaryKey(resid);
	}

	public List<ResInfoVO> getAll() {
		return dao.getAll();
	}
	
	public ResInfoVO checkEmail(String resmail, String respassid) {
		return dao.checkEmail(resmail, respassid);
	}
	
	//以後有時間改成String[] 
	public List<ResInfoVO> getKeyWordResInfo(String resname,String resaddid){
		List<ResInfoVO> list = dao.getAll();
		List<ResInfoVO> result =  list.stream().filter(vo->vo.getResname().contains(resname) && vo.getResaddid().contains(resaddid))
						 						.collect(Collectors.toList());
		return result;
	}


	
	
		//給後台查餐廳名稱和編碼
	public List<ResInfoVO> getKeyWordResInfo(String keyWord){
			List<ResInfoVO> list = dao.getAll();
			List<ResInfoVO> result =  list.stream().filter(vo->vo.getResname().contains(keyWord) || vo.getResid().contains(keyWord))
						 						   .collect(Collectors.toList());
		return result;
	}
	
	
	
	
}