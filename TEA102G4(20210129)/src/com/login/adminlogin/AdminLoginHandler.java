package com.login.adminlogin;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.addetail.model.AddetailService;
import com.addetail.model.AddetailVO;
import com.admininfo.model.AdmininfoService;
import com.admininfo.model.AdmininfoVO;


@WebServlet("/adminLoginHandler/adminLoginHandler.do")
public class AdminLoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdmininfoService admininfoSvc = null;
	
	protected boolean allowAdmin(String manmail, String manpassword, HttpServletRequest req) {
		Boolean bl = null;
		List<AdmininfoVO> list = new AdmininfoService().getAll();
		
		for (AdmininfoVO admininfoVO : list) {
			if ((admininfoVO.getManmail()).equals(manmail) && (admininfoVO.getManpassword()).equals(manpassword)) {
				bl = true;
				System.out.println("帳密正確");
				HttpSession session = req.getSession();
				session.setAttribute("role", "admin");
				break;
			} else {
				bl = false;
				System.out.println("帳密錯誤");
			}
		}
		return bl;
	}
	

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		AdmininfoService admininfoSvc = new AdmininfoService();
		AdmininfoVO admininfoVO = null;
		
		String manmail = req.getParameter("manmail").trim();
		System.out.println(manmail.length());
		String manpassword = req.getParameter("manpassword");
		
		if (!allowAdmin(manmail, manpassword, req)) {
			errorMsgs.add("帳號密碼錯誤");
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminLogin.jsp");
			failureView.forward(req, res);
			return;
			
		} else {
			
			HttpSession session = req.getSession();
			session.setAttribute("manmail", manmail);
			session.setAttribute("admininfoVO", admininfoVO);
			
			try {
				String location = (String) session.getAttribute("location");
				if (location != null) {
					session.removeAttribute("location");
					res.sendRedirect(location);
					return;
				}
			} catch (Exception e) {}
			res.sendRedirect(req.getContextPath() + "/back-end/custinfo/listAllCustinfo.jsp");
		}
	}

}
