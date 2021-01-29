<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>餐廳會員中心</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/res_member_centre.css">
</head>
<body>
      <!-- header -->
<!-- <div class=""> -->
    <%@ include file="/front-end/res/res_member_centre_header.jsp" %>
    
      
     
    <!-- header -->


    <div class="resinfo"><%@ include file="resinfo/listOneResInfo2.jsp"%></div>
    
    
    <!-- footer -->
<%--      <%@ include file="/front-end/res/res_member_centre_footer.jsp" %> --%>
    
</body>
</html>