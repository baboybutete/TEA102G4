<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.custinfo.model.*"%>
<%@ page import="java.util.*"%>
<%@ include file="getBaseStirng.file"%>
<!DOCTYPE html>
<%
	CustinfoVO custinfoVO1 = (CustinfoVO) session.getAttribute("custinfoVO"); //CustinfoServlet.java(Concroller), 存入req的empVO物件
 	
     if (custinfoVO1 != null) {
      byte[] imgbyte = custinfoVO1.getCustpicture();
      
      String imgString = getBaseString(imgbyte);
      pageContext.setAttribute("imgString", imgString);
     }
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>login</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cust.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
</head>
<body>
	 <%@ include file="/front-end/cust/custindex_header.jsp" %>
    
          <!-- header -->
	<div class="banner">

		<div class="subnav" style="display: inline-block;">
			<ul>
				<li><a
					href="<%=request.getContextPath() + "/front-end/cust/custinfo/listOneCustAccount.jsp"%>">會員資料

				</a></li>
				<li><a
					href="<%=request.getContextPath() + "/front-end/cust/custinfo/update_custinfo_input.jsp"%>">修改資料
				</a></li>
				<li><a
					href="<%=request.getContextPath() + "/front-end/cust/favorites/ListAllFavorites.jsp"%>">我的收藏
				</a></li>
<!-- 				<li><a href="#"> 預約紀錄 </a></li> -->
				<li><a
					href="<%=request.getContextPath() + "/front-end/cust/orderinfo_orderdet/ListAllOrder.jsp"%>">我的訂單</a></li>
<!-- 				<li><a href="#"> 優惠卷 </a></li> -->
				<!-- <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li> -->
			</ul>
		</div>
		<div class="box" style="weight: 10px; height: 425 margin-left: 10px;">
			<!-- 			<div class="box" -->
			<!-- 				style="weight: 100px; height: 425; border: 3px black dashed"> -->
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/custinfo/custinfo.do"
				name="form1" enctype="multipart/form-data">
				<table>
					<tr>
						<td>會員編號:<font color=red><b>*</b></font></td>
						<td><%=custinfoVO.getCustid()%></td>
					</tr>

					<tr>
						<td>會員姓名:</td>
						<td><input type="TEXT" name="custname" size="45"
							value="<%=(custinfoVO == null) ? "例如:王六" : custinfoVO.getCustname()%>" /></td>
					</tr>
					<tr>
						<td><input type="hidden" name="custstatus" size="45"
							value="<%=(custinfoVO == null) ? "" : custinfoVO.getCuststatus()%>" /></td>
					</tr>
					<tr>
						<td>會員帳號:</td>
						<td><input type="TEXT" name="custaccount" size="45"
							value="<%=(custinfoVO == null) ? "如EMAIL" : custinfoVO.getCustaccount()%>" readonly /></td>
					</tr>
					<tr>
						<td>會員密碼:</td>
						<td><input type="password" name="custpassword" size="45"
							value="<%=(custinfoVO == null) ? "" : custinfoVO.getCustpassword()%>" /></td>
					</tr>
					<tr>
						<td>確認密碼:</td>
						<td><input type="password" name="custpassword2" size="45"
							value="<%=(custinfoVO == null) ? "" : custinfoVO.getCustpassword()%>" /></td>
					</tr>

					<tr>
						<td>員工電話:</td>
						<td><input type="TEXT" name="custtel" size="45"
							value="<%=(custinfoVO == null) ? "0912345678" : custinfoVO.getCusttel()%>" /></td>
					</tr>
					<tr>
						<td>圖片:</td>
						<td><img id="pic" src="${imgString}" width="200px" height="100px">
							<input type="file" name="custpicture" id="p_file" accept="image/*" /></td>
					</tr>

				</table>
				<br> <input type="hidden" name="action" value="FrontUpdate">
				<input type="hidden" name="custid"
					value="<%=custinfoVO.getCustid()%>"> <input type="submit"
					value="送出修改">
			</FORM>

		</div>
	</div>
	<!-- 	</div> -->


	<!-- footer  -->
<%-- 	<%@ include file="/front-end/cust/custindex_footer.jsp" %> --%>
	

</body>
<script>
		$("#p_file").on("change", function() {
			let custpictureImg = $("#pic");
			let reader = new FileReader();
			file_picture = this.files[0];
			reader.readAsDataURL(this.files[0]);
			reader.addEventListener("load", function() {
				custpictureImg.attr("src", reader.result);
			})
		})
	</script>

</html>