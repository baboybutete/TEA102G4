package com.admininfo.model;

import java.util.List;

public class AdmininfoService {
	private AdmininfoDAO_interface dao;

	public AdmininfoService() {
		this.dao = new AdmininfoJDBCDAO();
	}

	public AdmininfoVO addAdmininfo(String manname, String manpassword, String manmail, String manrealname,
			String mantel, String manstatus, String manpurview) {
		AdmininfoVO admininfoVO = new AdmininfoVO();
		admininfoVO.setManmail(manmail);
		admininfoVO.setManname(manname);
		admininfoVO.setManpassword(manpassword);
		admininfoVO.setManpurview(manpurview);
		admininfoVO.setManstatus(manstatus);
		admininfoVO.setManrealname(manrealname);
		admininfoVO.setMantel(mantel);
		dao.insert(admininfoVO);
		return admininfoVO;
	}

	public AdmininfoVO updateAdmininfo(String manid, String manname, String manpassword, String manmail,
			String manrealname, String mantel, String manstatus, String manpurview) {
		AdmininfoVO admininfoVO = new AdmininfoVO();
		admininfoVO.setManid(manid);
		admininfoVO.setManmail(manmail);
		admininfoVO.setManname(manname);
		admininfoVO.setManpassword(manpassword);
		admininfoVO.setManpurview(manpurview);
		admininfoVO.setManstatus(manstatus);
		admininfoVO.setManrealname(manrealname);
		admininfoVO.setMantel(mantel);
		dao.update(admininfoVO);
		return admininfoVO;
	}

	public void deleteAdmininfo(String manid) {
		dao.delete(manid);
	}

	public AdmininfoVO getOneAdmin(String manid) {
		return dao.findByPrimaryKey(manid);
	}

	public List<AdmininfoVO> getAll() {
		return dao.getAll();
	}
	
	public AdmininfoVO getOneManmail(String manmail) {
		return dao.findByManmail(manmail);
	}
}
