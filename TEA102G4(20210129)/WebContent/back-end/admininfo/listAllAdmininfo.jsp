<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.admininfo.model.*"%>

<% 
	AdmininfoService admininfoSvc = new AdmininfoService();
	List<AdmininfoVO> list = admininfoSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>座位呢 - listAllAdmininfo.jsp</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/list_all_admin.css">
  </head>
  <body>
  	<jsp:useBean id="admininfoService" class="com.admininfo.model.AdmininfoService"></jsp:useBean>
    <div class="">
     <%@ include file="/back-end/back_header.jsp" %>

      <!-- 顯示資料的區域 -->
      <div class="custinfo">
        <div class="custinfo_title">
          <a>後台管理員管理</a>
          <img src="<%=request.getContextPath()%>/images/right-arrow.png">
          <a>查詢後台管理員</a>
        </div>
        <div class="input_adid">
          <FORM class="input_area" METHOD="post" ACTION="<%=request.getContextPath()%>/admininfo/controller/admininfoServlet.do">
            <b>輸入管理員編號 (如MAN00001):</b>
            <input type="text" name="manid">
            <input type="hidden" name="action" value="getOne_For_Display">
            <input type="submit" value="送出">
            <div class="errormsgs" style="color:red">${errorMsgs}</div>
          </FORM>
        </div>
        <table>
          <tr>
            <th>管理員編號</th>
			<th>管理員帳號</th>
			<th>管理員密碼</th>
<!-- 			<th>email</th> -->
			<th>管理員姓名</th>
			<th>管理員電話</th>
<!-- 			<th>管理員狀態</th> -->
<!-- 			<th>管理員權限</th> -->
			<th>修改</th>
          </tr>
          <%@ include file="page1.file"%>
			<c:forEach var="admininfoVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
          <tr>
            <td>${admininfoVO.manid}</td>
			<td>${admininfoVO.manmail}</td>
<%-- 			<td>${admininfoVO.manname}</td> --%>
			<td>${admininfoVO.manpassword}</td>
			<td>${admininfoVO.manrealname}</td>
			<td>${admininfoVO.mantel}</td>
<%-- 			<td>${admininfoVO.manstatus}</td> --%>
<%-- 			<td>${admininfoVO.manpurview}</td> --%>
            <td>
               <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/admininfo/controller/admininfoServlet.do" style="margin-bottom: 0px;">
                  <input type="submit" value="修改">
                  <input type="hidden" name="manid"  value="${admininfoVO.manid}">
                  <input type="hidden" name="action" value="getOne_For_Update">
               </FORM>
            </td>
          </tr>
         </c:forEach>
       </table>
      </div>
    </div>
    <!-- footer -->
    <%@ include file="/back-end/back_footer.jsp" %>
  </body>
</html>