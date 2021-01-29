<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>座位呢 -重新登入</title>
    <link rel="stylesheet" href='<%=request.getContextPath()%>/css/admin_relogin.css'>
  </head>
  <body>
    <div class="">
      <!-- header -->
      <div class="header w">
        <div class="logo">
          <img src="<%=request.getContextPath()%>/images/Asset 19.png">
        </div>
      </div>
      <!-- 登入視窗 -->
      <div class="">
        <FORM METHOD="post" ACTION="">
          <div id="login_frame">
              <div id="login_control">
                <a>您已登出後台管理中心</a>
                <br>
                <a href='<%=request.getContextPath()%>/back-end/adminLogin.jsp'>點此重新登入</a>
              </div>
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
