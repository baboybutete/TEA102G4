<%@page import="com.menu.model.MenuVO"%>
<%@page import="java.util.Base64"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.menu.model.*"%>
<%@ page import="com.menu.controller.*"%>
<%@ include file="getBaseStirng.file"%>

<%
MenuVO menuVO = (MenuVO) request.getAttribute("menuVO"); 
if(menuVO != null){
	byte[] imgbyte = menuVO.getMealimg();
	String imgString =  getBaseString(imgbyte);
	pageContext.setAttribute("imgString",imgString);
}
%>


<html>
<head>
<meta charset="UTF-8">
<title>餐廳菜單資訊</title>
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
    margin-top: 135px;
    margin-left: 201px;
  }
  footer{
	position: relative;
    margin-top: 400px;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
</style>
</head>
<body>

    <div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
    
<div class="info">
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/menu/MenuServlet" name="form1" enctype="multipart/form-data">
	<table>
		<tr>
<!-- 			<th>餐點編號</th> -->
<!-- 			<th>餐廳編號</th> -->
			<th>分類</th>
			<th>餐點名稱</th>
			<th>價位</th>
			<th>圖片</th>
			<th>狀態</th>
		</tr>

		<tr>
<%-- 			<td>${menuVO.mealid}</td> --%>
<%-- 			<td>${menuVO.resid}</td> --%>
			<td>${menuVO.classname}</td>
			<td>${menuVO.mealname}</td>
			<td>${menuVO.mealprice}元</td>
			<td><img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/resinfo/images/null2.jpg'" width="100px" height="50px"></td>
			<td>${menuVO.mealstatus}</td>
		</tr>
	</table>
</FORM>
	
	<jsp:useBean id="menuSvc" scope="page" class="com.menu.model.MenuService" />
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/menu/menu.do" style="margin-bottom: 0px;">
  <div class="container">
  <div class="col-3">
  <div class="row">
  	<input type="submit" value="資料修改">
    <input type="hidden" name="mealid"  value="${menuVO.mealid}">
	<input type="hidden" name="action"	value="getOne_For_Update">
  </div>
  </div>
  </div>
</FORM>
	</div>
	
	
<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>
</html>