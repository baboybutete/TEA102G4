<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.menu.model.*"%>
<%@ page import="java.util.Base64"%>

<%@ include file="getBaseStirng.file"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>餐廳菜單修改</title>
<style>
 form{
 	position: relative;
    margin-top: -480px;
    margin-left: 202px;
}
div.container div.row{
    margin-left: 20px;
}
div.container{
	margin-top: 50px;
	width: 50%;
    float: left;
}
div.container div.col-3{
	font-size: 20px;
  	text-align: justify;
}
div.container div.col-4{
	font-size: 20px;
  	text-align: justify;
}
div.container div.col-5{
	font-size: 25px;
  	text-align: justify;
}

input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
select{
	padding:5px;
	float: left;
}
input:focus { 
    border-color: #719ECE;
    box-shadow: 0 0 20px #719ECE;
}
select:focus { 
    border-color: #719ECE;
    box-shadow: 0 0 20px #719ECE;
}
footer{
	position: relative;
    margin-top: 100px;
}
#error{
    font-size: 20px;
    color: red;
    margin-left: 210px;
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
	<FORM METHOD="post" ACTION="<%=request.getContextPath() + "/menu/menu.do"%>" name="form1" enctype="multipart/form-data">
<%
				MenuVO menuVO = (MenuVO) request.getAttribute("menuVO");
				if (menuVO != null) {
					byte[] imgbyte = menuVO.getMealimg();
					String imgString = getBaseString(imgbyte);
					pageContext.setAttribute("imgString", imgString);
					if(imgString.equals("#")){
						pageContext.setAttribute("originalPic","hasNoOld");
					}else{
						pageContext.setAttribute("originalPic","hasOld");
					}
				}
			%>
	<div class="container">
   <div class="row">
    <div class="col-3">分類:</div>
    <div class="col-4">
    <input type="TEXT" name="classname"  value="<%=menuVO.getClassname()%>" />
	</div>
    </div><br>
    <div class="row">
    <div class="col-3">餐點名稱:</div>
    <div class="col-4">
    <input type="TEXT" name="mealname"  value="<%=menuVO.getMealname()%>" />
    </div>
  </div><br>
    <div class="row">
    <div class="col-3">價位:</div>
    <div class="col-4">
		<input type="TEXT" name="mealprice"  value="<%=menuVO.getMealprice()%>" />    
    </div>
  </div><br>
  <div class="row">
  <div class="col-3">圖片:</div>
    <div class="col-4">
      <img id="pic" src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'" width="100px" height="50px">
					<input id="p_file" type="file" name="mealimg">
    </div>
    </div><br>
    <div class="row">
  <div class="col-3">狀態:</div>
    <div class="col-4">
		<select name="mealstatus">
			<option value="上架" ${menuVO.mealstatus.equals("上架")?'selected':'' }>上架</option>
			<option value="下架" ${menuVO.mealstatus.equals("下架")?'selected':'' }>下架</option>
		</select>
	</div>
</div><br>
<div class="col-6">
  <div class="row">
  <input type="hidden" name="action" value="update"> 
  <input type="hidden" name="mealid" value="<%=menuVO.getMealid()%>"> 
  <input type="hidden" name="resid" value="<%=menuVO.getResid()%>">
  <input type="submit" value="送出修改">
</div>
</div>
</div>
</FORM>

	<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
	
	
	<script type="text/javascript" src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>" charset="UTF-8"></script>
	<script>
		
		$("#p_file").on("change",function(){
			let menuImg = $("#pic");
			
			 let reader = new FileReader();
	            file_picture = this.files[0];

	            reader.readAsDataURL(this.files[0]);
	            //預覽
	            reader.addEventListener("load", function () {
	            	menuImg.attr("src",reader.result);
	            	$("#originalPic").remove();
	            })
		})
	</script>
</body>
</html>