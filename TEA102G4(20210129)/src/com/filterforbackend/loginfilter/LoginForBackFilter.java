package com.filterforbackend.loginfilter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebFilter("/LoginForBackFilter")
public class LoginForBackFilter implements Filter {

	private FilterConfig config;
	
	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		
		Object manmail = session.getAttribute("manmail");
		if (manmail == null) {
			Enumeration<String> clentParameters = req.getParameterNames();
			
			while(clentParameters.hasMoreElements()) {
				String param = clentParameters.nextElement();
				session.setAttribute(param, request.getParameter(param));
			}
			
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/back-end/adminLogin.jsp");
			return;
		} else {
			chain.doFilter(request, response);
		}
	}
}
