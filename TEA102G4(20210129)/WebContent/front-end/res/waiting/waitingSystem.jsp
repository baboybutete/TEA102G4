<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%-- <link href="<%=request.getContextPath()%>/css/waitingSystem.css"> --%>
<style>
	.waitinginfo {
    	
    	width: 230px;
    	height: 500px;
    	background-color: #fff;
    	
   		margin : 20px auto;
	}
	.waitinginfo h2 {
    	height: 48px;
    	background-color: #9bceea;
    	text-align: center;
    	line-height: 48px;
    	font-size: 18px;
    	color: #fff;
	}
	.bd {
    	padding: 0 20px;
	}
	div.bd ul {
    	margin-bottom: 1rem;
    	margin-left:-39px;
	}
	.bd ul li {
    	padding: 5px 0;
    	border-bottom: 1px solid #ccc;
    	list-style:none;
	}
	
	.bd ul li h4 {
    	font-size: 16px;
    	color: #4e4e4e;
	}
	.bd ul li p {
    	font-size: 12px;
    	color: #a5a5a5;
	}
	.bd .more {
    	display: block;
    	height: 38px;
    	border: 1px solid #00a4ff;
    	margin-top: 5px;
   		text-align: center;
    	line-height: 38px;
    	color: #00a4ff;
    	font-size: 16px;
    	font-weight: 700;
	}
	.bd a:hover{
        background-color: skyblue;
        color:white;
        text-decoration:none;
    }
	
</style>
<script src="<%=request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>

</head>
<body>
		<div><%@ include file="/front-end/res/res_member_centre_header.jsp"%>
			 <!-- 候位資訊-->
<%--             <center><a>${sessionScope.resInfoVO.resid} / ${sessionScope.resInfoVO.resid}</a></center> --%>
            <div class="waitinginfo">
                <h2 id="openClose"></h2> <!-- 目前候位狀態 -->
                <div class="bd">
                    <ul>
                        <li>
                            <h4>目前叫號號碼</h4>
                            <p><a id="currentNumber"></a></p>
                        </li>
						<li>
                            <h4>目前號碼候位人數</h4>
                            <p><a id="currentSeat"></a></p>
                        </li>
                        <li>
                            <h4>下一組號碼候位人數</h4>
                            <p><a id="nextSeat"></a></p>
                        </li>
                        <li>
                            <h4>目前等候組數</h4>
                            <p><a id="waitingNumber"></p>
                        </li>
                    </ul>

                    <a class="more" id="addCurrentNumber">呼叫下一號</a>
                    <a class="more" id="delNumber">叫號歸零</a>
                    <a class="more" id="setOpenClose">開放 / 關閉候位</a>
                </div>
            </div>
   
		
          
	<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
<script>
	// 宣告 websocket 相關變數username = account
	var resid = '${sessionScope.resInfoVO.resid}';
	var userName = '${sessionScope.resInfoVO.resid}';// 資料庫有資料用resid存 問元穎這個是不是當key 
	var uri = "/WaitingWS/${resInfoVO.resid}/${sessionScope.resInfoVO.resid}"; // userName 從 LoginServlet 傳入
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + uri;
	var webSocket;
	var number;
	
	// websocket 事件處理函式
	function webSocketConnect() {
		// 建立 websocket 物件
		webSocket = new WebSocket(endPointURL);
		
		// 執行 websocket 連線
		webSocket.onopen = function(event) {
			console.log("Connect Success!");
		};
		
		// 接收 websocket 回傳的候位狀態
		webSocket.onmessage = function(event) {
			number = JSON.parse(event.data);
			console.log(number);
			
			var waiting = parseInt(number.total)-parseInt(number.current)
			$("#currentNumber").html(number.current + "號");
			$("#currentSeat").html(number.currentSeat + "人");
			$("#nextSeat").html(number.nextSeat + "人");
			$("#waitingNumber").html(waiting + "組");
			
			// 設定開放 / 不開放候位
			openClose = number.openClose;
			if (openClose === "open") $("#openClose").html("目前候位狀態");
			else $("#openClose").html("目前不開放候位");
		}
	}
	
	// 呼叫下一號 按鈕事件處理
	$("#addCurrentNumber").click(function() {
		if (parseInt(number.total) > parseInt(number.current)) {
			var event = {
				"resid": resid,
				"userName" : userName,
				"action" : "addCurrentNumber"
			};
			webSocket.send(JSON.stringify(event));
		} else {
			alert("沒有下一號了！");
		}
	});
	
	// 叫號歸零 按鈕事件處理
	$("#delNumber").click(function() {
		var event = {
			"resid": resid,
			"userName" : userName,
			"action" : "delNumber"
		};
		webSocket.send(JSON.stringify(event));
	});

	// 開放 / 關閉候位 按鈕事件處理
	$("#setOpenClose").click(function() {		
		var event = {
			"resid": resid,
			"userName" : userName,
			"action" : "setOpenClose"
		};
		webSocket.send(JSON.stringify(event));
	});
	
	// 等網頁的全部的內容（包括圖片、CSS、JS）載入後才會觸發
	window.onload = function() {
		webSocketConnect();
	};


</script>
</body>
</html>