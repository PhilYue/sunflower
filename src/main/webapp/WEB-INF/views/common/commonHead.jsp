<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<style>
.demo-long {
	margin-top: 200px;
	margin-bottom: 400px;
}

.form-box {
	margin-top: 35px;
}

.form-top {
	overflow: hidden;
	padding: 0 25px 15px 25px;
	background: #fff;
	-moz-border-radius: 4px 4px 0 0;
	-webkit-border-radius: 4px 4px 0 0;
	border-radius: 4px 4px 0 0;
	text-align: left;
}

.form-bottom {
	padding: 25px 25px 30px 25px;
	background: #eee;
	-moz-border-radius: 0 0 4px 4px;
	-webkit-border-radius: 0 0 4px 4px;
	border-radius: 0 0 4px 4px;
	text-align: left;
}

.form-group {
	margin-bottom: 15px;
}

.form-bottom form button.btn-login {
	width: 100%;
}

button.btn-login {
	height: 50px;
	margin: 0;
	padding: 0 20px;
	vertical-align: middle;
	background: #4aaf51;
	border: 0;
	font-family: 'Roboto', sans-serif;
	font-size: 16px;
	font-weight: 300;
	line-height: 50px;
	color: #fff;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	text-shadow: none;
	-moz-box-shadow: none;
	-webkit-box-shadow: none;
	box-shadow: none;
	-o-transition: all .3s;
	-moz-transition: all .3s;
	-webkit-transition: all .3s;
	-ms-transition: all .3s;
	transition: all .3s;
}

button.btn-login:focus {
	outline: 0;
	opacity: 0.6;
	background: #4aaf51;
	color: #fff;
}

button.btn-login:hover {
	opacity: 0.6;
	color: #fff;
}

.form-bottom form .input-error {
	border-color: #4aaf51;
}

input[type="password"], textarea, textarea.form-control {
	margin: 0;
	vertical-align: middle;
	color: #888;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	-moz-box-shadow: none;
	-webkit-box-shadow: none;
	box-shadow: none;
	-o-transition: all .3s;
	-moz-transition: all .3s;
	-webkit-transition: all .3s;
	-ms-transition: all .3s;
	transition: all .3s;
}
</style>
<%@ include file="commonHeadInclude.jsp"%>
<body>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container" style="margin-left: -14px; width: 100%;">
			<div class="navbar-header">
				<img src="/res/images/logo-new.png" /> <img alt="海玩logo"
					src="/res/images/slogan8.png">
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a class="navbar-brand" href="javascript:;"></a>
					<li>
				</ul>
				<ul class="nav navbar-nav">
					<li>
						<div class="input-group" style="margin-top: 10px; width: 249px;">
							<span class="startTime" placeholder="出行日期"></span> <input
								type="hidden" placeholder="出行日期" name=startTime />
							<div class="input-group-btn">
								<button class="btn btn-default" id="searchOrder"
									style="padding: 9px 12px;" type="submit">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</div>
						</div>
					</li>
				</ul>
				<!-- 查询自己的订单 -->
				<!-- <ul class="nav navbar-nav">
          	<li>
	          	<div class="input-group" style="width: 250px;margin-top: 10px;">
				  <input type="text" class="form-control enter-key-search" placeholder="Recipient's username" aria-describedby="basic-addon2">
				  <span class="input-group-addon" id="mail-do">@haiwan.com</span>
				</div>
			</li>
          </ul> -->
				<ul class="nav navbar-nav navbar-right">
					<li><a class="user-name" href="javascript:;"
						user-name="<sec:authentication property="name" />"><sec:authentication
								property="name" /></a></li>
					<!-- 暂时取消登录操作,打开即可用 -->
					<li class="dropdown"><a href="javascript:;"
						class="dropdown-toggle" data-toggle="dropdown">Operate <b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="/logout">Logout</a></li>
							<li><a href="javascript:;" class="login_cs_system">Login</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</div>
	<script>
		//登录  
		$("ul li .login_cs_system").click(function() {
			$('#gridSystemModalLabel').modal('show')
		})
		$("#all-count").click(function() {

		})
	</script>
	<div id="gridSystemModalLabel" class="modal fade" role="dialog"
		aria-labelledby="gridSystemModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="col-sm-6 col-sm-offset-3 form-box">
					<div class="form-top">
						<div class="form-top-left">
							<h3>Login to CS system</h3>
							<p>Enter your username and password to log on:</p>
						</div>
						<div class="form-top-right">
							<i class="fa fa-key"></i>
						</div>
					</div>
					<div class="form-bottom">
						<form role="form"
							action="${pageContext.request.contextPath}/j_spring_security_check"
							method="post" class="login-form">
							<div class="form-group">
								<label class="sr-only" for="form-username">Username</label> <input
									type="text" name="j_username" placeholder="Username..."
									class="form-username form-control" id="j_username">
							</div>
							<div class="form-group">
								<label class="sr-only" for="form-password">Password</label> <input
									type="password" name="j_password" placeholder="Password..."
									class="form-password form-control" id="j_password">
							</div>
							<button type="button" class="btn-login">Sign in!</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		/** 初始化标题栏 */
		$("div.navbar-fixed-top").autoHidingNavbar();
		/** 初始化日期下拉控件  */
		$(".startTime").birthdaypicker();
		/** 登录 */
		$("#gridSystemModalLabel .btn-login").click(function() {
			var checkFlag = checkLogin();
			if (checkFlag == 1) {
				$("#taskGrid").datagrid("fetch");
				//校验成功
				$("#gridSystemModalLabel .login-form").submit();
			} else if (checkFlag == 2) {
				//需要二次验证
				alert("需要二次验证");
			} else {
				//用户名或密码错误
				alert("用户名或密码错误");
			}
		});
		function checkLogin() {
			var flag;
			$.ajax({
				type : "POST",
				url : '/user/checkLogin?d=' + Math.random(),
				data : {
					account : $("#j_username").val(),
					password : $("#j_password").val()
				},
				dataType : 'json',
				async : false,
				success : function(json) {
					if (json.code == 200) {
						flag = 1;
					} else if (json.code == 202) {
						flag = 2;
					} else {
						flag = 0;
					}
				}
			});
			return flag;
		}
		//背景闪动提示
		function normal(id, times) {
			var obj = $("#" + id);
			obj.css("background-color", "#FFF");
			if (times < 0) {
				return;
			}
			times = times - 1;
			setTimeout("error('" + id + "'," + times + ")", 150);
		}
		function error(id, times) {
			var obj = $("#" + id);
			obj.css("background-color", "#F6CECE");
			times = times - 1;
			setTimeout("normal('" + id + "'," + times + ")", 150);
		}
	</script>