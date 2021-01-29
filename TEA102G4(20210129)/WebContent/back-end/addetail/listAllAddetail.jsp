<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@	page import="java.util.Base64"%>
<%@ page import="com.addetail.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="getBaseStirng.file"%>


<%
	AddetailService addetailSvc = new AddetailService();
	List<AddetailVO> list = addetailSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<meta charset="utf-8">
<title>座位呢 - listAllAddetail.jsp</title>
 <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() + "/css/list_all_addetail.css"%>">
</head>
<body>
	<jsp:useBean id="addetailService" class="com.addetail.model.AddetailService"></jsp:useBean>
	<jsp:useBean id="resInfoSvc" class="com.resinfo.model.ResInfoService"></jsp:useBean>
	<div>
		<!-- headerlogo -->
		<%@ include file="/back-end/back_header.jsp" %>

		<!-- 顯示資料的區域 -->
		<div class="custinfo">
		  <div class="custinfo_title">
			<a>餐廳資訊管理</a>
			<img src="<%=request.getContextPath()%>/images/right-arrow.png">
			<a>查詢餐廳廣告</a>
		  </div>
		  <div class="input_adid">
			<FORM class="input_area" METHOD="post" ACTION="<%=request.getContextPath()%>/addetail/controller/addetailServlet.do">
				<b>輸入廣告編號 (例:AD00001):</b>
				<input type="text" name="adid">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
				<div class="errormsgs" style="color:red">${errorMsgs}</div>
			</FORM>
		  </div>
		<table>
			<tr>
				<th>廣告編號</th>
				<th>標題</th>
				<th>內容</th>
				<th>圖片</th>
<!-- 				<th>類型</th> -->
				<th>審核狀態</th>
				<th>申請日期</th>
				<th>上架日期</th>
				<th>下架日期</th>
				<th>餐廳編號</th>
				<th>修改</th>
			</tr>
			<%@ include file="page1.file"%>
			<c:forEach var="addetailVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<%
					AddetailVO addetailVO = (AddetailVO) pageContext.getAttribute("addetailVO");
						if (addetailVO != null) {
							byte[] imgbyte = addetailVO.getAdimg();

							String imgString = getBaseString(imgbyte);
							pageContext.setAttribute("imgString", imgString);
						}
				%>
				<tr>
					<td>${addetailVO.adid}</td>
					<td>${addetailVO.adtitle}</td>
					<td>${addetailVO.adcontent}</td>
					<td><div class="tableImg"><img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'"></div></td>
<%-- 					<td>${addetailVO.adtype}</td> --%>
					<td>${addetailVO.reviewstatus}</td>
					<td><fmt:formatDate value="${addetailVO.addate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${addetailVO.onshelftime}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td><fmt:formatDate value="${addetailVO.offshelftime}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td>${resInfoSvc.getOneResInfo(addetailVO.resid).resname}</td>
					<td>
							<FORM class ="forstatus" METHOD="post" ACTION="<%=request.getContextPath()%>/addetail/controller/addetailServlet.do" style="margin-bottom: 0px;">
							<c:choose>
								<c:when test="${addetailVO.reviewstatus.equals('審核通過')}">
									<input class="status" name="reviewstatus" type="submit" value="審核中">
								</c:when>								
								<c:otherwise>
								<input class="status" name="reviewstatus" type="submit" value="審核通過">
							</c:otherwise>
							</c:choose>
							<input type="hidden" name="adid" value="${addetailVO.adid}">
							<input type="hidden" name="role" value="back-end">
							<input type="hidden" name="action" value="update_By_BackEnd">
							</FORM>
					</td>
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
// 				event.preventDefault();
// 				swal({
// 					  title: "修改?",
// 					  text: "確認",
// 					  icon: "warning",
// 					  buttons: true,
// 					  dangerMode: true,
// 					})
// 					.then((willDelete) => {
// 					  if (willDelete) {
// 					    swal("Poof! Your imaginary file has been deleted!", {
// 					      icon: "success",
// 					    });
// 					  } else {
// 					    swal("Your imaginary file is safe!");
// 					  }
// 					});
				
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