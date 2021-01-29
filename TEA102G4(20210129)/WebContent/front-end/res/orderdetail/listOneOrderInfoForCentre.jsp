<%@page import="com.orderdetail.model.OrderDetailService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="java.util.stream.Collectors"%>
<%@ page import="com.orderdetail.model.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%
	OrderDetailService orderDetailSvc = new OrderDetailService();
	String str = request.getParameter("orderid");
	List<OrderDetailVO> list = orderDetailSvc.getOneDetail(str);
	pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>餐廳營業時間資料</title>

<style>
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
/*     margin-top: 500px; */
/* } */
  div.addmeal{
    margin-left: 200px;
}
  li{
 	list-style-type: none;
}
div.sum{
	position: absolute;
    text-align: center;
    width: 220px;
    background-color: #ffa2a2;
    font-size: 30px;
    margin-left: 1042px;
    margin-top: 260px;
}
</style>
</head>
<body>


 <div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
 
 
  <div class="info">
<table>
	<tr>
		<th>訂單編號</th>
		<th>餐點名稱</th>
		<th>餐點數量</th>
		<th>餐點單價</th>

	</tr>
	<c:forEach var="orderDetailVO" items="${orderDetailVO}">
		
		<tr>
			<td>${orderDetailVO.orderid}</td>
			<td>${orderDetailVO.mealname}</td>
			<td>${orderDetailVO.mealcount}份</td>
			<td>${orderDetailVO.mealprice}元</td>
		</tr>
	</c:forEach>
</table>
</div>

<%
	int sum = 0;
	for(int i = 0; i < list.size(); i++){
		OrderDetailVO orderDetailVO = (OrderDetailVO) list.get(i);
		sum += orderDetailVO.getTotalprice();
	}
%>
		<div class="sum">總額: <%=sum %>元</div>
	
	

	
<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>
</html>