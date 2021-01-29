<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.* " %>
<%@ page import="com.shoppingcart.model.*"%>
<%@ page import="com.resinfo.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
// if(request.getParameter("resid") != null){
// 	session.setAttribute("resid", request.getParameter("resid"));
// 	System.out.println("");
// }else{
// 	System.out.println("從session拿");
// }
// ResInfoVO resInfoVO=null;
// String resid=null;
// if(session.getAttribute("resid")!=null){
// 	resid = (String)session.getAttribute("resid"); 
// 	System.out.println(resid);
// 	ResInfoService resInfoSvc = new ResInfoService();
// 	resInfoVO= resInfoSvc.getOneResInfo(resid);
// }else{
// 	System.out.println("從session拿");
// }
Set<ResInfoVO> set= new HashSet<ResInfoVO>();
ResInfoService resInfoSvc = new ResInfoService();
List<ResInfoVO> list2 = resInfoSvc.getAll();
@SuppressWarnings("unchecked")
Vector<MENU> buylist = (Vector<MENU>) session.getAttribute("shoppingcart");
if (buylist != null && (buylist.size() > 0)){
	for (int index=0; index < buylist.size(); index++) {
		MENU order = buylist.get(index);
		for(int i=0;i<list2.size();i++){
			if(order.getResid().equals(list2.get(i).getResid())){
					set.add(list2.get(i));
			}
		}
	}	 
}else{
	set.add(null);
}
pageContext.setAttribute("list", set);
%>

<!DOCTYPE html>
<html>
<head>
 	<title>Shoppingcar.jsp</title>
 	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/123.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/humburger.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index1.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
</head>
<style>
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
		font-size: 16px;
    	width: 20px;
	}
	.productInfo input{
		width:50px;
		text-align: center;
	}
	 #footer {
		padding: 0 0 2rem 0 ;
		background: rgb(236, 233, 233);
		text-align: center;
	}

		#footer a {
			color: rgba(255, 255, 255, 0.5);
		}

			#footer a:hover {
				color: #FFF;
			}

		#footer .copyright {
			color: black;
			font-size: 0.9rem;
			padding: 0;
			text-align: center;
		}

        ul.icons {
			cursor: default;
			list-style: none;
			padding-left: 0;
			margin-top:20px;
		}

		ul.icons li {
			display: inline-block;
			padding: 0 1rem 0 0;
			margin-top: 25px;
		}

		ul.icons li:last-child {
			padding-right: 0;
		}

		ul.icons li .icon:before {
			font-size: 2rem;
		}
		li span{
		color:black;
		}

	
</style>
<body>
<%@ include file="/front-end/cust/order_process/order_process_header.jsp" %>
    <jsp:useBean id="menuService" class="com.menu.model.MenuService" />
    <div id="contact-form" class="sc-AxjAm gjUWaj">
        <h2 font-style="h2" color="text01" width="100%" class="sc-fzpans fkGnzT">購物車</h2>
        <c:forEach var="resInfoVO" items="${list}">
        <%  
        ResInfoVO resInfoVO = (ResInfoVO)pageContext.getAttribute("resInfoVO");
		%>
        <section class="sc-fznxsB HZhgf">
        <h2 font-style="h2" color="text01" width="100%" class="sc-fzpans fkGnzT"><%= (resInfoVO==null)? "餐廳" : resInfoVO.getResname()%></h2>
        <br>

