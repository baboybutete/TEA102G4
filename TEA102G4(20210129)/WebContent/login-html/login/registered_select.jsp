<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<style>
    * {
    margin: 0;
    padding: 0;
}
.w {
    width: 1200px;
    margin: auto;
}
.q {
    width: 100%;
    margin: auto;
}

body {
    background-color: #f3f5f7;
}
li {
    list-style: none;
}
a {
    text-decoration: none;
}
.clearfix:before,.clearfix:after {
    content:"";
    display:table; 
  }
  .clearfix:after {
    clear:both;
  }
  .clearfix {
     *zoom:1;
  }   
 
/* .header {
    height: 42px;
    //層疊到 
    margin: 30px auto;
} */
.header {
    position: sticky;
    top:0;
    width: 100%;
    background-color: rgb(236, 233, 233);
    /* border-bottom: 1px solid rgb(255, 0, 0); */
    height: 60px;
    z-index: 10;
    
}
.log {
    height: 60px;
    width: 110px;
    margin-left: 20px;
    float: left;
}
.log img{
    margin-top: 1px;
    width: 100%;
    
}
main{
    margin:100px auto;
    
    width: 500px;
}
.wrapper{
    /* border: black solid 1px; */
    width: 500px;
    text-align: justfy;
    height: 200px;
    line-height:140px;
}
.wrapper a{
    color:black;
}
.box{
    /* border:orange solid 1px; */
    display: inline-block;
    width: 45%;
    height: 70%;
    margin: 30px 10px;
    text-align: center;
    /* line-height:70%; */
    font-size: 50px;
    font-weight: bold;
    box-shadow: 0 2px 3px 3px rgba(0,0,0, 0.1);
}
.box:hover{
    box-shadow: 0 0 0 0 rgba(0,0,0, 0);
    background-color: blanchedalmond;
    /* border: black solid 2px; */
}


span.text{
    display: inline-block;
    margin: 0 auto;
    border: palegreen 1px solid;
    width: 100px;
    vertical-align:middle;
    font-size: 20px;
    font-weight: bold;
    height: 100%;
}

</style>

<body>
    <div class="header">
		<!-- logo部分 -->
		<div class="log">
		<a href="<%=request.getContextPath()%>/index_plat.jsp"><img
				src="<%=request.getContextPath()%>/images/Asset 19.png"
				alt=""></a>
		</div>
	</div>
    <main>
        <div class="wrapper">
            <a href="<%= request.getContextPath()%>/front-end/cust/custinfo/custinfoadd.jsp">
                <span class="left-box box">
                    消費者
                </span>
            </a>
            <a href="<%= request.getContextPath()%>/front-end/res/resinfo/resinfoadd.jsp">
                <span class="right-box box">
                    餐廳
                </span>
            </a>
        </div>
    </main>
</body>
</html>