<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.admininfo.model.*" %>

<% 
 	AdmininfoVO admininfoVO = (AdmininfoVO)request.getAttribute("admininfoVO");
	AdmininfoVO admininfoVO1 = (AdmininfoVO)request.getAttribute("manmail");
%>
<!DOCTYPE html>
<html>
  <head>
	<meta charset="UTF-8">
	<title>登入 - adminLogin.jsp</title>
	<link href='<%=request.getContextPath()%>/css/admin_login.css' rel="stylesheet">
  </head>
  <body>
    <div>
      <!-- header -->
  	  <div class="header w">
	    <div class="logo">
	      <img src="<%=request.getContextPath()%>/images/Asset 19.png">
	    </div>
	  </div>
  	  <!-- 登入視窗 -->
  	  <div>
  	    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/adminLoginHandler/adminLoginHandler.do" >
          <div id="login_frame">
	        <p><label class="label_input">信箱: </label><input class="text_field" type="text" name="manmail" placeholder="請輸入管理員信箱" value="<%=(admininfoVO==null)? "" : admininfoVO.getManmail()%>"/>
	        <p><label class="label_input">密碼: </label><input class="text_field" type="password" name="manpassword" placeholder="請輸入管理員密碼">
              <div id="login_control">
			    <input type="hidden" name="action" value="admin_login">
	            <input type="submit" id="btn_login" value="登入">
              </div>
			  <div class="manmail_errorMsgs" style="color:red">${errorMsgs}</div>
<%-- 			  <div class="manpassword_errorMsgs" style="color:red">${errorMsgs.manpassword_err}</div> --%>
		  </div>
	   </FORM>
  	  </div>
    </div>
    <!--footer -->
    <footer class="footer">
      <div class="w">
          <div class="copyright">
              <p>第四組  座位呢<br>
                  © 2020年Tibame 第四組Inc.保留所有權力。</p>
          </div>
          <div class="links">
              <dl>
                  <dt>關於第四組</dt>
              </dl>
              <dl>
                  <dt>關於平台</dt>
              </dl>
              <dl>
                  <dt>關於Tibame</dt>
              </dl>
          </div>
      </div>
    </footer>
  </body>
</html>