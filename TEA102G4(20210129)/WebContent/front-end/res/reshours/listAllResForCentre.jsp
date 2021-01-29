<%@page import="java.sql.Timestamp"%>
<%@page import="com.resinfo.model.ResInfoVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.reshours.model.*"%>


<%
// 	ResHoursVO resHoursVO = (ResHoursVO)session.getAttribute("resHoursVO");
// 	ResHoursService reshoursSvc = new ResHoursService();
//		resInfoVO
// 	String str = request.getParameter("resid");
// 	List<ResHoursVO> list = reshoursSvc.getAllRes(str);
// 	pageContext.setAttribute("list",list);
%>

<%
	ResInfoVO resinfoVO = (ResInfoVO)session.getAttribute("resInfoVO");
	ResHoursService reshoursSvc = new ResHoursService();
	List<ResHoursVO> list = reshoursSvc.getAllRes(resinfoVO.getResid());
	pageContext.setAttribute("list",list);
	
%>


<html>
<head>
<title>餐廳營業時間資料 </title>

<style>
div.info {
    position: relative;
	margin-top: -450px;
	margin-left: 300px;
}
div.container div.row{
    margin-left: 20px;
}
div.container{
}
div.container div.col-3{
	font-size: 25px;
  	text-align: left;
}
div.container div.col-4{
	font-size: 20px;
  	text-align: left;
}
input[type=text]{
	width: 300px;
	padding:5px;
	font-size: 18px;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
footer{
	position: relative;
    margin-top: 300px;
}
</style>
<link href="<%=request.getContextPath()+"/css/bootstrap.min.css"%>" rel="stylesheet">
	  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/res_member_centre.css">
</head>
<body>
 
      <div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>



<div class="info">
	<c:if test="${empty list}">
				<div class="container">
	<% Timestamp open = new Timestamp(System.currentTimeMillis());
		Timestamp close = new Timestamp(System.currentTimeMillis());
	
	%>
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reshours/ResHoursServlet" style="margin-bottom: 0px;">
    <div class="row">
    <div class="col-3">餐廳編號:</div>
    <div class="col-4">${resInfoVO.resid}</div>
    </div><br>
  <div class="row">
  <div class="col-3">開始營業時間:</div>
    <div class="col-4"><input type="time" name="opening" value="<fmt:formatDate value="<%=open %>" pattern="HH:mm"/>"></input></div>
    </div><br>
    <div class="row">
  <div class="col-3">結束營業時間:</div>
    <div class="col-4"><input type="time" name="closing" value="<fmt:formatDate value="<%=close %>" pattern="HH:mm"/>"></div>
    </div>
  </div><br>

  <div class="container">
  <div class="col-3">
  <div class="row">
  	<input type="submit" value="新增營業時間">
    <input type="hidden" name="resid"  value="${resInfoVO.resid}">
	<input type="hidden" name="action"	value="insert">
  </div>
  </div>
  </div>
</FORM>
		
	</c:if>
	
	<c:forEach var="resHoursVO" items="${list}">
		<div class="container">
    <div class="row">
    <div class="col-3">餐廳編號:</div>
    <div class="col-4">${resHoursVO.resid}</div>
    </div><br>
  <div class="row">
  <div class="col-3">開始營業時間:</div>
    <div class="col-4"><fmt:formatDate value="${resHoursVO.opening}" pattern="HH:mm"/></input></div>
    </div><br>
    <div class="row">
  <div class="col-3">結束營業時間:</div>
    <div class="col-4"><fmt:formatDate value="${resHoursVO.closing}" pattern="HH:mm"/></div>
    </div>
  </div><br>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reshours/ResHoursServlet" style="margin-bottom: 0px;">
  <div class="container">
  <div class="col-3">
  <div class="row">
  	<input type="submit" value="資料修改">
    <input type="hidden" name="reshourid"  value="${resHoursVO.reshourid}">
	<input type="hidden" name="action"	value="getOne_For_Update">
  </div>
  </div>
  </div>
</FORM>
	</c:forEach>
</div>

<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>

</body>
</html>