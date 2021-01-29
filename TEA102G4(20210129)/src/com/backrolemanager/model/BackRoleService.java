package com.backrolemanager.model;

import java.util.List;



public class BackRoleService {
	private BackRoleDAO_interface DAO;
	
	public BackRoleService() {
		this.DAO = new BackRoleDAOJDBC();
	}
	
	public BackRoleVO addBackRole(String manpurview,String purviewcontent) {
		BackRoleVO backRoleVO = new BackRoleVO();
		backRoleVO.setPurviewcontent(purviewcontent);
		backRoleVO.setManpurview(manpurview);
		DAO.insert(backRoleVO);
		return backRoleVO;
	};

	public BackRoleVO updateBackRole(String manpurview, String purviewcontent) {
		BackRoleVO backRoleVO = new BackRoleVO();
		backRoleVO.setManpurview(manpurview);
		backRoleVO.setPurviewcontent(purviewcontent);
		DAO.update(backRoleVO);
		return backRoleVO;
	};

	public void deleteBackRole(String manpurview) {
		DAO.delete(manpurview);
	};

	public BackRoleVO getOneAdmin(String manpurview) {
		return DAO.findByPrimaryKey(manpurview);
	};

	public List<BackRoleVO> getAll() {
		return DAO.getAll();
	};

}
