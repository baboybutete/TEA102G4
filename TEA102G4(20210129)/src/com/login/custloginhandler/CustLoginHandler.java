package com.login.custloginhandler;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;
//import com.admininfo.model.*;
import com.custinfo.model.*;
import com.favorites.model.FavoritesService;
import com.favorites.model.FavoritesVO;
//import com.resempmanager.model.*;
import com.resinfo.model.*;

import javax.servlet.annotation.WebServlet;

@WebServlet("/custloginhandler/custloginhandler.do")
public class CustLoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CustinfoService custinfoSvc = null;
	ResInfoVO resinfoVO = null;

	// 【檢查使用者輸入的帳號(account) 密碼(password)是否有效】
	// 【實際上應至資料庫搜尋比對】
	protected boolean allowUser(String account, String password, HttpServletRequest req) {
		Boolean bl = null;
		List<CustinfoVO> list = new CustinfoService().getAll();
//		System.out.println("account" + "password" + account + " " + password);

		for (CustinfoVO vo : list) {
			if ((vo.getCustaccount()).equals(account) && (vo.getCustpassword()).equals(password)) {
				bl = true;
//				System.out.println("帳密正確");
				HttpSession session = req.getSession();
				session.setAttribute("role", "cust");
				break;
			} else {
				bl = false;
//				System.out.println("帳密no");
			}
		}
		return bl;
	}

	protected boolean allowUser2(String account, String password, HttpServletRequest req) {
		Boolean bl = null;
		List<ResInfoVO> list1 = new ResInfoService().getAll();
		ResInfoService resInfoSVc = new ResInfoService();

		for (ResInfoVO vo : list1) {
			if ((vo.getResemail()).equals(account) && (vo.getRespassid()).equals(password)) {
				bl = true;
				HttpSession session = req.getSession();
				session.setAttribute("role", "res");
				String resid = vo.getResid();
//				System.out.println(resid);
				resinfoVO = resInfoSVc.getOneResInfo(resid);
				session.setAttribute("resInfoVO", resinfoVO);
				break;
			} else {
				bl = false;
			}
		}
		return bl;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		CustinfoService custinfoSvc = new CustinfoService();
		CustinfoVO custinfoVO = null;

		// 【取得使用者 帳號(account) 密碼(password)】
		String account = req.getParameter("account").trim();
		String password = req.getParameter("password");

		// 【檢查該帳號 , 密碼是否有效】
		if (!allowUser(account, password, req)) { // 【帳號 , 密碼無效時】
			if (!allowUser2(account, password, req)) {
				errorMsgs.add("帳號密碼錯誤");
				RequestDispatcher failureView = req.getRequestDispatcher("/login-html/login/custLogin.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}
			
			
			if (resinfoVO.getStatus().equals("停權")) {
				errorMsgs.add("已被停權");
				req.getSession().removeAttribute("resInfoVO");
				RequestDispatcher failureView = req.getRequestDispatcher("/login-html/login/custLogin.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			} else { // 【帳號 , 密碼有效時, 才做以下工作】

				HttpSession session = req.getSession();
				session.setAttribute("account", account); // *工作1: 才在session內做已經登入過的標識

				try {
					String location = (String) session.getAttribute("location");
					if (location != null) {
						session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
						res.sendRedirect(location);
						return;
					}
				} catch (Exception ignored) {
				}

				res.sendRedirect(req.getContextPath() + "/index_plat.jsp"); // *工作3:
																			// (-->如無來源網頁:則重導至login_success.jsp)
			}

		} else { // 【帳號 , 密碼有效時, 才做以下工作】
			HttpSession session = req.getSession();

			custinfoVO = custinfoSvc.getOneAccount(account);

			// 用來判斷收藏
//				String custid = custinfoVO.getCustid();
//				List<FavoritesVO> list1 = new FavoritesService().getOneAccount(custid);
//				String[] str = new String[list1.size()];
//				int i = 0;
//				for (FavoritesVO vo : list1) {
//					str[i] =  vo.getResid();					
//					i++;
//				}
//			session.setAttribute("str", str);

			session.setAttribute("account", account); // *工作1: 才在session內做已經登入過的標識
			session.setAttribute("custinfoVO", custinfoVO);//

			try {
				String location = (String) session.getAttribute("location");
				if (location != null) {
					//修改
					session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
					res.sendRedirect(location);
					return;
				}
			} catch (Exception ignored) {
				
			}

			// ok
			res.sendRedirect(req.getContextPath() + "/index_plat.jsp"); // *工作3:
																		// (-->如無來源網頁:則重導至login_success.jsp)

		}
	}
}
