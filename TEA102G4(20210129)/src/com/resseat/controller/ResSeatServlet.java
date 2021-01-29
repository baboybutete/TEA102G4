package com.resseat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.resinfo.model.ResInfoService;
import com.resinfo.model.ResInfoVO;
import com.resseat.model.ResSeatDistributionVO;
import com.resseatoracle.model.ResSeatOracleService;
import com.resseatoracle.model.ResSeatOracleVO;
import com.ressseat.jedis.JedisHandleSeat;
import com.ressseat.jedis.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//設定座位圖
@WebServlet("/ResSeatServlet")
public class ResSeatServlet extends HttpServlet {
	private ResInfoService service;
	private static JedisPool pool;

	@Override
	public void init() {
		this.service = new ResInfoService();
		pool = JedisPoolUtil.getJedisPool();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		
		/*
		 * 之後登入的改寫，用這裡的resid update seat HttpSession session = req.getSession(); String
		 * resid = session.getAttribute("resid"); if(resid == null) { location =
		 * req.getRequestURI(); session.setAttribute("location",location);
		 * res.sendRedirect(location); }
		 */
		String action = req.getParameter("action");
		String resid = req.getParameter("resid");
		System.out.println("connect");
		

		if (action != null && "getSeatDistribution".equals(action)) {
			Jedis jedis = pool.getResource();
			jedis.auth("123456");
			ResSeatDistributionVO vo = new ResSeatDistributionVO();
			String msg = null;
			if(resid.trim().length() == 0) {
				vo.setStatus("2");
				msg = new  JSONObject(vo).toString();
				out.println(msg);
				return;
			}
			
			String regex = "^[R][0-9]{5}$";
			if(!resid.matches(regex)) {
				vo.setStatus("3");
				msg = new  JSONObject(vo).toString();
				out.println(msg);
				return;
			}
			
			
			/*之後改寫這個就不會發生，因為會先用session先確定有登入否則就會導回燈入頁面*/
			
			String seatDistribution = jedis.hget("seatDistribution", resid);
			jedis.close();
			vo.setResid(resid);
			if(seatDistribution == null) {
				//這家餐廳沒有資料
				vo.setStatus("1");
			}else {
				vo.setStatus("yes");
				vo.setSeatData(seatDistribution);
			}
			msg = new  JSONObject(vo).toString();
			
			/*在前端處理JSON*/
			out.println(msg);
		}
		

		if ((action != null && "saveResSeatDistribution".equals(action))) {
			/* 暫時的寫法，用get transfer req parameter ，只新增corrdinate不更改其他東西 */
			String corrdinate = req.getParameter("seatData");
			/* get resid and corridinate in the seatData */
			if(resid == null || resid.trim().isEmpty()) {
				out.println("");
				return;
			}
			
			/*將狀態圖刪除*/
			JedisHandleSeat.deleteAllSeatState(resid);
			
			/*存在Redis分布圖*/
			JedisHandleSeat.saveSeatDistribution(resid, corrdinate);
			
			/*存在Oracle 的resinfo*/
			ResInfoVO originalVO = service.getOneResInfo(resid);
			
			if(originalVO == null) {
				out.println("");
				return;
			}
			String resname = originalVO.getResname();
			String resaddid = originalVO.getResaddid();
			byte[] resimg = originalVO.getResimg();
			String barrierfree = originalVO.getBarrierfree();
			String parentchild = originalVO.getParentchild();
			String traffic = originalVO.getTraffic();
			String parking = originalVO.getParking();
			String payinfo = originalVO.getPayinfo();
			String notifcont = originalVO.getNotifcont();
			String resemail = originalVO.getResemail();
			String respassid = originalVO.getRespassid();
			Integer currentwaitingnum = originalVO.getCurrentwaitingnum();
			Integer subtimediff = originalVO.getSubtimediff();
			Integer waitdistance = originalVO.getWaitdistance();
			String contact = originalVO.getContact();
			String contactphon = originalVO.getContactphon();
			Timestamp adddate = originalVO.getAdddate();
			String status = originalVO.getStatus();
			String lat = originalVO.getLat();
			String lng = originalVO.getLng();
			
			
			/*update resinfovo座位分布圖*/
			ResInfoVO resinfovo = service.updateResInfo(resid, resname, resaddid, resimg, barrierfree, parentchild, traffic, parking, payinfo, notifcont, resemail, respassid, currentwaitingnum, subtimediff, waitdistance, contact, contactphon, corrdinate, adddate, status, lat, lng);
			System.out.println("save in oracle");
			
			// oracle 更新狀態
			ResSeatOracleService resSOSer = new ResSeatOracleService();
			ResSeatOracleVO resSeatOracleVO = resSOSer.getOneResSeatOracleVO(resid, "now");//只限定存time
			if(resSeatOracleVO != null) {
				//update
				resSOSer.updateResSeatOracle(resid, "now", corrdinate);
				System.out.println("update state");
			}else {
				//insert
				resSOSer.addResSeatOracle(resid, "now", corrdinate);
				System.out.println("insert state");
			}
			
			
			/*得到更新的seatDate並回傳回去*/
			String corrdinateNew = resinfovo.getCorrdinate();
			/*在前端處理JSON*/
//			out.println(corrdinateNew);
			out.write(corrdinateNew);
			
		}
		

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req, res);
	}

}
