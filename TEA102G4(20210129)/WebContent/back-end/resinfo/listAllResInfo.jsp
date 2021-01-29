<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>

<%-- resinfo可以跟其他人共用 --%>
<%@ page import="com.resinfo.controller.*"%>
<%@ page import="com.resinfo.model.*"%>
<%@ include file="getBaseStirng.file"%>

<%	
	List<ResInfoVO> list = null;
	list = (List<ResInfoVO>)request.getAttribute("result");
	if (list == null){
		ResInfoService resinfoSvc = new ResInfoService();
	     list = resinfoSvc.getAll();
	    pageContext.setAttribute("list",list);
	} else {
		pageContext.setAttribute("list",list);
		 
	}
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<meta charset="utf-8">
<title>座位呢 - listAllResInfo.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() + "/css/listAllResInfo.css"%>">
</head>
<body>
	<jsp:useBean id="addetailService" class="com.addetail.model.AddetailService"></jsp:useBean>
	<jsp:useBean id="resInfoSvc" class="com.resinfo.model.ResInfoService"></jsp:useBean>
	<div>
		<%@ include file="/back-end/back_header.jsp" %>
		
		<!-- 顯示資料的區域 -->
		<div class="custinfo">
		  <div class="custinfo_title">
			<a>餐廳資訊管理</a>
			<img src="<%=request.getContextPath()%>/images/right-arrow.png">
			<a>查詢餐廳</a>
		  </div>
		  <div class="input_adid">
			<FORM class="form1" METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/resinfo/resinfo.do" >
	        	<b>輸入餐廳編號或名稱 (如R00001):</b>
	        	<input type="text" name="resid">
	        	<input type="hidden" name="action" value="Search_For_Display_From_Backend">
	        	<input type="submit" value="送出">
    		</FORM>
<%-- 	    	<FORM class="form2" METHOD="post" ACTION="<%=request.getRequestURI()%>"> --%>
<!-- 	        	<input type="hidden" name="action" value="Search_For_Display_From_Backend"> -->
<!-- 	        	<input type="submit" value="顯示全部"> -->
<!-- 	    	</FORM> -->
	    	<div class="errormsgs" style="color:red">${errorMsgs}</div>
		  </div>
		<table class="data">
			<tr>
				<th>餐廳編號</th>
				<th>餐廳名稱</th>
				<th>餐廳地址</th>
				<th>餐廳圖片</th>
				<th>付款資訊</th>
				<th>餐廳設定的訂位通知</th>
				<th>餐廳信箱</th>
				<th>目前候位號碼 </th>
				<th>聯絡人姓名</th>
				<th>聯絡人電話</th>
				<th>加入日期</th>
				<th>會員狀態</th>
				<th>修改狀態</th>
			</tr>
			<%@ include file="page1.file" %> 		
			<c:forEach var="resInfoVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<%  
					ResInfoVO resInfoVO = (ResInfoVO)pageContext.getAttribute("resInfoVO");
					if(resInfoVO != null){
						byte[] imgbyte = resInfoVO.getResimg();
						
						String imgString =  getBaseString(imgbyte);
						pageContext.setAttribute("imgString",imgString);
					}
				%>
			<tr>
				<td>${resInfoVO.resid}</td>
				<td>${resInfoVO.resname}</td>
				<td>${resInfoVO.resaddid}</td>
				<td><div class="tableImg"><img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'" width="50px" height="50px"></div></td>
				<td>${resInfoVO.payinfo}</td>
				<td>${resInfoVO.notifcont}</td>
				<td>${resInfoVO.resemail}</td>
 				<td>${resInfoVO.waitdistance}</td>
				<td>${resInfoVO.contact}</td>
				<td>${resInfoVO.contactphon}</td>
				<td><fmt:formatDate value="${resInfoVO.adddate}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${resInfoVO.status}</td>
				<td>
			  		<FORM class="forstatus" METHOD="post" ACTION="<%=request.getContextPath()%>/resinfo/ResInfoServlet" style="margin-bottom: 0px;">
			  		<c:choose>
			  			<c:when test="${resInfoVO.status.equals('使用')}">
			  				<input class="status" name="status" type="submit" value="停權">
			  			</c:when>
			  			<c:otherwise>
			  				<input class="status" name="status" type="submit" value="使用">
			  			</c:otherwise>
			  		</c:choose>
			     	<input type="hidden" name="resid"  value="${resInfoVO.resid}">
			     	<input type="hidden" name="role"  value="back-end">
			     	<input type="hidden" name="action"	value="update_By_BackEnd">
			     	</FORM>
				</td>
			</tr>
			</c:forEach>
		</table>
		<%@ include file="page2.file"%>
	  </div>
	</div>
	
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