<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>座位呢-餐廳頁面</title>
<link rel="stylesheet" href="cust.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/login-html/css/login.css" />
</head>

<body>
	<header class=head>
		<a class="logo" href="<%=request.getContextPath()%>/index_plat.jsp"><img
			src="<%=request.getContextPath()%>/images/Asset 19.png" alt=""></a>

	</header>


	<div class="logintext">
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<h4>使用者帳號登入</h4>
		<form method="post"
			action="<%=request.getContextPath()%>/custloginhandler/custloginhandler.do">
			<div>
				帳號：<input type="text" name="account" value="">
			</div>
			<div>
				密碼：<input type="password" name="password" value="">
			</div>

			<div class="denru">
				<input type=submit id="btn_login" value="登入" />
			</div>
			<div class="zhuche">
				<a
					href="<%=request.getContextPath()%>/login-html/login/registered_select.jsp">註冊</a>
			</div>
		</form>

	</div>


	<!-- footer  -->
	<div class="footer">
		<div class="container">

			<ul>
				<li><a href="#"><span class="label">關於我們</span></a></li>
				<li><a href="#"><span class="label">平台緣起</span></a></li>
				<li><a href="#"><span class="label">操作流程</span></a></li>
			</ul>

		</div>
		<!-- <div class="num4"> <p>
                © 2020年Tibame 第四組座位呢Inc.保留所有權力。--註冊第6666666666666666666號</p>
            </div> -->


	</div>










</body>


</html>