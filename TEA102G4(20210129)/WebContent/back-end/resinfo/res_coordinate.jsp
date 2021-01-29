<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>座位呢 - res_coordinate.jsp</title>
</head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/res_coordinate.css">

<style>
 	
  table.data{
  	width: 800px;
  	display: block;
  	overflow:scroll;
  	white-space: nowrap;
  }
  
  table, th, td {
    border: 1px solid black;
  }
  th, td {
    padding: 10px;
    text-align: center;
  }
  ul li {
  	list-style:none
  }
 
  
  div.result{
  	width: 800px;
  	float:right;
  	margin-right: 100px;
  	margin-top:20px;
  }
  
  div.search{
  	width:900px;
  	
  	text-align:left;
  	
  }
  
  div.search ul{
   display:inline-block;
 	margin:0 0;
 	width:100px;
 	padding:0 0;
  }
  div.content{
/*   	float:right; */
  	margin-top:10px;
/*   	margin-right:20px; */
  }
  #myCanvas{
  	margin-top:10px;
  	background-color: white;
  	margin-right: 100px;
  }
  
</style>

<body>
	<div>
		<%@ include file="/back-end/back_header.jsp" %>
		<!-- 顯示資料的區域 -->
		<div class="custinfo">
		  <div class="custinfo_title">
			<a>餐廳資訊管理</a>
			<img src="<%=request.getContextPath()%>/images/right-arrow.png">
			<a>查詢餐廳座位圖</a>
		  </div>
		  <div class="input_adid">
			<div class="content">
			<div class="search">
        			<b>輸入餐廳編號 (如R00001):</b>
        			<input id="resid" type="text" name="resid">
        			<button id="submit" type="button" value="送出">送出</button>
			<%-- 錯誤表列 --%>
				<span class="msg"></span>
			</div>
			<canvas  width="1000" height="440" style="border: 2px solid black" id="myCanvas"></canvas>
			</div>
		  </div>
	  </div>
	</div>
	<!-- footer -->
 	<%@ include file="/back-end/back_footer.jsp" %>
	<script src="<%=request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
	<script>
		
		$(window).on('load', function () {
			var borderOfCanvas = 1;
			var startX = 0;
			var startY = 0;
			var selectedE = -1; //moving element
			var focusIndexOfDataArray = -1;// assigned a number when mousedown trigger;used for delete element, 起始點同是dataArray;
			var moveDistanceX = 0;
			var moveDistanceY = 0;
			var countNumber = 1;
			var myCanvasPosition = $("#myCanvas").offset();

			/*----step1 拿到canvas環境，以下兩步必做---*/
			/*提示Canvas*/
			/** @type {HTMLCanvasElement} */
			var myCanvas = document.getElementById("myCanvas");
			var ctx = myCanvas.getContext('2d');
			var fixArray = [];//初始值定義可被拖和複製的物品
			var dataArray = [];//the data will be changed when moving the itmes. Store fixArray in the beginning and will be followed by seatArray after dragging items in optional place( or the index within the length of fixArray) dataArray = fixArray + seatArray
			var seatArray = [];
			var lightBorderColor = 'yellow'
			var chairwidth = 40;
			var chairlength = 40;

			var lineWidthOfHollowchair = 1;
			var chairwidthOfHollowchair = chairwidth
				- lineWidthOfHollowchair * 2;
			var chairlengthOfHollowchair = chairlength
				- lineWidthOfHollowchair * 2;

			/*fixArray :optional items. 
			the data that never change, which is used to setting any required information.
			x ,y is the origin point
			w , h are stand for width and height,individually */
			fixArray.push({/* table*/
				x: 10,
				y: 80,
				w: 150,
				h: 100,
				color: 'no',
				itemNumber: 0
			})
			// fixArray.push({/*unavailable chair*/
			//     x: 10,
			//     y: 190,
			//     w: 40,
			//     h: 40,
			//     color: 'black',
			//     itemNumber: 1
			// })
			// fixArray.push({/*used chair*/
			//     x: 10,
			//     y: 250,
			//     w: 40,
			//     h: 40,
			//     color: 'red',
			//     itemNumber: 2
			// })
			// fixArray.push({/*locked chair (by customer)*/
			//     x: 10,
			//     y: 310,
			//     w: 40,
			//     h: 40,
			//     color: 'yellow',
			//     itemNumber: 3
			// }
			// )
			fixArray.push({/*availble chair (中空的)*/
				x: 10,
				y: 370,
				w: 38,
				h: 38,
				color: 'no',
				itemNumber: 4
			})

			/*initial the dataArray by using fixArray*/
			intialDataArray();
			/*get data from server at the begining
			從oracle撈出來的資料*/
			var resid = "R00007";
//  			getPositionFromServer();
			draw();

			/*for checking data*/
			$("#check").on("click", function () {
				console.log(fixArray);
				console.log(seatArray);
				console.log(dataArray);

			})

			/*----------修改數字----------*/
			$keyInNumber = $("#keyInNumber");
			myCanvas.addEventListener("dblclick", function (
				event) {
				/*只有代表座位element的元素被點到時input才會出現*/
				if (focusIndexOfDataArray >= fixArray.length) {
					$keyInNumber.removeClass("hideIt");
					/*show input afer double click*/
					showInputTag(event);
				} else {
					$keyInNumber.addClass("hideIt");
				}
			})
			/*key in the seat number after  focus and enter event*/
			$keyInNumber.on("focus", function (event) {
				$keyInNumber.on("keydown", function (event) {
					changeNumber(event);
				})
			})
			$keyInNumber.on("blur", function () {
				$keyInNumber.addClass("hideIt");
				$keyInNumber.val('');
			})

			/*將input的座標放到滑鼠位置*/
			function showInputTag(event) {
				$keyInNumber.focus();
				console.log();
				$keyInNumber.offset({
					top: event.pageY,
					left: event.pageX
				})
			}

			/*change seat number*/
			function changeNumber(event) {
				/*13 is enter*/
				if (event.keyCode === 13) {
					/*Target index of seatArray. FocusIndexOfDataArray is assigned when mousedown event be triggered*/
					let renumberIndex = focusIndexOfDataArray
						- fixArray.length;
					seatArray[renumberIndex].seatNumber = $keyInNumber
						.val();//change number
					intialDataArray();
					//將input隱藏起來
					$keyInNumber.addClass("hideIt");
					draw();
				}
			}

			/*delete the element when hit enter and focusIndexOfDataArray is belong to seat */
			window.addEventListener("keydown", function (event) {
				deleteElement(event);
			})

			/*再把這個方法移到下面*/
			/*delete element*/
			function deleteElement(event) {
				/*46 represents for delete. Using event.code and event.keyCode for checking*/
				if (event.keyCode === 46
					&& focusIndexOfDataArray >= fixArray.length) {
					/*remove seatArray. 
					removeIndex is the index of dataArray. */
					let removeIndex = focusIndexOfDataArray
						- fixArray.length;
					/*remove the index of seatArray*/
					seatArray.splice(removeIndex, 1);
					intialDataArray();
					draw();
				}
			}

			/*滑鼠點下時，判斷是哪個元素被點擊，然後重新定義該元素的xy座標，再重新繪製整張畫布*/
			myCanvas.addEventListener("mousedown", function (
				event) {

// 				let hittedIndex = handleMouseDown(event);
				// lightBorder(hittedIndex);
			})
			myCanvas.addEventListener("mousemove", function (
				event) {
// 				handleMouseMove(event);
			})

			myCanvas.addEventListener("mouseup",
				function (event) {
// 					handleMouseUp(event);
				})

			/*add light border aroud the target element*/
			// function lightBorder(hittedIndex) {
			// 	if (hittedIndex >= fixArray.length) {
			// 		let focusEl = seatArray[hittedIndex];

			// 	}
			// }

			/*紀錄滑鼠點擊時的X Y 座標*/
			function handleMouseDown(event) {
				startX = event.pageX - myCanvasPosition.left;
				startY = event.pageY - myCanvasPosition.top;
				selectedE = getHittedElement(dataArray);
				/*複製選擇欄內的物件。
				只有點擊旁邊選擇欄時，才會複製物品並push到seat裡面。當連擊時會連續產生很多個物件*/
				draw();
				focusIndexOfDataArray = selectedE;

				if (selectedE >= 0
					&& selectedE < fixArray.length) {
					/*setting seat number */
					if (dataArray[selectedE].itemNumber > 0) {
						
						dataArray[selectedE].seatNumber = countNumber;

						countNumber++;
					} else {
						//桌子不給號碼
						dataArray[selectedE].seatNumber = -1;
					}

					seatArray.push(dataArray[selectedE]);

					return selectedE;
				}
				return selectedE;
			}

			/*trigger this function when moving element*/
			function handleMouseMove(event) {

				if (selectedE != -1) {
					rePosition();
				}
			}

			/*跑迴圈判斷是哪個element被點擊，並且回傳該element的index*/
			function getHittedElement(array) {
				for (let i = 0; i < array.length; i++) {
					let element = array[i];

					if ((element.x <= startX && startX <= element.x
						+ element.w)
						&& (element.y <= startY && startY <= element.y
							+ element.h)) {
						return i;
					}
				}
				return -1;
			}

			function handleMouseUp(event) {
				if (selectedE != -1) {
					/*initial dataArray
					將dataArray值恢復成原本的fix資料,將原本在該索引值的物件替換掉
					 */
					intialDataArray();

					let fixObj = fixArray[selectedE];
					selectedE = -1;//恢復-1 初始值，有沒有不影響

				}
			}

			/*計算被點擊的element要被移動到哪裡去*/
			function rePosition() {
				let movingE = dataArray[selectedE];

				/*get the moving distance of mouse*/
				moveDistanceX = event.pageX
					- myCanvasPosition.left - startX;
				moveDistanceY = event.pageY
					- myCanvasPosition.top - startY;
				startX = event.pageX - myCanvasPosition.left;//重新定義起始位置X
				startY = event.pageY - myCanvasPosition.top;//重新定義起始位置Y
				/*reset the position of x and y*/
				movingE.x = movingE.x + (moveDistanceX);
				movingE.y = movingE.y + (moveDistanceY);
				draw();
			}

			/*將dataArray 初始化 deep copy*/
			function intialDataArray() {
				dataArray = [];
				for (let i = 0; i < fixArray.length; i++) {
					let obj = {
						x: fixArray[i].x,
						y: fixArray[i].y,
						w: fixArray[i].w,
						h: fixArray[i].h,
						color: fixArray[i].color,
						itemNumber: fixArray[i].itemNumber
					}
					dataArray.push(obj);
				}
				dataArray = $.merge(dataArray, seatArray);
			}
			/*重新繪製canvas*/
			function draw() {

				/*清除畫布(x,y,width,heigth) */
				ctx.clearRect(0, 0, 1000, 700);

				
				let light = true;
				drawingSeat(seatArray, light);
			}

			function drawingSeat(array, light) {
				/*light是布林值，避免item列表在雙擊也被加上亮框,
				當light 是false時不會有亮框和編號*/
				for (let i = 0; i < array.length; i++) {
					let ele = array[i];
					/*給中空的圖案*/
					if (ele.color === "no") {
						ctx.beginPath();
						ctx.strokeStyle = "black";
						ctx.lineWidth = lineWidthOfHollowchair;
						ctx.rect(ele.x, ele.y, ele.w, ele.h);
						ctx.stroke();
						ctx.closePath();
						drawingSeatNumber(ele, light);

						if ((selectedE - fixArray.length) === i
							&& light) {
							ctx.beginPath();
							ctx.strokeStyle = lightBorderColor;
							ctx.lineWidth = 2;
							ctx.rect(ele.x, ele.y, ele.w, ele.h);
							ctx.stroke();
							ctx.closePath();
						}

					}
					/*給實心的圖案*/
					else {
						ctx.fillStyle = ele.color;
						ctx
							.fillRect(ele.x, ele.y, ele.w,
								ele.h);
						/*drawing number*/
						drawingSeatNumber(ele, light);
						/*亮框focus，當迴圈輪到點擊的元素時將其加上亮框*/
						if ((selectedE - fixArray.length) === i
							&& light) {
							ctx.beginPath();
							ctx.strokeStyle = lightBorderColor;
							ctx.lineWidth = 2;
							ctx
								.rect(ele.x, ele.y, ele.w,
									ele.h);
							ctx.stroke();
							ctx.closePath();
						}
					}
				}

			}
			function drawingSeatNumber(ele, light) {
				//不畫桌子號碼
				if(ele.seatNumber===-1)return;
				if (light) {
					ctx.font = "20px Arial";
					ctx.fillStyle = "orange";
					let relativeX = 10;
					let relativeY = 20;
					let number = ele.seatNumber;
					let x = ele.x + relativeX;
					let y = ele.y + relativeY;
					ctx.fillText(number, x, y);
				}

			}

			/*ajax getting position*/
			$("#submit").on("click",function(){
				resid = $("#resid").val();
				getPositionFromServer();
			})
			
			function getPositionFromServer() {
				var MyPoint = "/ResSeatServlet";
				var host = window.location.host;
				var path = window.location.pathname;
				var webCtx = path.substring(0, path.indexOf('/', 1));
				var endPointURL = "http://" + window.location.host + webCtx + MyPoint;
				let url = 'http://localhost:8081/Developing2/ResSeatServlet'
				let action = "getSeatDistribution";
				console.log(resid);
				
				$.ajax({
					url: endPointURL,
					type: "POST",
					data: {
						"action": action,
						"resid": resid
					},
					success: function (data) {
						console.log(data);
						/*assigne to dataArray (merge it and call drawing method)*/
						let msgObj = JSON.parse(data);
						seatArray=[];
						if(msgObj.status === "yes"){
							seatArray = JSON.parse(msgObj.seatData);
						}else if(msgObj.status === "1"){
							$("span.msg").html("此間餐廳未設定座位圖");
							
							//印出status
						}else if (msgObj.status === "2"){
							$("span.msg").html("請輸入餐廳編號或名稱");
						}else if(msgObj.status === "3"){
							$("span.msg").html("餐廳編號格式錯誤")
						}
						
						intialDataArray();
						//淺層複製 seatArray 內的物件和dataArry內的後半物件是相同的(same address in memory)
						draw();
					}
				})
			}
		});

	</script>
</body>
</html>