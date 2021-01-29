package com.waiting.controller;
//import javax.servlet.RequestDispatcher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");		
		
		// 將 userName 加到 session
		request.getSession().setAttribute("userName", userName);
		
		// 依不同權限跳轉到不同頁面
		String page = "consumer.jsp";
		if (userName.equals("admin")) 
			page = "restaurant.jsp";
		//若登入的是admin權限則跳到R00001頁面
		response.sendRedirect(request.getContextPath() + "/" + page + "?resid=R00001");
		/*
		request.setAttribute("userName", userName);
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
		*/
	}
}