<%int count=0;if (buylist != null && (buylist.size() > 0)) {%>
<table>

	<%
	 for (int index=0; index < buylist.size(); index++) {
		 MENU order = buylist.get(index);
		 
	%>

	<%  
	System.out.println(order.getResid());
			if(order.getResid().equals(resInfoVO.getResid())){
				count++;
				if(order != null){
					byte[] imgbyte = order.getMealimg();
					String imgString =  getBaseString(imgbyte);
					pageContext.setAttribute("imgString",imgString);
				}
			 
		%>
	 	<article class="productInfo"><!-- Each individual product description -->
          <div><img alt="" src="${imgString}" class="productimg"></div>
          <p hidden id="resid" class="resid" value="<%=order.getResid()%>"><%=order.getResid()%></p>
          <p hidden id="mealid" class="mealid" value="<%=order.getMealid()%>"><%=order.getMealid()%></p>
          <p id="price" class="price" value="<%=order.getPrice()%>">$<%=order.getPrice()%></p>
          <p id="name" class="productContent" value="<%=order.getName()%>" ><%=order.getName()%></p>
          <button class="del"type="button"name="" >-</button>
          <input  name="number" placeholder="數量" class="buyNumber" disabled value="<%=order.getMealcount()%>" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')">
          <button class="add"type="button"name="" >+</button>
          <form name="deleteForm" action="<%=request.getContextPath()%>/shoppingcart/shoppingcart.do" method="POST">
              <input type="hidden" name="resid" value="<%=order.getResid()%>">
              <input type="hidden" name="mealid" value="<%=order.getMealid()%>">
              <input type="hidden" name="action"  value="DELETE">
              <input type="hidden" name="del" value="<%= index %>">
              <input type="submit" value="刪 除" class="button">
          </form>
        </article>
        	<%}%>
	</tr>
	<%}%>
</table>
<%}%><a hidden class="count" name="count" value="<%= count %>"><%= count %></a>
		 <button  class="sc-fzoyAV dTkVEz">
            <div hidden="" class="sc-AxjAm sc-fzqMdD gbAuPN">
                <div class="sc-AxjAm sc-fzowVh gSfeHE">
                    <div class="sc-AxjAm sc-fzoJus kTYBqE"></div>
                </div>
            </div><span class="sc-fzoLsD fqEnxe">前往餐廳訂位與訂餐</span>
        </button>
        </section>
      </c:forEach>
       
	
    </div>
     

    
    <footer id="footer">
        <div class="container">
            <ul class="icons">
                <li><a href="#"><span class="label">關於我們</span></a></li>
                <li><a href="#" ><span class="label">聯絡客服</span></a></li>
                <li><a href="#" ><span class="label">訂位資訊</span></a></li>
                <li><a href="#" ><span class="label">候位資訊</span></a></li>
            </ul>
        </div>
        <div class="copyright">
            &copy; 座位呢?
        </div>
    </footer>

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
		    $(".productInfo").on("click","button.add",function(){
		    	let mealid = $(this).closest(".productInfo").find("#mealid").attr("value");
		    	let name = $(this).closest(".productInfo").find("#name").attr("value");
		    	let price = $(this).closest(".productInfo").find("#price").attr("value");
		    	let quantity = $(this).closest(".productInfo").find(".buyNumber").attr("value");
		    	let img = $(this).closest(".productInfo").find("img").attr("src");
		    	console.log(quantity);
		    	console.log(quantity++);
		    	$(this).closest(".productInfo").find(".buyNumber").attr("value",quantity);

		    	let data ={
		    			"action":"ADD",
		    			"name":name,
		    			"price":price,
		    			"quantity":1,
		    			"img":img,
		    			"mealid":mealid
		               };
		    	console.log(data);
		    	$.ajax({
		    		type:"POST",
		    		url: "<%=request.getContextPath()%>/shoppingcart/shoppingcart.do",
		    		data:data,
		    		success: function (data) {
		            }
		    	})   
		    });
		    $(".productInfo").on("click","button.del",function(){
		    	let resid = $(this).closest(".productInfo").find("#resid").attr("value");
		    	let mealid = $(this).closest(".productInfo").find("#mealid").attr("value");
		    	let name = $(this).closest(".productInfo").find("#name").attr("value");
		    	let price = $(this).closest(".productInfo").find("#price").attr("value");
		    	let quantity = $(this).closest(".productInfo").find(".buyNumber").attr("value");
		    	let img = $(this).closest(".productInfo").find("img").attr("src");
		    	let del = $(this).closest(".productInfo").find("input").eq(4).attr("value");
		    	if(quantity == 1){
		    		$(this).closest(".productInfo").remove();
		    	}else{
		    		$(this).closest(".productInfo").find(".buyNumber").attr("value",quantity-1);
		    	}
		    	let data ={
		    			"action":"REDUCE",
		    			"name":name,
		    			"price":price,
		    			"quantity":1,
		    			"img":img,
		    			"mealid":mealid,
		    			"del":del,
		    			"resid":resid
		               };
		    	console.log(data);
		    	$.ajax({
		    		type:"POST",
		    		url: "<%=request.getContextPath()%>/shoppingcart/shoppingcart.do",
		    		data:data,
		    		success: function (data) {
		           	
		            }
		    	})


		    });
		    $("button.dTkVEz").on("click", function () {
		    	sessionStorage.removeItem("form_data2");
		    	console.log($(this).closest("section").find(".count").attr("value"));
		    	let count = $(this).closest("section").find(".count").attr("value");
		    	var data = [];
		    	if($(this).closest("section").eq(0).find("p").eq(1).attr("value")!=null){
		    		for(i = 0;i < count;i++){
			    		var data_arr = {};
			    		console.log($(this).closest("section").find(".productInfo").eq(i).find("p").eq(3).attr("class"));
			            data_arr.mealid=$(this).closest("section").find(".productInfo").eq(i).find("p").eq(1).attr("value");
			            data_arr.name=$(this).closest("section").find(".productInfo").eq(i).find("p").eq(3).attr("value");
			            data_arr.mealcount=$(this).closest("section").find(".productInfo").eq(i).find("input").attr("value");
			            data_arr.price=$(this).closest("section").find(".productInfo").eq(i).find("p").eq(2).attr("value");
			            data_arr.count=count;
			            data_arr.action="insert2";
			            data.push(data_arr);
			    	}
		    	}
		    	console.log($(this).closest("section").eq(0).find("p").eq(0).attr("value"));
		    	var data_arr2 = {};
	            data_arr2.resid=$(this).closest("section").eq(0).find("p").eq(0).attr("value");
	            var a = $(this).closest("section").eq(0).find("p").eq(0).attr("value");
	            sessionStorage.setItem("form_data_resid", JSON.stringify(data_arr2));
		   		
		    	sessionStorage.setItem("form_data2", JSON.stringify(data));
		    	if(sessionStorage.getItem("form_data2") != "[]" && sessionStorage.getItem("form_data2") != "{}"){
		    		location.href = '<%=request.getContextPath()%>/front-end/cust/order_process/order_process.jsp?resid='+a; 
		    	}else{
		    		location.href = '<%=request.getContextPath()%>/index_plat.jsp'; 	
		    	}
		        
	        });
    </script>
</body>
</html>