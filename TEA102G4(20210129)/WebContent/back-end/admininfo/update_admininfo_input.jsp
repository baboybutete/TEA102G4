<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.admininfo.model.*"%>

<%
	AdmininfoVO admininfoVO = (AdmininfoVO) request.getAttribute("admininfoVO");
%>


<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>座位呢 - update_admininfo_input.jsp</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/update_admininfo_input.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.css" />
  </head>
  <body>
    <div>
       <!-- headerlogo -->
      <%@ include file="/back-end/back_header.jsp" %>

      <!-- 顯示資料的區域 -->
      <div class="custinfo">
        <div class="custinfo_title">
          <a>後台管理員管理</a>
            <img src="<%=request.getContextPath()%>/images/right-arrow.png">
          <a>修改管理員資料</a>
        </div>
        <div class="input_adid">
          <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/admininfo/controller/admininfoServlet.do" name="form1">
			<table>
				<tr>
					<td>管理員編號:</td>
					<td><%=admininfoVO.getManid()%></td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td>管理員帳號:</td> -->
<!-- 					<td> -->
					<input type="hidden" name="manname" size="30" value="<%=admininfoVO.getManname()%>" readonly/>
<%-- 					<div class="" style="color:red">${errorMsgs.mannamenull}</div> --%>
<%-- 					<div class="" style="color:red">${errorMsgs.mannameerr}</div> --%>
<%-- 					<div class="" style="color:red">${errorMsgs.mannameexist}</div> --%>
<!-- 					</td> -->
<!-- 				</tr> -->
				<tr>
					<td>管理員帳號:</td>
					<td><input type="TEXT" name="manmail" size="30"	value="<%=admininfoVO.getManmail()%>" readonly/>
					<div class="" style="color:red">${errorMsgs.manmailnull}</div>
					<div class="" style="color:red">${errorMsgs.manmailerr}</div>
					<div class="" style="color:red">${errorMsgs.manmailexist}</div>
					</td>
				</tr>
				<tr>
					<td>管理員密碼:</td>
					<td><input type="TEXT" name="manpassword" size="30" value="<%=admininfoVO.getManpassword()%>"/>
					<div class="" style="color:red">${errorMsgs.manpwdnull}</div>
					<div class="" style="color:red">${errorMsgs.mannpwderr}</div>
					</td>
				</tr>
				<tr>
					<td>管理員姓名:</td>
					<td><input type="TEXT" name="manrealname" size="30"	value="<%=admininfoVO.getManrealname()%>" />
					<div class="" style="color:red">${errorMsgs.realnamenull}</div>
					<div class="" style="color:red">${errorMsgs.realnameerr}</div>
					</td>
				</tr>
				
				<tr>
					<td>管理員電話:</td>
					<td><input type="TEXT" name="mantel" size="30"	value="<%=admininfoVO.getMantel()%>" />
					<div class="" style="color:red">${errorMsgs.mantelnull}</div>
					<div class="" style="color:red">${errorMsgs.mantelerr}</div>
					</td>
				</tr>
				
<!-- 				<tr> -->
<!-- 					<td>管理員狀態:</td> -->
<!-- 					<td><select size="1" name="manstatus"> -->
<%-- 						  <option value="使用中" ${(admininfoVO.manstatus).equals("使用中")? 'selected' : ''}>使用中 --%>
<%-- 						  <option value="已停權" ${(admininfoVO.manstatus).equals("已停權")? 'selected' : ''}>已停權 --%>
<!-- 					</select> -->
<!-- 					</td> -->
<!-- 				</tr> -->
			
				<jsp:useBean id="backRoleSvc" scope="page" class="com.backrolemanager.model.BackRoleService" />
<!-- 				<tr> -->
<!-- 					<td>管理員權限:</td> -->
<!-- 					<td><select size="1" name="manpurview"> -->
<%-- 						<c:forEach var="backRoleVO" items="${backRoleSvc.all}"> --%>
<%-- 							<option value="${backRoleVO.manpurview}" ${(admininfoVO.manpurview==backRoleVO.manpurview)? 'selected':'' } >${backRoleVO.purviewcontent} --%>
<%-- 						</c:forEach> --%>
<!-- 					</select></td> -->
<!-- 				</tr> -->
			
			</table>
			<br>
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="manid" value="<%=admininfoVO.getManid()%>">
			<input type="hidden" name="manstatus" value="使用中">
			<input type="hidden" name="manpurview" value="1">
			<input type="submit" value="送出修改">
			</FORM>
        </div>
      </div>
    </div>
    <!-- footer -->
    <%@ include file="/back-end/back_footer.jsp" %>
	</body>
</html>