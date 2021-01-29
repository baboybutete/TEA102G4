<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
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