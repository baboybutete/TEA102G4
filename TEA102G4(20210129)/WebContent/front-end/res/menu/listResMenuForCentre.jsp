<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.menu.model.*"%>
<%@ include file="getBaseStirng.file"%>
<%@	page import="com.resinfo.model.ResInfoVO"%>

<%
// MenuService menuSvc = new MenuService();
// String str = request.getParameter("resid");
// List<MenuVO> list = menuSvc.getOneResMenu(str);
// pageContext.setAttribute("list",list);

	ResInfoVO resinfoVO = (ResInfoVO)session.getAttribute("resInfoVO");
	MenuService menuSvc = new MenuService();
	List<MenuVO> list = menuSvc.getOneResMenu(resinfoVO.getResid());
	pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>餐聽菜單資料</title>
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
  	position: relative;
    margin-top: -574px;
    margin-left: 201px;
  }
  footer{
	position: relative;
    margin-top: 400px;
}
  div.addmeal{
    margin-left: 202px;
}
  li{
 	list-style-type: none;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
form{
    margin-top: 15px;
}
</style>
</head>
<body>
<div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
    
    
 <div class="info">
<!--  <h3>餐聽菜單資料:</h3> -->
<jsp:useBean id="menuService" class="com.menu.model.MenuService" />
	<table>
		<tr>
<!-- 			<th>餐點編號</th> -->
<!-- 			<th>餐廳編號</th> -->
			<th>分類</th>
			<th>餐點名稱</th>
			<th>價位</th>
			<th>圖片</th>
			<th>狀態</th>
			<th>加入日期</th>
			<th>刪除</th>
			<th>修改</th>
		</tr>
		<c:forEach var="menuVO" items="${list}" >
				
		<%  
			MenuVO menuVO = (MenuVO) pageContext.getAttribute("menuVO");
			if(menuVO != null){
				byte[] imgbyte = menuVO.getMealimg();
				String imgString =  getBaseString(imgbyte);
				pageContext.setAttribute("imgString",imgString);
			}
		%>
		<tr>
<%-- 				<td>${menuVO.mealid}</td> --%>
<%-- 				<td>${menuVO.resid}</td> --%>
				<td>${menuVO.classname}</td>
				<td>${menuVO.mealname}</td>
				<td>${menuVO.mealprice}元</td>
				<td><img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'" width="100px" height="50px"></td>
				<td>${menuVO.mealstatus}</td>
				<td><fmt:formatDate value="${menuVO.adddate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<form action="<%=request.getContextPath() + "/menu/menu.do"%>" METHOD="POST">
						<input type="submit" value="刪除">
						<input type="hidden" name="action" value="delete">
						<input type="hidden" name="mealid" value="${menuVO.mealid}">
					</form>
				</td>
				<td>
					<form action="<%=request.getContextPath() + "/menu/menu.do"%>" METHOD="POST">
						<input type="submit" value="修改">
						<input type="hidden" name="action" value="getOne_For_Update">
						<input type="hidden" name="mealid" value="${menuVO.mealid}">
					</form>
				</td>
		</tr>
		</c:forEach>
	</table>
	</div>
	
	<div class="addmeal">
	<%MenuVO menuVO = (MenuVO) pageContext.getAttribute("menuVO"); %>
					<form action="<%=request.getContextPath() + "/menu/menu.do"%>" METHOD="POST">
						<input type="submit" value="新增餐點">
						<input type="hidden" name="action" value="getOne_For_Insert">
						<input type="hidden" name="resid" value="${menuVO.resid}">
					</form>
	</div>
	
	
<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>
<script src="<%=request.getContextPath()+"/js/er.twzipcode.data.js"%>"></script>
  <script src="<%=request.getContextPath()+"/js/er.twzipcode.min.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>
</html>