<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.menu.model.*"%>
<%@ page import="com.menu.controller.*"%> 

<% 	
	String resname = (String)session.getAttribute("resname");
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
    height: 250px;
    text-align: center
	}
	.productInfo button{
		background-color: rgb(239, 239, 239);
		border: 1px solid black	;
		border-radius:8px;
		font-size: 16px;
    	width: 20px;
	}
	.productInfo input{
		width:50px;
		text-align: center;
	}

	
</style>
<body>
	${param.resid}
  <%@ include file="/front-end/cust/order_process/order_process_header.jsp" %>
    <div class="sc-AxjAm dVvNhC">
        <div class="sc-fznLPX dtmoaU">
            <span>
                <a href="">網站首頁</a>
            </span>

            <img src="<%=request.getContextPath()%>/images/back (2).png">
            <span>
                <a href="<%=request.getContextPath()%>/front-end/cust/resinfo/index_res.jsp"><%=resname%></a>
            </span>
            <img src="<%=request.getContextPath()%>/images/back (2).png">
            <span>
                <a href="<%=request.getContextPath()%>/front-end/cust/order_process/order_process.jsp">訂位</a>
            </span>
            <img src="<%=request.getContextPath()%>/images/back (2).png">
            <span>
                <a href="">填寫聯絡資訊</a>
            </span>
            <img src="<%=request.getContextPath()%>/images/back (2).png">
            <span>
                <a href="">點選餐點</a>
            </span>
        </div>
    </div>
    <jsp:useBean id="menuService" class="com.menu.model.MenuService" />
    <div id="contact-form" class="sc-AxjAm gjUWaj">
        <h2 font-style="h2" color="text01" width="100%" class="sc-fzpans fkGnzT">餐點列表</h2>
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
          <button class="del"type="button"name="" >-</button>
          <input  name="number" placeholder="數量" class="buyNumber" disabled value="1" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')">
          <button class="add"type="button"name="" >+</button>
        </article>
				
		  <td hidden>${menuVO.mealstatus}</td>
		  <td hidden><fmt:formatDate value="${menuVO.adddate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</c:forEach>
	</table>
        </section>
        <%@ include file="page2.file" %>
       <h2 font-style="h2" color="text01" width="100%" class="sc-fzpans fkGnzT">已點選的餐點</h2>
       <section class="sc-fznxsB HZhgf1">
        </section>
        <button  class="sc-fzoyAV dTkVEz">
            <div hidden="" class="sc-AxjAm sc-fzqMdD gbAuPN">
                <div class="sc-AxjAm sc-fzowVh gSfeHE">
                    <div class="sc-AxjAm sc-fzoJus kTYBqE"></div>
                </div>
            </div><span class="sc-fzoLsD fqEnxe">確認訂位與訂餐</span>
        </button>
	
    </div>
     

    
     <%@ include file="/front-end/cust/order_process/order_process_footer.jsp" %>
    
    <script>
    var count=0;
    var preview_func2 = function () {
        if (sessionStorage.getItem("form_data2") != "[]" && sessionStorage.getItem("form_data2") != null) {
            var form_data2 = JSON.parse(sessionStorage.getItem("form_data2"));
            console.log(sessionStorage.getItem("form_data2"));
            count = form_data2[0].count;
            for(i = 0;i < count;i++ ){
            	var num = (form_data2[i].price)*(form_data2[i].mealcount);
            	$(".HZhgf1").append('<p class='+form_data2[i].mealid+' value='
            			+form_data2[i].price+' placeholder='+form_data2[i].mealcount+' name='+form_data2[i].name+'>'+form_data2[i].name+" * "
            			+form_data2[i].mealcount+" = "+num+"元"+'<button class="remove" >－移除</button>'+'</p>');
            	for(j=0;j<4;j++){
            		if($(".mealid").eq(j).attr("value") == form_data2[i].mealid){
            			$(".buyNumber").eq(j).attr("value",form_data2[i].mealcount);
            		}
            	}
            }
        }
        
    }
    preview_func2();
    
    $(".bWxJST").on("click", "input", function () {
        $(this).attr("aria-checked", "true");
    });
    $("button.hamburger").on("click", function () {
        $(this).toggleClass("is-active");
    });
    $("div.header-btn").on("click", function () {
        $("div.header-dropdown-menu").slideToggle();
    });
    
    $(".productInfo").on("click","button.add",function(){
		var num = $(this).closest("article").find("input.buyNumber").val();
		var productname = $(this).closest("article").find("p.productContent").text();
		var id = $(this).closest("article").find("p.mealid").text();
		var price = $(this).closest("article").find("p.price").attr("value");
		<%--
        console.log($(this).closest("article").find("input.buyNumber").val());
        console.log($(this).closest("article").find("p.mealid").text());
        console.log($(this).closest("article").find("p.price").text());
        console.log($(this).closest("article").find("p.productContent").text());
        --%>
		if(num != ""){
			for(i = 0;i <= count;i++){
            	if(($(".HZhgf1 > p").eq(i).attr("class")) == id){
            		let a = parseInt($(".HZhgf1 > p").eq(i).attr("placeholder"));
            		
            		$(".HZhgf1 > p").eq(i).attr("placeholder",(a+1));
            		let b = parseInt($(".HZhgf1 > p").eq(i).attr("placeholder"));
                    $(".HZhgf1 > p").eq(i).remove();
            		count--;
            		$(".HZhgf1").append('<p class='+id+' value='+price+' placeholder='+b+' name='+productname+'>'
                        	+productname+"*"+b
                        	+" = "+b*price+"元"+'<button class="remove" >－移除</button>'+'</p>')
                            count++;
            		$(this).closest("article").find("input").attr("value",b);
            		var data = [];
                	for(i = 0;i < count;i++){
                		var data_arr = {};
                        data_arr.mealid=$(".HZhgf1 > p").eq(i).attr("class");
                        data_arr.name=$(".HZhgf1 > p").eq(i).attr("name");
                        data_arr.mealcount=$(".HZhgf1 > p").eq(i).attr("placeholder");
                        data_arr.price=$(".HZhgf1 > p").eq(i).attr("value");
                        data_arr.count=count;
                        data_arr.action="insert2";
                        data.push(data_arr);
                	}
                    sessionStorage.setItem("form_data2", JSON.stringify(data));
            		return;
            	}
        	}
        	$(".HZhgf1").append('<p class='+id+' value='+price+' placeholder='+num+' name='+productname+'>'
                	+productname+"*"+num
                	+" = "+num*price+"元"+'<button class="remove" >－移除</button>'+'</p>')
                    count++;
        	var data = [];
        	for(i = 0;i < count;i++){
        		var data_arr = {};
                data_arr.mealid=$(".HZhgf1 > p").eq(i).attr("class");
                data_arr.name=$(".HZhgf1 > p").eq(i).attr("name");
                data_arr.mealcount=$(".HZhgf1 > p").eq(i).attr("placeholder");
                data_arr.price=$(".HZhgf1 > p").eq(i).attr("value");
                data_arr.count=count;
                data_arr.action="insert2";
                data.push(data_arr);
        	}
            sessionStorage.setItem("form_data2", JSON.stringify(data));
		}
    });
    
    $(".productInfo").on("click","button.del",function(){
		var num = $(this).closest("article").find("input.buyNumber").val();
		var productname = $(this).closest("article").find("p.productContent").text();
		var id = $(this).closest("article").find("p.mealid").text();
		var price = $(this).closest("article").find("p.price").attr("value");

		for(i = 0;i <= count;i++){
            if(($(".HZhgf1 > p").eq(i).attr("class")) == id){
            	let a = parseInt($(".HZhgf1 > p").eq(i).attr("placeholder"));
				if((a-1) <= 0){
					$(".HZhgf1 > p").eq(i).remove();
					count--;
						
					var data = [];
	                for(i = 0;i < count;i++){
	                	var data_arr = {};
	                       data_arr.mealid=$(".HZhgf1 > p").eq(i).attr("class");
	                       data_arr.name=$(".HZhgf1 > p").eq(i).attr("name");
	                       data_arr.mealcount=$(".HZhgf1 > p").eq(i).attr("placeholder");
	                       data_arr.price=$(".HZhgf1 > p").eq(i).attr("value");
	                       data_arr.count=count;
	                       data_arr.action="insert2";
	                       data.push(data_arr);
	                }
	                   sessionStorage.setItem("form_data2", JSON.stringify(data));
            		return;
            	}
            	$(".HZhgf1 > p").eq(i).attr("placeholder",(a-1));
            	let b = parseInt($(".HZhgf1 > p").eq(i).attr("placeholder"));
                $(".HZhgf1 > p").eq(i).remove();
            	count--;
            	$(".HZhgf1").append('<p class='+id+' value='+price+' placeholder='+b+' name='+productname+'>'
                        +productname+"*"+b
                        +" = "+b*price+"元"+'<button class="remove" >－移除</button>'+'</p>')
                           count++;
            	$(this).closest("article").find("input").attr("value",b);
            	var data = [];
                for(i = 0;i < count;i++){
                	var data_arr = {};
                       data_arr.mealid=$(".HZhgf1 > p").eq(i).attr("class");
                       data_arr.name=$(".HZhgf1 > p").eq(i).attr("name");
                       data_arr.mealcount=$(".HZhgf1 > p").eq(i).attr("placeholder");
                       data_arr.price=$(".HZhgf1 > p").eq(i).attr("value");
                       data_arr.count=count;
                       data_arr.action="insert2";
                       data.push(data_arr);
                }
                   sessionStorage.setItem("form_data2", JSON.stringify(data));
            }
        }
    });
    
    
    $(".HZhgf1").on("click","button",function(){
    
    	let num = $(this).closest("p").attr("class");
    	for(j=0;j<4;j++){
    		if($(".mealid").eq(j).attr("value") == num){
    			console.log($(".mealid").eq(j).attr("value"));
    			$(".buyNumber").eq(j).attr("value",1);
    		}
    	}
    	$(this).closest("p").remove();
    	count--;
    	var data = [];
    	for(i = 0;i < count;i++){
        	var data_arr = {};
               data_arr.mealid=$(".HZhgf1 > p").eq(i).attr("class");
               data_arr.name=$(".HZhgf1 > p").eq(i).attr("name");
               data_arr.mealcount=$(".HZhgf1 > p").eq(i).attr("placeholder");
               data_arr.price=$(".HZhgf1 > p").eq(i).attr("value");
               data_arr.count=count;
               data_arr.action="insert2";
               data.push(data_arr);
        }
           sessionStorage.setItem("form_data2", JSON.stringify(data));
    });
    $("button.dTkVEz").on("click",function(){
    	var data = [];
    	for(i = 0;i < count;i++){
    		var data_arr = {};
    		console.log($(".HZhgf1 > p").eq(i).attr("class"));
        	console.log($(".HZhgf1 > p").eq(i).attr("value"));
        	console.log($(".HZhgf1 > p").eq(i).attr("placeholder"));
        	console.log($(".HZhgf1 > p").eq(i).attr("name"));

            data_arr.mealid=$(".HZhgf1 > p").eq(i).attr("class");
            data_arr.name=$(".HZhgf1 > p").eq(i).attr("name");
            data_arr.mealcount=$(".HZhgf1 > p").eq(i).attr("placeholder");
            data_arr.price=$(".HZhgf1 > p").eq(i).attr("value");
            data_arr.count=count;
            data_arr.action="insert2";
            data.push(data_arr);
    	}
    	sessionStorage.setItem("form_data2", JSON.stringify(data));
        location.href = '<%=request.getContextPath()%>/front-end/cust/order_process/order_process2.jsp';
    });

    
    

    </script>

</body>

</html>