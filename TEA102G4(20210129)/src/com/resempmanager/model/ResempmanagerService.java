package com.resempmanager.model;

import java.util.List;

public class ResempmanagerService {
	private ResempmanagerDAO_interface dao;

	public ResempmanagerService() {
		dao = new ResempmanagerJDBCDAO();
	}
	
	public ResempmanagerVO addResempmanager(String purviewnum, String resid, String emppassword, String empname, String emptel,String empstatus, java.sql.Timestamp adddate) {
		ResempmanagerVO resempmanagerVO = new ResempmanagerVO();
		resempmanagerVO.setPurviewnum(purviewnum);
		resempmanagerVO.setResid(resid);
		resempmanagerVO.setEmppassword(emppassword);
		resempmanagerVO.setEmpname(empname);
		resempmanagerVO.setEmptel(emptel);
		resempmanagerVO.setEmpstatus(empstatus);
		resempmanagerVO.setAdddate(adddate);
		dao.insert(resempmanagerVO);
		return resempmanagerVO;
	}
	
	public ResempmanagerVO updateResempmanager(String purviewnum, String resid, String emppassword, String empname, String emptel,String empstatus, java.sql.Timestamp adddate, String empid) {
		ResempmanagerVO resempmanagerVO = new ResempmanagerVO();
		
		resempmanagerVO.setPurviewnum(purviewnum);
		resempmanagerVO.setResid(resid);
		resempmanagerVO.setEmppassword(emppassword);
		resempmanagerVO.setEmpname(empname);
		resempmanagerVO.setEmptel(emptel);
		resempmanagerVO.setEmpstatus(empstatus);
		resempmanagerVO.setAdddate(adddate);
		resempmanagerVO.setEmpid(empid);
		
		dao.update(resempmanagerVO);
		
		return resempmanagerVO;	
	}
	
	public void deleteResempmanager(String empid) {
		dao.delete(empid);
	}
	
	public ResempmanagerVO getOneResempmanager(String empid) {
		return dao.findByPrimaryKey(empid);
	}
	
	public List<ResempmanagerVO> getAll(){
		return dao.getAll();
	}
}
