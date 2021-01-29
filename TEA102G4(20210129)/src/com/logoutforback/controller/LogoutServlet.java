package com.logoutforback.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/logout/logout.do")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        // 清除資料
        session.invalidate();
        RequestDispatcher logoutView = req
				.getRequestDispatcher("/back-end/relogin.jsp");
        logoutView.forward(req, res);
//        out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
//        out.println("<BODY>你以登出!<BR>");
//        out.println("請按此重新登入 <A HREF="+req.getContextPath()+"/back-end/adminLogin.jsp>重新登入</A>");
//        out.println("</BODY></HTML>");
//        out.close();
	}

}
