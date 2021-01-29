<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.orderinfo.model.*" %>
<%@ page import="com.custinfo.model.*" %>
<%


	String resname = (String)session.getAttribute("resname");
%>
<!DOCTYPE html>
<html lang="en">

<head>


    <title>Document</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/css/jquery-3.5.1.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/123.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/humburger.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index1.css">
</head>

<body>
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
                <a href="">確認訂單</a>
            </span>
        </div>
    </div>
    <div id="contact-form" class="sc-AxjAm gjUWaj">
        <h2 font-style="h2" color="text01" width="100%" class="sc-fzpans fkGnzT">確認訂單</h2>
        <section class="sc-fznxsB HZhgf">
            <div class="sc-AxjAm hWjeGT"><label for="people" data-required="true"
                    class="sc-fznyAO hNOFHy">用餐人數</label><input id="people" required="" aria-invalid="false"
                    class="sc-fzoXzr daLexJ2" value="" Readonly>


            </div>
            <div class="sc-AxjAm hWjeGT"><label for="date" data-required="true"
                    class="sc-fznyAO hNOFHy">訂位時間</label><input id="date" required="" aria-invalid="false"
                    class="sc-fzoXzr daLexJ1" value="" Readonly>

            </div>
            <div class="sc-AxjAm hWjeGT1"><label for="name" data-required="true"
                    class="sc-fznyAO hNOFHy">訂位人姓名</label><input name="subscriber" id="name1" required="" aria-invalid="false"
                    class="sc-fzoXzr daLexJ3" value="" Readonly>

            </div>
            <div class="sc-AxjAm dwdptv"><label for="phone" data-required="true"
                    class="sc-fznyAO hNOFHy">訂位人電話</label><input  type="tel" id="phone" required="" aria-invalid="false"
                    class="sc-fzoXzr crzQPx1" value="" disabled>

            </div>

            <div class="sc-AxjAm jJmmTq"><label for="email" data-required="false" class="sc-fznyAO hNOFHy">訂位人
                    Email</label><input type="email" id="email" aria-invalid="false" class="sc-fzoXzr cRyFpS1" value="" disabled>
                <div hidden="" class="sc-fzoaKM lhhYft"><svg viewBox="0 0 20 20" class="sc-fzpmMD JwZXS">
                        <g fill="none" fill-rule="evenodd">
                            <path d="M0 0h20v20H0z"></path>
                            <path fill="#FF532D"
                                d="M19.279 15.581l.002-.003-7.933-13.685a1.462 1.462 0 0 0-2.695 0L.72 15.578l.002.003a1.444 1.444 0 0 0-.221.765c0 .807.655 1.462 1.462 1.462.033 0 .065-.008.099-.01l.006.01h15.867l.005-.01c.034.002.065.01.1.01.807 0 1.461-.655 1.461-1.462 0-.282-.083-.542-.221-.765z"
                                class="sc-fznxKY gfAcsO"></path>
                            <path fill="#FFF"
                                d="M10.002 15.023c-.605 0-.967-.322-.967-.864 0-.551.361-.874.967-.874.6 0 .962.323.962.874 0 .542-.361.864-.962.864zM10.886 7.109l-.146 4.73H9.26l-.146-4.73z"
                                class="sc-fznxKY gfAcsO"></path>
                        </g>
                    </svg></div>
                <div hidden="" class="sc-fzoaKM lhhYft"><svg viewBox="0 0 20 20" class="sc-fzpmMD JwZXS">
                        <defs>
                            <rect id="icon-svg-email-a" width="20" height="14" rx="1"></rect>
                        </defs>
                        <g fill="none" fill-rule="evenodd">
                            <path d="M0 0h16v16H0z"></path>
                            <g transform="translate(0 3)">
                                <mask id="icon-svg-email-b" class="sc-fzqBkg esmkEi">
                                    <use xlink:href="#icon-svg-email-a"></use>
                                </mask>
                                <use xlink:href="#icon-svg-email-a" class="sc-fzqPZZ iQGxnQ"></use>
                                <path fill="#FFF" stroke-linecap="round" stroke-linejoin="round" d="M0 0l10 8 10-8"
                                    mask="url(#icon-svg-email-b)" class="sc-fznxKY frbnlN"></path>
                            </g>
                        </g>
                    </svg>訂位完成後，我們將發送訂位確認信到您的電子信箱，不再另寄簡訊通知。</div>
            </div>
            <div class="sc-AxjAm eauHyZ"><label data-required="false" class="sc-fznyAO ekSXjQ">其他備註</label><textarea
                    aria-invalid="false" rows="3" placeholder="有任何特殊需求嗎？可以先寫在這裡喔！（例如：行動不便、過敏）"
                    class="sc-fzpjYC dqCINP" value="" disabled></textarea>
                
            </div>
        </section>
        <section class="sc-fznxsB HZhgf1">
        </section>
        <button  class="sc-fzoyAV dTkVEz">
            <div hidden="" class="sc-AxjAm sc-fzqMdD gbAuPN">
                <div class="sc-AxjAm sc-fzowVh gSfeHE">
                    <div class="sc-AxjAm sc-fzoJus kTYBqE"></div>
                </div>
            </div><span class="sc-fzoLsD fqEnxe">完成</span>
        </button>
	<input type="hidden" id="paystatus"name="paystatus" value="">
	<input type="hidden" id="checkin" name="checkin" value="未報到">
	<input type="hidden" id="orderstatus" name="orderstatus" value="成立">
	<input type="hidden" id="resid"name="resid" value="${resid}">
	<input type="hidden" id="custid" name="custid" value="<%=(custinfoVO==null)? "C00002" : custinfoVO.getCustid()%>">
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
        $(".hWjeGT1 input").attr("disabled",true);
        var seat = "沒選位";
        var preview_func = function () {
            if (sessionStorage.getItem("form_data") != null) {
                var form_data = JSON.parse(sessionStorage.getItem("form_data"));
                console.log(form_data);
                console.log("people"+form_data.people);
                people.value = form_data.people;
                date.value = form_data.date;
                name1.value = form_data.name;
                phone.value = form_data.ctel;
                email.value = form_data.email;
               	$(".eauHyZ textarea").attr("placeholder",form_data.other);
                $(".hWjeGT1 input").attr("value",form_data.name);
                console.log( $("#people").attr("value"));
                $("#people").attr("value",form_data.people);
                console.log( $("#people").attr("value"));
                $("#date").attr("value",form_data.date);
                $(".dwdptv input").attr("value",form_data.ctel);
                $(".jJmmTq input").attr("value",form_data.email);
                if(form_data.seats != null){
                	$(".hWjeGT1").before('<div class="sc-AxjAm hWjeGT5"><label for="name" data-required="true" class="sc-fznyAO hNOFHy">座位</label><input id="seat" aria-invalid="false" class="sc-fzoXzr daLexJ4" value="'+form_data.seats+'" disabled></div>');
                	seat = form_data.seats;
                }else{
                	 console.log("沒選位");
                }
            };
            
        }
        preview_func();
        var count="";
		var mealall = new Array();
		var numall = new Array();
        var preview_func2 = function () {
            if (sessionStorage.getItem("form_data2") != null && sessionStorage.getItem("form_data2") != "[]") {
                var form_data2 = JSON.parse(sessionStorage.getItem("form_data2"));
                console.log(form_data2);

                var total =0;
 
                count = form_data2[0].count;

                
                for(i = 0;i < count;i++ ){
                	var num = (form_data2[i].price)*(form_data2[i].mealcount);
                	$(".HZhgf1").append('<p class='+form_data2[i].mealid+' value='
                			+form_data2[i].price+' placeholder='+form_data2[i].mealcount+' name='+form_data2[i].name+'>'+form_data2[i].name+" * "
                			+form_data2[i].mealcount+" = "+num+"元"+'</p>');
                			total+=num;
                			mealall[i]=form_data2[i].mealid;
                			numall[i]=form_data2[i].mealcount;
                }
                $(".HZhgf1").append('總額 = '+total+'元')
            }
        }
        preview_func2();
        console.log(JSON.stringify(mealall));
        console.log(JSON.stringify(numall));
        console.log(count);
        var paystatus = "";
        if (sessionStorage.getItem("form_data2") != null && sessionStorage.getItem("form_data2") != "[]"){
			 paystatus = "未付款";
		}else{
			 paystatus = "沒點餐";
		}
        $("button.dTkVEz").on("click", function () {
			let memberName = $(".hWjeGT1 input").val();
			var mealall2 = JSON.stringify(mealall);
			var numall2 = JSON.stringify(numall);
			var data2 = JSON.stringify(sessionStorage.getItem("form_data2"));
			
        	let data ={
                    action:"insert2",
                    "subscriber":$(".hWjeGT1 input").val(),
                    "subphone":$(".dwdptv input").val(),
                    "subtime":$("#date").val(),
                    "subnumber":$("#people").val(),
                    "paystatus":paystatus,
                    checkin:$("#checkin").val(),
                    "seat":seat,
                    orderstatus:$("#orderstatus").val(),
                    resid:$("#resid").val(),
                    custid:$("#custid").val(),
                    "count":count,
                    "data3":data2,
                    "mealall":mealall2,
                    "numall":numall2
                   };
        	console.log(data)
        	if($(".hWjeGT1 input").val()==""){
        		alert("人數不能空");
        		return;
        	}
        	
            $.ajax({
                     type: "POST",
                     url: "<%=request.getContextPath()%>/orderinfo/orderinfo.do",
                     data:data,
                     success: function (data) {
                    	 sessionStorage.removeItem("form_data2");
                    	 sessionStorage.removeItem("form_data");
                    	 sessionStorage.removeItem("seats");
//                     	 swal("ok");
                    	 
                    	 location.href = '<%=request.getContextPath()%>/front-end/cust/orderinfo_orderdet/ListAllOrder.jsp';
                     }
        	});
        });
        



    </script>

</body>

</html>