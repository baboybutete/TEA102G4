<%@page import="com.menu.model.*"%>
<%@page import="com.resinfo.model.*"%>
<%@page import="java.util.Base64"%>
<%@ include file="getBaseStirng.file"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@	page import="com.resinfo.model.ResInfoVO"%>

<html>
<head>
<meta charset="UTF-8">
<title>新增餐點</title>

<style>
form{
	margin-top: -450px;
    margin-left: 150px;
}
div.container div.row{
    margin-left: 100px;
}
div.container{
    position: relative;
    margin-top: 30px;
}
div.container div.col-4{
	font-size: 25px;
  	text-align: justify;
}
input[type=text]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
input[type=password]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
select{
	padding:5px;
  	border-radius: 6px;
}
input:focus { 
    border-color: #719ECE;
    box-shadow: 0 0 20px #719ECE;
}
select:focus { 
    border-color: #719ECE;
    box-shadow: 0 0 20px #719ECE;
}
#error{
    font-size: 20px;
    color: red;
    margin-left: 240px;
    position: absolute;
    margin-top: 100px;
}
li{
	list-style-type:none;
}
</style>
</head>
<body>
 <div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
    
    
	<div id="error">
	<c:if test="${not empty errorMsg}">
		<ul>
			<c:forEach var="msg" items="${errorMsg}">
				<li>${msg}</li>
			</c:forEach>
		</ul>
	</c:if>
	</div>
	
	<%			
				ResInfoVO resinfoVO = (ResInfoVO)session.getAttribute("resInfoVO");
				MenuVO menuVO = (MenuVO) request.getAttribute("menuVO");
				if (menuVO != null) {
					byte[] imgbyte = menuVO.getMealimg();
					String imgString = getBaseString(imgbyte);
					pageContext.setAttribute("imgString", imgString);
				}
	%>

	<jsp:useBean id="menuServlet" class="com.menu.model.MenuService"></jsp:useBean>
	<jsp:useBean id="resInfoSercice" class="com.resinfo.model.ResInfoService"></jsp:useBean>
	
	
	
	<form action="<%=request.getContextPath() + "/menu/menu.do"%>" enctype="multipart/form-data" method="POST">
		
		<div class="container">
  <div class="row">
  <div class="col-4">分類:</div>
    <div class="col-6">
      <input type="TEXT" name="classname" size="45px" placeholder="請輸入餐點種類"
			 value="<%= (menuVO==null)? "" : menuVO.getClassname()%>" />
    </div>
  </div><br>
   <div class="row">
  <div class="col-4">餐點名稱:</div>
    <div class="col-6">
      <input type="TEXT" name="mealname" size="45px" placeholder="請輸入餐點名稱"
			 value="<%= (menuVO==null)? "" : menuVO.getMealname()%>" />
    </div>
  </div><br>
  <div class="row">
  <div class="col-4">價位:</div>
    <div class="col-6">
      <input type="TEXT" name="mealprice" size="45px" placeholder="請輸入價位"
			 value="<%= (menuVO==null)? "" : menuVO.getMealprice()%>" />
    </div>
  </div><br>
  <div class="row">
  <div class="col-4">圖片:</div>
    <div class="col-6">
      <img id="pic" src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'" width="200px" height="150px">
 		<input id="p_file" type="file" name="mealimg" >
    </div>
  </div><br>
  <div class="row">
  
  <div class="col-4">狀態:</div>
    <div class="col-6">
    	<select name="mealstatus">
			<option value="<%= (menuVO==null)? "下架" : menuVO.getMealstatus() %>">下架</option>				
			<option value="<%= (menuVO==null)? "上架" : menuVO.getMealstatus() %>">上架</option>
	    </select>
	    
    </div>
  </div><br>
  <div class="col-6">
  <div class="row">
	<input type="hidden" name="resid" value="<%=resinfoVO.getResid()%>">
	<input type="hidden" name="action" value="insert">
	<input type="submit" value="送出新增">
   </div>
  </div>
</div>
</FORM>
		
<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
    
    
	<script type="text/javascript"
		src="<%=request.getContextPath() + "/js/jquery-3.5.1.min.js"%>"
		charset="UTF-8"></script>
	<script>
		$("#p_file").on("change", function() {
			let menuImg = $("#pic");

			let reader = new FileReader();
			file_picture = this.files[0];

			reader.readAsDataURL(this.files[0]);
			//預覽
			reader.addEventListener("load", function() {
				menuImg.attr("src", reader.result);
			})
		})
	</script>
</body>
</html>