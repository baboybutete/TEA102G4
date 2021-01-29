<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.menu.model.*"%>

<%@ page import="com.menu.controller.*"%> 

<%


if(request.getParameter("resid") != null){
	session.setAttribute("resid", request.getParameter("resid"));
	System.out.println("");
}else{
	
	System.out.println("從session拿");
}
	
%>
<% 	
	String resid=(String)session.getAttribute("resid");
	MenuService menuSvc = new MenuService();
	List<MenuVO> list = menuSvc.getSpecificAll(resid);
	List<MenuVO> list2 = new ArrayList();
	for(int i=0;i<list.size();i++){
		if(((MenuVO)list.get(i)).getMealstatus().equals("上架")){
			list2.add((MenuVO)list.get(i));
		}
	}
	pageContext.setAttribute("list", list2);
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
  	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/css/jquery-3.5.1.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/123.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/humburger.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index1.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
</head>
<style>

	
	table{
		border : solid black;
		border-collapse: collapse;
		
		border-spacing: 0px;
		
	}
	
	th,td{
		border : solid black;
		width:100px;
		height:100px;
	}
	
	th{
		background-color:orange;
		width:70px;
		text-align:center;
	}
	
	tr:hover {
				background-color: #f5f5f5;
	}

	
	a{
		text-decoration:none;
	}


	.productInfo .productimg {
	width: auto;
	width: 100%;
	height:175px;
	}
	.productInfo {
    float: left;
    padding-left: 3%;
    padding-right: 3%;
    width: 25%;
    height: 280px;
    text-align: center
	}
	.productInfo button{
		background-color: rgb(239, 239, 239);
		border: 1px solid black	;
		border-radius:8px;
		font-size: 14px;
    	width: 80px;
        height: 20px;
        margin-top: 5px;
	}
	.productInfo input{
	text-align: center;
	width: 50px;
	}
	.productInfo input.button{
		width:100px;
	}

	
</style>
<body>

 <%@ include file="/front-end/cust/order_process/order_process_header.jsp" %>
    <jsp:useBean id="menuService" class="com.menu.model.MenuService" />
    <div id="contact-form" class="sc-AxjAm gjUWaj">
        <h2 font-style="h2" color="text01" width="100%" class="sc-fzpans fkGnzT">菜單</h2>
        <section class="sc-fznxsB HZhgf">
            <table>
		<%@ include file="page1.file" %>
		<c:forEach var="menuVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
		<%  
			MenuVO menuVO = (MenuVO)pageContext.getAttribute("menuVO");
			
			if(menuVO != null){
				byte[] imgbyte = menuVO.getMealimg();
				
				String imgString =  getBaseString(imgbyte);
				pageContext.setAttribute("imgString",imgString);
			}
		%>
		<article class="productInfo"><!-- Each individual product description -->
		
          <div><img alt="" src="${imgString}" class="productimg"></div>
          <p hidden class="mealid" value="${menuVO.mealid}">${menuVO.mealid}</p>
          <p class="price" value="${menuVO.mealprice}">$${menuVO.mealprice}</p>
          <p class="productContent" value="${menuVO.mealname}" >${menuVO.mealname}</p>
          <div class="number"align="center">數量：<input class="quantity" type="text" name="quantity" size="3" value=1 onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')"></div>
          <button class="shopping" value="放入購物車">放入購物車</button>
          <input type="hidden" id="mealid"name="mealid" value="${menuVO.mealid}">
          <input type="hidden" id="name" name="name" value="${menuVO.mealname}">
          <input type="hidden" id="price" name="price" value="${menuVO.mealprice}">
      	  <input type="hidden" name="action" value="ADD">

        </article>
				
		  <td hidden>${menuVO.mealstatus}</td>
		  <td hidden><fmt:formatDate value="${menuVO.adddate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
		
		</c:forEach>
	
	</table>
	
        </section>
        <%@ include file="page2.file" %>
        <button  class="sc-fzoyAV dTkVEz">
            <div hidden="" class="sc-AxjAm sc-fzqMdD gbAuPN">
                <div class="sc-AxjAm sc-fzowVh gSfeHE">
                    <div class="sc-AxjAm sc-fzoJus kTYBqE"></div>
                </div>
            </div><span class="sc-fzoLsD fqEnxe">前往購物車</span>
        </button>
	
    </div>
<%@ include file="/front-end/cust/order_process/order_process_footer.jsp" %>
    <script>
    $(".bWxJST").on("click", "input", function () {
        $(this).attr("aria-checked", "true");
    });
    $("button.hamburger").on("click", function () {
        $(this).toggleClass("is-active");
    });
    $("div.header-btn").on("click", function () {
        $("div.header-dropdown-menu").slideToggle();
    });

    $(".productInfo button").on("click",function(){
    	let mealid = $(this).closest(".productInfo").find("#mealid").attr("value");
    	let name = $(this).closest(".productInfo").find("#name").attr("value");
    	let price = $(this).closest(".productInfo").find("#price").attr("value");
    	let quantity = $(this).closest(".productInfo").find(".quantity").val();
    	let img = $(this).closest(".productInfo").find("img").attr("src");
    	
    	let data ={
    			"action":"ADD",
    			"name":name,
    			"price":price,
    			"quantity":quantity,
    			"img":img,
    			"mealid":mealid,
    			"resid":"${resid}"
               };
    	console.log(data);
    	$.ajax({
    		type:"POST",
    		url: "<%=request.getContextPath()%>/shoppingcart/shoppingcart.do",
    		data:data,
    		success: function (data) {
    			swal("成功新增"+name+" * "+quantity+"至購物車中");
            }
    	})   
    });
    $("button.dTkVEz").on("click", function () {
    	location.href = '<%=request.getContextPath()%>/front-end/cust/shoppingcart/Shoppingcar.jsp'; 
    });
	
    </script>

</body>

</html>