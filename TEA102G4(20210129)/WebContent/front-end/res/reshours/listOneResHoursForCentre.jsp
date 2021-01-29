<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.reshours.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
ResHoursVO resHoursVO = (ResHoursVO) request.getAttribute("resHoursVO"); 
// ResHoursVO resHoursVO = (ResHoursVO)session.getAttribute("resHoursVO"); 
%>

<html>
<head>
<title>餐聽營業時間資料</title>

<style>
form{
	margin-top: -500px;
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
div.info {
    position: relative;
	margin-top: 50px;
	margin-left: 0px;
}
</style>
</head>
<body>
 <div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>

 <div class="info">
<!--  <h3>餐聽營業時間資料:</h3> -->
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reshours/ResHoursServlet" name="form1" enctype="multipart/form-data">
<div class="container">
<!--    <div class="row"> -->
<!--     <div class="col-2">營業時間編號:</div> -->
<%--     <div class="col-4"><%=resHoursVO.getReshourid()%></div> --%>
<!--     </div><br> -->
    <div class="row">
    <div class="col-3">餐廳編號:</div>
    <div class="col-4"><%=resHoursVO.getResid()%></div>
    </div><br>
  <div class="row">
  <div class="col-3">開始營業時間:</div>
    <div class="col-4"><fmt:formatDate value="${resHoursVO.opening}" pattern="HH:mm"/></div>
    </div><br>
    <div class="row">
  <div class="col-3">結束營業時間:</div>
    <div class="col-4"><fmt:formatDate value="${resHoursVO.closing}" pattern="HH:mm"/></div>
    </div>
  </div>
</FORM>

<%-- <jsp:useBean id="reshoursSvc" scope="page" class="com.reshours.model.ResHoursService" /> --%>
<%-- <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reshours/ResHoursServlet" style="margin-bottom: 0px;"> --%>
<!--   <div class="container"> -->
<!--   <div class="col-3"> -->
<!--   <div class="row"> -->
<!--   	<input type="submit" value="資料修改"> -->
<%--     <input type="hidden" name="reshourid"  value="${resHoursVO.reshourid}"> --%>
<!-- 	<input type="hidden" name="action"	value="getOne_For_Update"> -->
<!--   </div> -->
<!--   </div> -->
<!--   </div> -->
<!-- </FORM> -->
    </div>
    
  <div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
  
</body>

</html>