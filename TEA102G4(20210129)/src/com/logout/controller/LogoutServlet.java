package com.logout.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/logoutservlet/logoutservlet.do")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
          response.setContentType("text/html;charset=UTF-8");
//          PrintWriter out = response.getWriter();
          request.setCharacterEncoding("UTF-8");
//          request.getRequestDispatcher("").include(request, response);
          HttpSession session = request.getSession();
          // 清除資料
          session.invalidate();
          response.sendRedirect(request.getContextPath() + "/index_plat.jsp");
//          out.close();
    }
}
