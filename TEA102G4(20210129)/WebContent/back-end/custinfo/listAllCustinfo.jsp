<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.custinfo.model.*"%>
<%@ include file="getBaseStirng.file"%>
<%
	CustinfoService custinfoSvc = new CustinfoService();
	List<CustinfoVO> list = custinfoSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>座位呢 - listAllCustinfo.jsp</title>
    <link rel="stylesheet" href="<%=request.getContextPath() + "/css/list_all_custinfo.css"%>">
  </head>
  <body>
	<jsp:useBean id="addetailService" class="com.addetail.model.AddetailService"></jsp:useBean>
	<div>
	  <!-- headerlogo -->
	  <%@ include file="/back-end/back_header.jsp" %>

	  <!-- 顯示資料的區域 -->
	  <div class="custinfo">
		<div class="custinfo_title">
		  <a>消費者資訊管理</a>
		  <img src="<%=request.getContextPath()%>/images/right-arrow.png">
		  <a>查詢消費者</a>
		</div>
		<div class="input_adid">
		  <FORM class="input_area" METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/custinfo/custinfo.do" >
		    <b>輸入消費者編號 (如C00001):</b>
		    <input type="text" name="custid">
		    <input type="hidden" name="action" value="getOne_For_Display">
		    <input type="submit" value="送出">
		    <div class="errormsgs" style="color:red">${errorMsgs}</div>
    	  </FORM>
		</div>
		<table>
		  <tr>
			<th>顧客會員編號</th>
			<th>會員姓名</th>
			<th>會員電話</th>
			<th>會員帳號</th>
			<th>會員密碼</th>
<!-- 			<th>會員狀態</th> -->
			<th>註冊日期</th>
			<th>會員照片</th>
<!-- 			<th>修改狀態</th> -->
		  </tr>	
			<%@ include file="page1.file"%> 
 			<c:forEach var="custinfoVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"> 
	 		<% 
// 				String photoString = null;
// 					try {
// 						CustinfoVO custinfoVO = (CustinfoVO) pageContext.getAttribute("custinfoVO");
// 						Base64.Encoder encoder = Base64.getEncoder();
// 						photoString = encoder.encodeToString(custinfoVO.getCustpicture());
// 					} catch (Exception e) {
// 					  e.printStackTrace();
// 					}
				CustinfoVO custinfoVO = (CustinfoVO) pageContext.getAttribute("custinfoVO");
					if (custinfoVO != null) {
						byte[] imgbyte = custinfoVO.getCustpicture();
						
						String imgString = getBaseString(imgbyte);
						pageContext.setAttribute("imgString", imgString);
					}
		    %>
 		  <tr> 
			<td>${custinfoVO.custid}</td>
 			<td>${custinfoVO.custname}</td>
 			<td>${custinfoVO.custtel}</td>
 			<td>${custinfoVO.custaccount}</td>
 			<td>${custinfoVO.custpassword}</td>
<%-- 			<td>${custinfoVO.custstatus}</td> --%>
 			<td><fmt:formatDate value="${custinfoVO.registrationtime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
 			<td><div class="tableImg"><img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'"></div></td>
<!--  			<td> -->
			  <FORM class="forstatus" METHOD="post" ACTION="<%=request.getContextPath()%>/custinfo/custinfo.do" style="margin-bottom: 0px;">
			  		<c:choose>
			  			<c:when test="${custinfoVO.custstatus.equals('使用中')}">
			  				<input class="status" name="custstatus" type="hidden" value="停權">
			  			</c:when>
			  			<c:otherwise>
			  				<input class="status" name="custstatus" type="hidden" value="使用中">
			  			</c:otherwise>
			  		</c:choose>
			     	<input type="hidden" name="custid"  value="${custinfoVO.custid}">
			     	<input type="hidden" name="role"  value="back-end">
			     	<input type="hidden" name="action"	value="update_By_BackEnd">
			     	</FORM>
<!-- 			</td> -->
 		  </tr>
 		 </c:forEach>
 	   </table>
	   <%@ include file="page2.file"%>
	   </div>
	</div>
	<!-- footer -->
    <%@ include file="/back-end/back_footer.jsp" %>
</body>
	<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>
	<script>
		$("FORM.forstatus").on("submit",function(event){
			var ok = confirm("確定要更改嗎");
			if(!ok){
				event.preventDefault();
			}
		})
		
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
</html>