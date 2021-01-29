<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>


    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>座位呢-餐廳頁面(消費者看到的)</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/css/style1.css">
    <!-- <link rel="stylesheet" href="mobile.css"> -->
</head>

<body>
    <!-- 頭部 -->
    <div class="header">
        <!-- logo部分 -->
        <div class="log">
            <img src="<%=request.getContextPath() %>/front-end/images/Asset 19.png" alt="">
        </div>

        <!-- 搜索模塊 -->
        <div class="searchmap">
            <div class="lookmap"></div>
            <div class="searchinput"><input type="text" placeholder="請輸入想要搜尋的地點"></div>
        </div>

        <div class="searchfood">
            <div class="lookfood"></div>
            <div class="searchinput"><input type="text" placeholder="請輸入食物類型"></div>
        </div>
        <button class="searchbut"></button>

        <div class="home">
            <a href="<%=request.getContextPath() %>/login-html/login/custLogin.jsp">登入</a>

        </div>

        <div class="manager">
            <a href="<%=request.getContextPath()%>/member/member.do">會員中心</a>
        </div>
        <!-- 會員 -->
        <div class="user">
            <img src="<%=request.getContextPath() %>/front-end/images/user.jpg" alt="">
            <span>我真可愛</span>

        </div>
    </div>

    <!--------------------------- mobil顯示的搜尋欄位------------------------------------>
    
    <!-- 上方搜尋欄 -->
    <div class="navbar">
        <div class="navbar_container">
            <div class="navbar_header_container">
                <a class="navbar_brand" href="">
                    <img src="<%=request.getContextPath() %>/front-end/images/logonb.png" alt="">
                </a>
            </div>
            <div class="navbar_nav_container">
                <ul class="navbar_nav_list">
                    <li>
                        <a href="javascript:void(0);" >☰</a>
                        <ul class="dropdown_menu">
                            <li>
                                <a href="">餐廳資訊</a>
                            </li>
                            <li>
                                <a href="">優惠活動</a>
                            </li>
                            <li>
                                <a href="">評論</a>
                            </li>
                            <li>
                                <a href="">線上訂位</a>
                            </li>
                            <li>
                                <a href="">菜單</a>
                            </li>
                            <li>
                                    <form action="">
                                        <input type="search" placeholder="搜尋食物"><input type="search" placeholder="搜尋地點">
                                        <button type="submit" class="searchbut"></button>
                                    </form>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!--------------------------- mobil顯示的搜尋欄位------------------------------------>
    
    <!-- banner-->
    <div class="banner">
        <div class="q">
            <img src="<%=request.getContextPath() %>/front-end/images/111.jpg" alt="" class="res">
            <div class="subnav">
                <ul>
                    <li><a href="#">餐聽資訊 <span> &gt; </span></a></li>
                    <li><a href="#">優惠活動 <span> &gt; </span> </a></li>
                    <li><a href="#"> 評  論 <span> &gt; </span> </a></li>
                    <li><a href="#">線上訂位 <span> &gt; </span> </a></li>
                    <li><a href="#"> 菜  單 <span> &gt; </span> </a></li>
                    <!-- <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                    <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                    <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                    <li><a href="#">愛睏 <span> &gt; </span> </a></li> -->
                </ul>
            </div>
        </div>
    </div>
    <!-- 核心區-->
    <div class="box w">
        <div class="box-hd">
            <h3>餐廳座位資訊</h3>
            <a href="#">返回頂部</a>
        </div>
        <hr>
        <div class="w clearfix">
            <div class="setdown">
                <img src="<%=request.getContextPath() %>/front-end/images/setdown.png" alt="">
            </div>
            <!-- 候位資訊-->
            <div class="waitinginfo">
                <h2>目前候位狀態</h2>
                <div class="bd">
                    <ul>
                        <li>
                            <h4>目前叫號號碼</h4>
                            <p>1號</p>
                        </li>
                        <li>
                            <h4>你的候位號碼</h4>
                            <p>1000號</p>
                        </li>
                        <li>
                            <h4>目前等候組數</h4>
                            <p>4組</p>
                        </li>
                    </ul>
                    <a href="#" class="more">領取候位號碼</a>
                </div>
            </div>
        </div>
    </div>


    <div class="box w">
        <div class="box-hd">
            <h3>餐廳資訊</h3>
            <a href="#">返回頂部</a>
        </div>
        <hr>
        <div class="w clearfix">
            <div class="resinfo">
                <ul>
                    <li>電話 :</li>
                    <li>最後點餐時間 :</li>
                    <li>樓層 :</li>
                    <li>親子空間 :</li>
                    <li>無障礙空間 :</li>
                    <li>營業時間 :</li>
                    <li>地址 :</li>
                    <li>交通資訊 :</li>
                </ul>
            </div>
            <div class="mapdiv">        
                    <img src="<%=request.getContextPath() %>/front-end/images/map.png" alt="">
            </div>
        </div>
    </div>
    </div>

    <!-- footer  -->
    <div class="footer">
        <div class="w">
            <div class="copyright">
                <img src="<%=request.getContextPath() %>/front-end/images/Asset 17.png" alt="">
                <p>第四組 ------ 座位呢<br>
                    © 2020年Tibame 第四組Inc.保留所有權力。--註冊第6666666666666號</p>
                <a href="#" class="app">下載APP</a>
            </div>
            <div class="links">
                <dl>
                    <dt>關於第四組</dt>
                    <dd><a href="#">關於</a></dd>
                    <dd><a href="#">團隊成員</a></dd>
                    <dd><a href="#">網站理念</a></dd>
                    <dd><a href="#">使用技術</a></dd>
                    <dd><a href="#">使用教學</a></dd>
                </dl>
                <dl>
                    <dt>關於平台</dt>
                    <dd><a href="#">關於</a></dd>
                    <dd><a href="#">管理團隊</a></dd>
                    <dd><a href="#">工作機會</a></dd>
                    <dd><a href="#">客戶服務</a></dd>
                    <dd><a href="#">使用教學</a></dd>
                </dl>
                <dl>
                    <dt>關於Tibame</dt>
                    <dd><a href="#">關於</a></dd>
                    <dd><a href="#">教學團隊</a></dd>
                    <dd><a href="#">指導老師</a></dd>
                    <dd><a href="#">銘謝</a></dd>
                </dl>
            </div>
        </div>
    </div>
</body>

</html>