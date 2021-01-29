<%@page import="com.orderdetail.model.OrderDetailService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.orderdetail.model.*"%>
<%@	page import="com.resinfo.model.ResInfoVO"%>

<%
// 	OrderDetailService orderDetailSvc = new OrderDetailService();
// 	String str = request.getParameter("resid");
// 	List<OrderDetailVO> list = orderDetailSvc.getAllDetail(str);
// 	pageContext.setAttribute("list",list);
	
	ResInfoVO resinfoVO = (ResInfoVO)session.getAttribute("resInfoVO");
	OrderDetailService orderDetailSvc = new OrderDetailService();
	List<OrderDetailVO> list = orderDetailSvc.getOrderInfo(resinfoVO.getResid());
	pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>餐廳訂單管理</title>

<style>
form{
    margin-top: 15px;

}
  table#table-1 {
	background-color: #CCCCCF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
  	font-size: 20px;
    color: blue;
    display: inline;
  }
   li{
  	font-size: 30px;
  	color: red;
  }
</style>

<style>
  table {
	width: 1060px;
	background-color: #CCCCCF;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
  	text-align: center;
    border: 1px solid black;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
  div.info{
  	position: absolute;
    margin-top: 96px;
    margin-left: 201px;
  }
/*   footer{ */
/* 	position: relative; */
/*     margin-top: 300px; */
/* } */
  div.addmeal{
    margin-left: 200px;
}
  li{
 	list-style-type: none;
}
</style>
</head>
<body>


 <div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
 
 
  <div class="info">
<table>
	<tr>
		<th>訂單編號</th>
		<th>訂位姓名</th>
		<th>電話</th>
		<th>人數</th>
		<th>座位號碼</th>
		<th>訂位時間</th>
		<th>下單日期</th>
		<th>報到狀態</th>
		<th>報到狀態修改</th>
		<th>餐點資訊</th>
<!-- 		<th>修改</th> -->
	</tr>
	<c:forEach var="orderDetailVO" items="${list}">
		
		<tr>
			<td>${orderDetailVO.orderid}</td>
			<td>${orderDetailVO.subscriber}</td>
			<td>${orderDetailVO.subphone}</td>
			<td>${orderDetailVO.subnumber}</td>
			<td>${orderDetailVO.seat}</td>
			<td><fmt:formatDate value="${orderDetailVO.subtime}" pattern="yyyy-MM-dd HH:mm"/></td>
			<td><fmt:formatDate value="${orderDetailVO.ordertime}" pattern="yyyy-MM-dd"/></td>
			<td>
				${orderDetailVO.checkin}
			</td>
			<td>
			  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/orderdetail/OrderDetailServlet" >
			     	<input type="hidden" name="action"	value="getOne_For_Update">
			     	<input type="hidden" name="orderid"  value="${orderDetailVO.orderid}">
			     	<input type="hidden" name="checkin"  value="已報到">
			     	<input type="submit" value="報到">
			  </FORM>
			</td>
			<td>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/orderdetail/OrderDetailServlet" >
		        	<input type="hidden" name="action" value="getOneInfo_For_Display"> 
	 	 			<input type="hidden" name="orderid" value="${orderDetailVO.orderid}"> 
	  				<input type="submit" value="詳細">
		    	</FORM>
		    </td>
		</tr>
	</c:forEach>
</table>
</div>
<!-- <div class="flip"><button class="btn1" >詳細</button></div> -->




<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>

</html>