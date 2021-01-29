<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
      <link href="<%=request.getContextPath()+"/css/bootstrap.min.css"%>" rel="stylesheet">
	  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/res_member_centre.css">
      <div class="header w">
        <div class="logo">
          <a href="<%=request.getContextPath()%>/index_plat.jsp"><img src="<%=request.getContextPath()%>/front-end/res/resinfo/images/logo.png" width="200" height="100" alt="">
		</a>   
	<div class="membercentre"><a href='<%=request.getContextPath()%>/front-end/res/res_member_centre.jsp'>會員中心</a></div>   
    <div class="logout"><a href='<%=request.getContextPath()%>/logoutservlet/logoutservlet.do'>登出</a></div>
	<div class="reshome"><a href='<%=request.getContextPath()%>/index_plat.jsp'>首頁</a></div>   
      </div>     
        </div>
      <div class="subnav">
          <ul>
              <h3>基本資訊</h3>
              <li><a href="<%=request.getContextPath()%>/front-end/res/resinfo/listOneResInfoForCentre.jsp">帳號資訊</a></li>
              <li><a href="<%=request.getContextPath()%>/front-end/res/menu/listResMenuForCentre.jsp">菜單資訊</a></li>
              <li><a href="<%=request.getContextPath()%>/front-end/res/carousel/listResCarouselForCentre.jsp">輪播圖資訊</a></li>
              <li><a href="<%=request.getContextPath()%>/front-end/res/reshours/listAllResForCentre.jsp">營業時間資訊</a></li>
              <h3>訂單資訊</h3>
              <li><a href="<%=request.getContextPath()%>/front-end/res/orderdetail/listResOrderInfoForCentre.jsp">消費者訂單</a></li>
              <h3>後台管理</h3>
              <li><a href="<%=request.getContextPath()%>/front-end/res/corrdinate/tabledesign.jsp">座位圖配置</a></li>
              <li><a href="<%=request.getContextPath()%>/front-end/res/corrdinate/front_res_seatstate.jsp">即時座位更新</a></li>
              <li><a href="<%=request.getContextPath()%>/front-end/res/waiting/waitingSystem.jsp">候位系統</a></li>
<!--               <h3>推播管理</h3> -->
<!--               <li><a href="#">定位通知</a></li> -->
              <li><a href="<%=request.getContextPath()%>/front-end/res/addetail/listResAddetailForCentre.jsp">廣告管理</a></li>
<!--               <li><a href="#">優惠券</a></li> -->
          </ul>
      </div>
