<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.custinfo.model.*" %>
<%
	
	String resname = (String)session.getAttribute("resname");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
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

</style>

<body>
<span><span>
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
        </div>
    </div>
    <div id="contact-form" class="sc-AxjAm gjUWaj">
        <h2 font-style="h2" color="text01" width="100%" class="sc-fzpans fkGnzT">確認訂位與填寫聯絡資訊</h2>
        <section class="sc-fznxsB HZhgf">
            <div class="sc-AxjAm hWjeGT"><label for="name" data-required="true"
                    class="sc-fznyAO hNOFHy">用餐人數</label><input id="people" required="" aria-invalid="false"
                    class="sc-fzoXzr daLexJ2" value="" disabled>
            </div>
            <div class="sc-AxjAm hWjeGT"><label for="name" data-required="true"
                    class="sc-fznyAO hNOFHy">訂位時間</label><input id="date" required="" aria-invalid="false"
                    class="sc-fzoXzr daLexJ1" value="" disabled>

            </div>
            <div class="sc-AxjAm hWjeGT1"><label for="name" data-required="true"
                    class="sc-fznyAO hNOFHy">訂位人姓名</label><input id="name" required="" aria-invalid="false"
                    class="sc-fzoXzr daLexJ" value=""><span id="msg1"></span>

            </div>
            <div class="sc-AxjAm dwdptv"><label for="phone" data-required="true"
                    class="sc-fznyAO hNOFHy">訂位人電話</label><input type="tel"id="phone" required="" aria-invalid="false"
                    class="sc-fzoXzr crzQPx" value=""><span id="msg2"></span>

            </div>

            <div class="sc-AxjAm jJmmTq"><label for="email" data-required="false" class="sc-fznyAO hNOFHy">訂位人
                    Email</label><input type="email" id="email" aria-invalid="false" class="sc-fzoXzr cRyFpS" value="<%=(custinfoVO==null)? "" : custinfoVO.getCustaccount() %>"><span id="msg3"></span>
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
                    class="sc-fzpjYC dqCINP" value=""></textarea>
                
            </div>
        </section>
        <button type="submit" class="sc-fzoyAV dTkVEz">
            <div hidden="" class="sc-AxjAm sc-fzqMdD gbAuPN">
                <div class="sc-AxjAm sc-fzowVh gSfeHE">
                    <div class="sc-AxjAm sc-fzoJus kTYBqE"></div>
                </div>
            </div><span class="sc-fzoLsD fqEnxe">確認訂位</span>
        </button>
        <button type="submit" class="sc-fzoyAV dTkVEz2">
            <div hidden="" class="sc-AxjAm sc-fzqMdD gbAuPN">
                <div class="sc-AxjAm sc-fzowVh gSfeHE">
                    <div class="sc-AxjAm sc-fzoJus kTYBqE"></div>
                </div>
            </div><span class="sc-fzoLsD fqEnxe">進行點餐</span>
        </button>
    </div>
   
    
  <%@ include file="/front-end/cust/order_process/order_process_footer.jsp" %>
    <script>
    var preview_func = function () {
        if (sessionStorage.getItem("form_data") != null) {
            var form_data = JSON.parse(sessionStorage.getItem("form_data"));
            console.log(form_data);
            people.value = form_data.people;
            date.value = form_data.date;
            if(form_data.seats !=null && (form_data.seats).length != 0){
            	$(".hWjeGT1").before('<div class="sc-AxjAm hWjeGT5"><label for="name" data-required="true" class="sc-fznyAO hNOFHy">座位</label><input id="seats" aria-invalid="false" class="sc-fzoXzr daLexJ4" value="'+form_data.seats+'" disabled></div>')
                console.log(form_data.seats);
            }
            if(form_data.name !=null && (form_data.name).length != 0){
            	$(".hWjeGT1 input").attr("value",form_data.name);
            }
            if(form_data.ctel !=null && (form_data.ctel).length != 0){
            	$(".dwdptv input").attr("value",form_data.ctel);
            }
            if(form_data.email !=null && (form_data.email).length != 0){
            	$(".jJmmTq input").attr("value",form_data.email);
            }
        };
    }
    
    
    preview_func();
    
        $(".bWxJST").on("click", "input", function () {
            $(this).attr("aria-checked", "true");
        });
        $("button.hamburger").on("click", function () {
            $(this).toggleClass("is-active");
        });
        $("div.header-btn").on("click", function () {
            $("div.header-dropdown-menu").slideToggle();
        });
        
        $("button.dTkVEz").on("click", function () {
            var re_nm = /^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,6}$/,
                re_idt = /^[A-Z]{1}\d{9}$/,
                re_tel = /^0\d{1}\d{6,8}$/,
                re_ctel = /^09\d{8}$/,
                re_eml = /^\S+@\w+((\.|-)\w+)*\.\w+$/;


            var name = $(".hWjeGT1 input").val();

            if (name == '') {
            	$("#msg1").text("!姓名不可為空").css("color","red").css("margin-left","15px");
            	return;
            } else if (!re_nm.test(name)) {
            	$("#msg1").text("!姓名格式錯誤，只能六個字(包含數字、英文)").css("color","red").css("margin-left","15px");
            	return;
            }else{
            	$("#msg1").text("");
            	};

            
            var ctel = $(".dwdptv input").val();
            if (ctel == '') {
            	$("#msg2").text("!請輸入您的手機號碼").css("color","red").css("margin-left","15px");
                return;
            } else if (!re_ctel.test(ctel)) {
            	$("#msg2").text("!您的手機號碼輸入錯誤").css("color","red").css("margin-left","15px");
                return;
            }else{
            	$("#msg2").text("");
        	};

            var email = $(".jJmmTq input").val();
            if (email == '') {
            	$("#msg3").text("!請輸入您的信箱").css("color","red").css("margin-left","15px");
                return;
            } else if (!re_eml.test(email)) {
            	$("#msg3").text("!您的信箱輸入錯誤!").css("color","red").css("margin-left","15px");
                return;
            }else{
            	$("#msg3").text("");
        	};
            var form_data = JSON.parse(sessionStorage.getItem("form_data"));

            var data_arr = {};
            data_arr.people=form_data.people;
            data_arr.date=form_data.date;
            data_arr.name=$(".hWjeGT1 input").val();
            data_arr.ctel=$(".dwdptv input").val();
            data_arr.email=$(".jJmmTq input").val();
            if($(".eauHyZ textarea").val() !=''){
            	data_arr.other=$(".eauHyZ textarea").val();
            }
            if($(".hWjeGT5 input") != null ){
            	data_arr.seats=$(".hWjeGT5 input").val();
            }
            sessionStorage.setItem("form_data", JSON.stringify(data_arr));
            location.href = '<%=request.getContextPath()%>/front-end/cust/order_process/order_process2.jsp';
        });
        console.log($("input.daLexJ2").attr("value"));
        
        
        $("button.dTkVEz2").on("click", function () {
        	var re_nm = /^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,6}$/,
            re_idt = /^[A-Z]{1}\d{9}$/,
            re_tel = /^0\d{1}\d{6,8}$/,
            re_ctel = /^09\d{8}$/,
            re_eml = /^\S+@\w+((\.|-)\w+)*\.\w+$/;


        var name = $(".hWjeGT1 input").val();

        if (name == '') {
        	$("#msg1").text("!姓名不可為空").css("color","red").css("margin-left","15px");
        	return;
        } else if (!re_nm.test(name)) {
        	$("#msg1").text("!姓名格式錯誤，只能六個字(包含數字、英文)").css("color","red").css("margin-left","15px");
        	return;
        }else{
        	$("#msg1").text("");
        	};

        
        var ctel = $(".dwdptv input").val();
        if (ctel == '') {
        	$("#msg2").text("!請輸入您的手機號碼").css("color","red").css("margin-left","15px");
            return;
        } else if (!re_ctel.test(ctel)) {
        	$("#msg2").text("!您的手機號碼輸入錯誤").css("color","red").css("margin-left","15px");
            return;
        }else{
        	$("#msg2").text("");
    	};

        var email = $(".jJmmTq input").val();
        if (email == '') {
        	$("#msg3").text("!請輸入您的信箱").css("color","red").css("margin-left","15px");
            return;
        } else if (!re_eml.test(email)) {
        	$("#msg3").text("!您的信箱輸入錯誤!").css("color","red").css("margin-left","15px");
            return;
        }else{
        	$("#msg3").text("");
    	};
    	var a = $(".eauHyZ textarea").val();
        if (a != '') {
        	$(".dqCINP").attr("value",a);

        }
        var form_data = JSON.parse(sessionStorage.getItem("form_data"));
        
        var data_arr = {};
        data_arr.people=form_data.people;
        data_arr.date=form_data.date;
        data_arr.name=$(".hWjeGT1 input").val();
        data_arr.ctel=$(".dwdptv input").val();
        data_arr.email=$(".jJmmTq input").val();
        if($(".eauHyZ textarea").val() !=''){
        	data_arr.other=$(".eauHyZ textarea").val();
       }
        
        if($(".hWjeGT5 input") != null){
        	data_arr.seats=$(".hWjeGT5 input").val();
        }
        sessionStorage.setItem("form_data", JSON.stringify(data_arr));
        location.href = "<%=request.getContextPath()%>/front-end/cust/order_process/order_process3(menu).jsp";
        });
        
        $(".dqCINP").attr("value",a);
        console.log($(".dqCINP").attr("value"));
        console.log($(".dqCINP").text());
    </script>

</body>

</html>