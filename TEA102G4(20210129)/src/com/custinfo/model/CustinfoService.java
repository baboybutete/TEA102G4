package com.custinfo.model;

import java.sql.Timestamp;
import java.util.List;

public class CustinfoService {
	CustinfoJDBCDAO DAO;

	public CustinfoService() {
		this.DAO = new CustinfoJDBCDAO();
	}
	
	public CustinfoVO addCustinfo( String custname, String custtel, String custpassword, String custstatus, Timestamp registrationtime,
			String custaccount, byte[] custpicture) {
		CustinfoVO custinfoVO = new CustinfoVO();
		custinfoVO.setCustname(custname);
		custinfoVO.setCusttel(custtel);
		custinfoVO.setCustaccount(custaccount);
		custinfoVO.setCustpassword(custpassword);
		custinfoVO.setCuststatus(custstatus);
		custinfoVO.setRegistrationtime(registrationtime);
		custinfoVO.setCustpicture(custpicture);
		DAO.insert(custinfoVO);
		return custinfoVO;
	};
	

	public CustinfoVO updateCustinfo(String custid, String custname, String custtel, String custpassword, String custstatus,
			String custaccount, byte[] custpicture) {
		CustinfoVO custinfoVO = new CustinfoVO();
		custinfoVO.setCustid(custid);
		custinfoVO.setCustaccount(custaccount);
		custinfoVO.setCustname(custname);
		custinfoVO.setCustpicture(custpicture);
		custinfoVO.setCusttel(custtel);
		custinfoVO.setCustpassword(custpassword);
		custinfoVO.setCuststatus(custstatus);
		DAO.update(custinfoVO);
		return custinfoVO;
	};

	public void deleteCustinfo(String custid) {
		DAO.delete(custid);
	};

	public CustinfoVO getOneCust(String custid) {
		return DAO.findByPrimaryKey(custid);
	};

	public List<CustinfoVO> getAll() {
		return DAO.getAll();
	};
	
	public CustinfoVO getOneAccount(String custaccount) {
		return DAO.findByAccount(custaccount);
	}
}
