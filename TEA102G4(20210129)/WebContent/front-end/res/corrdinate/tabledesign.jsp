<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>餐廳座位配置</title>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/res_member_centre.css">
<style>
	div.wrapper_canvas{
		position:absolute;
		top:120px;
		left:250px;
		height:600px;
	}
	#myCanvas{
		background-color:white;
		margin-top:40px;
	}
	input {
		border: 1px solid black;
		position: absolute;
		left: 420px;
		top: 130px;
		width: 30px;
	}

	input.hideIt {
		display: none;
	}
	div.canvas_button{
		position:absolute;
		top:0;
		right:0;
	}
		
	
</style>
</head>
<body>

	<%@ include file="../res_member_centre_header.jsp" %>
	
	<%-- canvas --%>
	<div class="wrapper_canvas">
		<canvas width="1000" height="440" style="border: 1px solid black" id="myCanvas"></canvas>
		<input id="keyInNumber" class="hideIt" type="text">
		<div class="canvas_button">
			<button id="save" type="button">save</button>
			<button id="clearAll" type="button">clear</button>
		</div>
		
	</div>
	

	<%@ include file="../res_member_centre_footer.jsp" %>



<script src="<%=request.getContextPath()%>/js/jquery-3.5.1.js"></script>
<%-- 改用 page include? --%>
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
			console.log("測試");
			console.log($("#myCanvas").width());
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
				y: 90,
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
				y: 250,
				w: 38,
				h: 38,
				color: 'no',
				itemNumber: 4
			})

			/*initial the dataArray by using fixArray*/
			intialDataArray();
			/*get data from server at the begining
			從oracle撈出來的資料*/
			var resid = "${resInfoVO.resid}";//使用EL
			console.log("el :"+resid);
 			getPositionFromServer();
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

				let hittedIndex = handleMouseDown(event);
				// lightBorder(hittedIndex);
			})
			myCanvas.addEventListener("mousemove", function (
				event) {
				handleMouseMove(event);
			})

			myCanvas.addEventListener("mouseup",
				function (event) {
					handleMouseUp(event);
				})

			/*add light border aroud the target element*/
			// function lightBorder(hittedIndex) {
			// 	if (hittedIndex >= fixArray.length) {
			// 		let focusEl = seatArray[hittedIndex];

			// 	}
			// }

			/*紀錄滑鼠點擊時的X Y 座標*/
			function handleMouseDown(event) {
				startX = event.pageX - $("#myCanvas").offset().left;
				startY = event.pageY - $("#myCanvas").offset().top;
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
					- $("#myCanvas").offset().left - startX;
				moveDistanceY = event.pageY
					- $("#myCanvas").offset().top - startY;
				startX = event.pageX - $("#myCanvas").offset().left;//重新定義起始位置X
				startY = event.pageY - $("#myCanvas").offset().top;//重新定義起始位置Y
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
				ctx.clearRect(0, 0, 1000, 1000);

				// drawing dividing line
				ctx.beginPath();
				ctx.strokeStyle = 'rgb(0,0,0)';// 線的顏色
				ctx.lineWidth = 1;//線的粗細
				ctx.moveTo(200, 0);//起點
				ctx.lineTo(200, 700);//第二點
				ctx.stroke();// 執行
				ctx.closePath();
				
				/*drawing optional items*/
				let light = false;
				drawingSeat(fixArray, light);

				ctx.beginPath();
				ctx.font = "20px Arial";
				ctx.fillStyle = "black";
// 				ctx.fillText("Drag them", 10, 50);
				// ctx.fillText("Drop here", 220, 50);
				ctx.fillText("桌子",10,80);
				ctx.fillText("椅子",10,240);
				ctx.closePath();
				// drawing grid
				drawingGrid();

				/*drawing seats*/
				light = true;
				drawingSeat(seatArray, light);
			}
			
			function drawingGrid(){
				//200 開始450結束
				ctx.beginPath();
				ctx.lineWidth = 1;//線的粗細
				ctx.strokeStyle="rgb(192,192,192)";
				//直線
				for(let i = 240; i < myCanvas.width;i +=40){
					ctx.moveTo(i,0);
					ctx.lineTo(i,1000);
					// ctx.stroke();放這裡顏色會越畫越淡
				}
				// 橫線
				for(let j = 40; j < myCanvas.height; j +=40){
					ctx.moveTo(200,j);
					ctx.lineTo(1000,j);
				}
				ctx.stroke();//
				ctx.closePath();
			}
			

			function drawingSeat(array, light) {
				/*light是布林值，避免item列表在雙擊也被加上亮框,
				當light是false時不會有亮框和編號*/
				for (let i = 0; i < array.length; i++) {
					let ele = array[i];
					/*給中空的圖案*/
					if (ele.color === "no") {
						//線
						ctx.closePath();
						ctx.beginPath();
						ctx.strokeStyle = "rgb(0,0,0)";
						ctx.fillStyle="rgb(255,255,255)";
						ctx.lineWidth = lineWidthOfHollowchair;
						ctx.rect(ele.x, ele.y, ele.w, ele.h);

						ctx.stroke();
						ctx.fill();
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
						ctx.fillRect(ele.x, ele.y, ele.w,ele.h);
						/*drawing number*/
						drawingSeatNumber(ele, light);
						/*亮框focus，當迴圈輪到點擊的元素時將其加上亮框*/
						if ((selectedE - fixArray.length) === i
							&& light) {
							ctx.beginPath();
							ctx.strokeStyle = lightBorderColor;
							ctx.lineWidth = 2;
							ctx.rect(ele.x, ele.y, ele.w,ele.h);
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

			function getPositionFromServer() {
				var MyPoint = "/ResSeatServlet";
				var host = window.location.host;
				var path = window.location.pathname;
				var webCtx = path.substring(0, path.indexOf('/', 1));
				var endPointURL = "http://" + window.location.host + webCtx + MyPoint;
				//let url = 'http://localhost:8081/Developing2/ResSeatServlet'
				let action = "getSeatDistribution";
				console.log(endPointURL);
				$.ajax({
					url: endPointURL,
					type: "POST",
					data: {
						"action": action,
						"resid": resid
					},
					success: function (data) {
						
						/*assigne to dataArray (merge it and call drawing method)*/
						let msgObj = JSON.parse(data);
						seatArray=[];
						if(msgObj.status === "yes"){
							console.log("get資料處理前");
							console.log(msgObj.seatData);
							console.log(seatArray);
							seatArray = JSON.parse(msgObj.seatData);//number
							coveringToNumber(seatArray);
							console.log("get資料處理後");
							console.log(msgObj);
							console.log(seatArray);
							
							
							
						}else if(msgObj.status === "1"){
							console.log("此間餐廳未設定座位圖");
							
							//印出status
						}else if (msgObj.status === "2"){
							console.log("請輸入餐廳編號或名稱");
						}else if(msgObj.status === "3"){
							console.log("餐廳編號格式錯誤")
						}
						
						intialDataArray();
						//淺層複製 seatArray 內的物件和dataArry內的後半物件是相同的(same address in memory)
						draw();
					}
				})
			}

			/*ajax sending to save*/
			/*using parameter to recognize user is a replacement for now. Session in server will be the formal way.
			按下save後狀態seat都會被刪除*/
			$("#save").on("click", function () {
// 				let areYouSure = confirm("確定存取變更?");
				swal({
					  title: "儲存變更",
					  text: "確定存取變更?",
					  icon: "info",
					  buttons: true,
					  dangerMode: true,
					})
					.then((willDelete) => {
					  if (willDelete) {
						  var time;
						    var host = window.location.host;
						    var path = window.location.pathname;
						    var MyPoint = "/ResSeatServlet" //餐廳和消費者改成EL:userid,從request拿
						    var webCtx = path.substring(0, path.indexOf('/', 1));
						    var endPointURL = "http://" + host + webCtx + MyPoint;
						    console.log("endPoint"+endPointURL);
							
							/*turn array into JSON string */
							converingToString(seatArray);
							console.log("seatArray save"+seatArray);//存進去變字串
							let seatString = JSON.stringify(seatArray);
							let action = "saveResSeatDistribution";
							let url = endPointURL;
							$.ajax({
								url: url,
								type: "POST",
								data: {
									"action": action,
									"seatData": seatString,
									"resid": resid
								},
								success: function (data) {
									/*後端增加擋掉空陣列以後再弄*/
									console.log("拿到存檔完後的資料了");
									
									/*assigne to dataArray (merge it and call drawing method)*/
									console.log(data);
									
									seatArray=[];
									
									seatArray = JSON.parse(data);
									coveringToNumber(seatArray);
									console.log("轉成number後");
									console.log(seatArray);
									
									intialDataArray();
									//淺層複製 seatArray 內的物件和dataArry內的後半物件是相同的(same address in memory)
									draw();
									swal("已存檔");
								}

							})
						  
						  
						  
					  } else {
		 			    swal("取消變更");
					  }
					});
				
			
			})
			
			  function coveringToNumber(seatArray) {
            	for (let i = 0; i < seatArray.length; i++) {
                    let obj = seatArray[i];
                    
                    obj.x = Number(obj.x);
                    obj.y = Number(obj.y);
                    obj.w = Number(obj.w);
                    obj.h = Number(obj.h);
                    obj.itemNumber = Number(obj.itemNumber);
                  
                    let number = Number(obj.seatNumber);
                    
                    if(! isNaN(number)){
                    	obj.seatNumber = number;
                    }
                }
            }
			
			function converingToString(seatArray){
				for (let i = 0; i < seatArray.length; i++) {
                    let obj = seatArray[i];
                    obj.x = obj.x.toString();
                    obj.y = obj.y.toString();
                    obj.w = obj.w.toString();
                    obj.h = obj.h.toString();
                    obj.itemNumber = obj.itemNumber.toString();
                    obj.seatNumber = obj.seatNumber.toString();
                    
//                     if(! isNaN(number)){
//                     	obj.seatNumber = number;
//                     }
                }
				console.log(seatArray);
			}

			$("#clearAll").on("click", function () {
				swal({
					  title: "清除所有物件",
					  text: "你真的想要清除全部嗎?",
					  icon: "warning",
					  buttons: true,
					  dangerMode: true,
					})
					.then((willDelete) => {
					  if (willDelete) {
						  clearAll();
						  var time;
						    var host = window.location.host;
						    var path = window.location.pathname;
						    var MyPoint = "/ResSeatServlet" //餐廳和消費者改成EL:userid,從request拿
						    var webCtx = path.substring(0, path.indexOf('/', 1));
						    var endPointURL = "http://" + host + webCtx + MyPoint;
						    console.log("endPoint"+endPointURL);
							
							/*turn array into JSON string */
							converingToString(seatArray);
							console.log("seatArray save"+seatArray);//存進去變字串
							let seatString = JSON.stringify(seatArray);
							let action = "saveResSeatDistribution";
							let url = endPointURL;
							$.ajax({
								url: url,
								type: "POST",
								data: {
									"action": action,
									"seatData": seatString,
									"resid": resid
								},
								success: function (data) {
									/*後端增加擋掉空陣列以後再弄*/
									console.log("拿到存檔完後的資料了");
									
									/*assigne to dataArray (merge it and call drawing method)*/
									console.log(data);
									
									seatArray=[];
									
									seatArray = JSON.parse(data);
									coveringToNumber(seatArray);
									console.log("轉成number後");
									console.log(seatArray);
									
									intialDataArray();
									//淺層複製 seatArray 內的物件和dataArry內的後半物件是相同的(same address in memory)
									draw();
									swal("刪除成功");
								}

							})
						  
					  } else {
		 			    swal("取消刪除");
					  }
					});
			})
			
			function clearAll() {
			    	seatArray = [];
					intialDataArray();
					draw();
					countNumber = 1;
			}

		});
	</script>
</body>
</html>