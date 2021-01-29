package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/member/member.do")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String role = (String) req.getSession().getAttribute("role");
		
			switch (role) {
			case "cust":
				res.sendRedirect(req.getContextPath() + "/front-end/cust/custindex.jsp");//消費者會員中心
				break;
			case "res":
				res.sendRedirect(req.getContextPath() + "/front-end/res/res_member_centre.jsp"); //正常要到餐廳會員中心
				
				break;
			default:
				res.sendRedirect(req.getContextPath() + "/login-html/login/custLogin.jsp");
			}
		
	}

}
