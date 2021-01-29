package com.map.controller;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.map.model.MapService;
import com.map.model.MapVO;

@WebServlet("/map/MapServlet")
@MultipartConfig
public class MapServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
			req.setCharacterEncoding("UTF-8");
			
			
			MapService mapService = new MapService();
			List<MapVO> listMapVO = mapService.getAll();
			String action = req.getParameter("action");
			
			if("search".equals(action)) {
				//前端用input所以沒輸入會是空字串
				String resaddid = req.getParameter("resaddid");
				String classname = req.getParameter("classname");
				
				MapService mapSer = new MapService();
				List<MapVO> result = mapSer.getSearchResult(resaddid.trim(), classname.trim());
				
				req.setAttribute("result", result);
				String uri = "/front-end/cust/searchForRest/searchResult.jsp";
				RequestDispatcher succesessView = req.getRequestDispatcher(uri);
				succesessView.forward(req, res);
			}
			
			
//			req.setAttribute("listMapVO", listMapVO);
//			String url = "/front-end/map/map.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url);
//			successView.forward(req, res);
			
	}
	
}
