<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.custinfo.model.*" %>
<%@ page import="java.util.Base64" %>
<%@ include file="/others/getBaseStirng.file" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style1.css">
    <style>
       

        main{
            /* border: tomato 1px solid; */
            width: 700px;
            margin: 20px auto;
            min-height:600px;
        }
        .resalt-box{
            /* border: solid orange 1px; */
            width: 550px;
            margin: 0 auto;
            background-color: #F5F5F5;
            position: relative;

        }
        .resalt-box .top{
            margin-bottom: 10px;
        }

        div.top h2{
            font-size: 0.1em;

        }
        div.listing {
            /* border: solid 1px red; */
            position: relative;
            /* overflow: hidden; */
            /* height: 150px; */
            margin-bottom: 20px;
        }

        div.listing a{
            text-decoration: none;
            color: black;
        } 
        div.searchTop{
            height: 100%;
        }

        div.LInfoA~div{
            margin-bottom: 10px;
           
        }
        div.searchItem{
            display: block;
            height: 150px;
            margin-bottom: 15px;
            margin-top: 10px;
            /* border: solid 1px green;  */  
            box-shadow: 0 2px 3px 3px rgba(0,0,0, 0.1);

        }
        div.searchLeft{
            float: left;
            /* border: solid 1px black; */
            height: 100%;
            display: block;
            width: 100px;
            height: 100px;
            margin: 25px 10px;
        }
        div.searchLeft img{
            vertical-align: middle;
            height: 100%;
            width: 100%;
        }
       
        div.leftInfo{
            /* float: right; */
            margin-left: 130px;
            height: 100%;
        }
        div.info{
            letter-spacing: 1px;
        }
        
        div.leftInfo .LInfoA{
            font-size: 1.3em;
            font-weight: 600;
            margin-bottom: 15px;
        }
        div.leftInfo .LInfoB{
            color:gray;
            font-size: 1em;
        }
        div.LInfoD ul li{
            display: inline-block;
            font-size: 12px;
            margin-right: 12px;
        }
        div.divide-line{
            background-color: rgb(233,236, 239);
            border:rgb(233,236, 239) solid 1px;
            /* border-bottom: black 1px; */
            /* position: absolute; */
            width: 100%;
            bottom:0;
            box-sizing : border-box;
            margin-bottom:15px;
        }
        
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style1.css">
    <script src="https://kit.fontawesome.com/26c35b8c14.js" crossorigin="anonymous"></script>
</head>

<body>
	<%@ include file="/index_plat_header.jsp" %>
	<jsp:useBean id="resService" class="com.resinfo.model.ResInfoService"></jsp:useBean>
    <main>
        <div class="resalt-box">
            <div class="top">
                <h2><span>總共找到${result.size()}筆資料</span></h2>
            </div>
            <div class="listing">
                <div class="divide-line"></div>
                <c:forEach var="mapvo" items="${result}">
                	<c:set var="resinfoVO1" value="${resService.getOneResInfo(mapvo.resid)}"></c:set>
                	<div class="searchItem">
                    <div class="searchTop">
                        <div class="searchLeft"><img src="<%=request.getContextPath()%>/resinfo/ResInfoServlet?action=getOneImg&resid=${mapvo.resid}" alt=""></div>
                        <div class="leftInfo">
                            <div class="LInfoA"><a href="<%=request.getContextPath()%>/resinfo/ResInfoServlet?action=getOne_For_Display_By_Client&resid=${mapvo.resid}"><span>${mapvo.resname}</span></a></div>
                            <div class="LInfoB"><i class="fas fa-map-marker-alt"></i><span>地址 : ${mapvo.resaddid}</span></div>
                            <div class="LInfoC">營業時間 : <fmt:formatDate value="${mapvo.opening}" pattern="HH:mm"/> ~ <fmt:formatDate value="${mapvo.closing}" pattern="HH:mm"/></div>                          
                            <div class="LInfoD"><ul class="LID_list">
                            	<c:if test="${resinfoVO1.barrierfree ne '無'}">
                            		<li><span class="info_text">無障礙空間</span><i class="fas fa-wheelchair"></i></li>
                            	</c:if>
                                <c:if test="${resinfoVO1.payinfo eq '信用卡'}">
                                	<li><span class="info_text">信用卡</span><i class="fas fa-credit-card"></i></li>
                                </c:if>
                                <c:if test="${resinfoVO1.parentchild ne '無'}">
                                	<li><span class="info_text">親子友善</span><i class="fas fa-user-friends"></i></li>
                                </c:if>
                            </ul></div>
                        </div>
                    </div>
                </div>
                <div class="divide-line"></div>
                </c:forEach>
               
        	</div>
        </div>
    </main>
    
    <%@ include file="/index_plat_footer.jsp" %>
    
    

</body>

</html>