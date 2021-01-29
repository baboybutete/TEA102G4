<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.resinfo.controller.*"%>
<%@ page import="com.resinfo.model.*"%>
<%@ include file="getBaseStirng.file"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%

// ResInfoVO resInfoVO = (ResInfoVO)session.getAttribute("resInfoVO");
// if(resInfoVO != null){
// 	byte[] imgbyte = resInfoVO.getResimg();
// 	String imgString =  getBaseString(imgbyte);
// 	pageContext.setAttribute("imgString",imgString);
// }
%>

<%
ResInfoVO resInfoVO = null;
if(request.getAttribute("resInfoVO") != null){
	//從修改後拿回來的資料
	resInfoVO =(ResInfoVO) request.getAttribute("resInfoVO");
	session.setAttribute("resInfoVO", resInfoVO);
	System.out.println("get請求");
}else{
	//登入後拿到的資料
	resInfoVO = (ResInfoVO)session.getAttribute("resInfoVO");
	System.out.println("session請求");
}

if(resInfoVO != null){
	byte[] imgbyte = resInfoVO.getResimg();
	String imgString =  getBaseString(imgbyte);
	pageContext.setAttribute("imgString",imgString);
}
%>

<style>
form{
	margin-top: 45px;
}
div.container div.row{
    margin-left: 20px;
}
div.container{
}
div.container div.col-2{
	font-size: 20px;
  	text-align: justify;
}
div.container div.col-1{
/* 	border: 1px solid lightgray; */
	font-size: 20px;
  	text-align: justify;
}
div.container div.col-4{
	font-size: 20px;
  	text-align: justify;
}
div.container div.col-5{
	font-size: 20px;
  	text-align: justify;
}
input[type=text]{
	width: 300px;
	padding:5px;
	font-size: 18px;
}
input[type=password]{
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
    margin-top: 50px;
}
</style>

<link href="<%=request.getContextPath()+"/css/bootstrap.min.css"%>" rel="stylesheet">
   
<!-- <div class="pageinfo"><h3>餐廳基本資訊:</h3></div> -->
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/resinfo/ResInfoServlet" name="form1" enctype="multipart/form-data">
<div class="container">
   <div class="row">
<!--     <div class="col-2">餐廳編號:</div> -->
<%--     <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getResid()%> disabled/></div> --%>
    <div class="col-2">餐廳名稱:</div>
    <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getResname()%> disabled/></div>
  </div><br>
  <div class="row">
  <div class="col-2">餐廳地址:</div>
    <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getResaddid()%> disabled/></div>
  <div class="col-2">餐廳圖片:</div>
    <div class="col-3">
      <img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'" width="200px" height="150px">
      
    </div>
  </div><br>
   <div class="row">
  <div class="col-2">無障礙空間:</div>
    <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getBarrierfree()%> disabled/></div>
  <div class="col-2">親子空間:</div>
    <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getParentchild()%> disabled/></div>
  </div><br>
  <div class="row">
  <div class="col-2">交通資訊:</div>
    <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getTraffic()%> disabled/></div>
  <div class="col-2">停車資訊:</div>
    <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getParking()%> disabled/></div>
  </div><br>
  <div class="row">
  <div class="col-2">付款資訊:</div>
    <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getPayinfo()%> disabled/></div>
<!--   <div class="col-2">訂位通知設定:</div> -->
<%--     <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getNotifcont()%> disabled/></div> --%>
  </div><br>
<!--   <div class="row"> -->
<!--   <div class="col-2">餐廳信箱:</div> -->
<%--     <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getResemail()%> disabled/></div> --%>
<!--   <div class="col-2">密碼:</div> -->
<%--     <div class="col-4"><input type="password" value=<%=resInfoVO.getRespassid()%> disabled/></div> --%>
<!--   </div><br> -->
<!--   <div class="row"> -->
<!--   <div class="col-2">目前候位號碼:</div> -->
<%--     <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getCurrentwaitingnum()%> disabled/></div> --%>
<!--   <div class="col-2">訂位通知時間差:</div> -->
<%--     <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getSubtimediff()%> disabled/></div> --%>
<!--   </div><br> -->
  <div class="row">
<!--   <div class="col-2">候位號碼差:</div> -->
<%--     <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getWaitdistance()%> disabled/></div> --%>
  <div class="col-2">聯絡人姓名:</div>
    <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getContact()%> disabled/></div>
  <div class="col-2">聯絡人電話:</div>
    <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getContactphon()%> disabled/></div>
  </div><br>
<!--   <div class="row"> -->
<!--   <div class="col-2">座位屬性+座標:</div> -->
<%--     <div class="col-4"><input type="TEXT" name="corrdinate" value=<%=resInfoVO.getCorrdinate()%> disabled/></div> --%>
<!--   </div><br> -->
<!--   <div class="row"> -->
<!--   <div class="col-2">加入日期:</div> -->
<%--     <div class="col-4"><fmt:formatDate value="${resInfoVO.adddate}" pattern="yyyy-MM-dd HH:mm"/></div> --%>
<!--   <div class="col-2">會員狀態:</div> -->
<%--     <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getStatus()%> disabled/></div> --%>
<!--   </div><br> -->
<!--   <div class="row"> -->
<!--   <div class="col-2">經度:</div> -->
<%--     <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getLat()%> disabled/></div> --%>
<!--   <div class="col-2">緯度:</div> -->
<%--     <div class="col-4"><input type="TEXT" value=<%=resInfoVO.getLng()%> disabled/></div> --%>
<!--   </div> -->
</div>
</FORM>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/resinfo/ResInfoServlet" style="margin-bottom: 0px;">
  <div class="container">
  <div class="col-2">
  <div class="row">
  	<input type="submit" value="資料修改">
    <input type="hidden" name="resid"  value="${resInfoVO.resid}">
	<input type="hidden" name="action"	value="getOne_For_Update">
  </div>
  </div>
  </div>
</FORM>

  <script src="<%=request.getContextPath()+"/js/er.twzipcode.data.js"%>"></script>
  <script src="<%=request.getContextPath()+"/js/er.twzipcode.min.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>
<script>
$("#p_file").on("change", function() {
	let resInfoImg = $("#pic");
	let reader = new FileReader();
	file_picture = this.files[0];
	reader.readAsDataURL(this.files[0]);
	reader.addEventListener("load", function() {
	resInfoImg.attr("src", reader.result);
$("#originalPic").remove();
	})
})
</script>
