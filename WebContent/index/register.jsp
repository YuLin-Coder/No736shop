<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>用户注册</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/iconfont.css">
	<link rel="stylesheet" href="css/global.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="css/login.css">
	<script src="js/jquery.min.js" charset="UTF-8"></script>
	<script src="js/bootstrap.min.js" charset="UTF-8"></script>
	<script src="js/jquery.form.js" charset="UTF-8"></script>
	<script src="js/global.js" charset="UTF-8"></script>
	<script src="js/login.js" charset="UTF-8"></script>
</head>
<body>

	<div class="public-head-layout container">
		<a class="logo" href="index"><img src="images/icons/logo.jpg" alt="" class="cover"></a>
	</div>
	
	<div style="background:url(images/login_bg.jpg) no-repeat center center; ">
		<div class="login-layout container">
			<div class="form-box register">
  				<div class="tabs-nav">
  					<h2>用户注册</h2>
  				</div>
  				<div class="tabs_container">
					<form class="tabs_form" method="post" action="register" id="register_form">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">
									<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
								</div>
								<input class="form-control phone" name="username" id="register_phone" required placeholder="请输入用户名" autocomplete="off" type="text">
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">
									<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
								</div>
								<input class="form-control password" name="password" id="register_pwd" placeholder="请输入密码" autocomplete="off" type="password">
								<div class="input-group-addon pwd-toggle" title="显示密码"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></div>
							</div>
						</div>
	                    <button class="btn btn-large btn-primary btn-lg btn-block submit" id="register_submit" data-loading-text="注册中..." type="button">注册</button><br>
	                    <!-- 错误信息 -->
						<div class="form-group">
							<div class="error_msg" id="register_error"></div>
						</div>
	                    <p class="text-center">已有帐号! <a href="login">马上登录</a></p>
                    </form>
                </div>
			</div>

			<script>
				$(document).ready(function() {
					// 后台信息
					if("${msg}"!=""){
						$("#register_error").html(msgtemp('<strong>${msg}</strong>', 'alert-danger'));
					}
					// 以下确定按钮仅供参考
					$('.submit').click(function() {
						var that = this;
						var form = $(this).parents('form')
						var phone = form.find('input.phone');
						var pwd = form.find('input.password');
						var error = form.find('.error_msg');
						// 验证用户名参考这个
						switch(phone.validatemobile()) {
							case 1: error.html(msgtemp('<strong>用户名为空</strong> 请输入用户名',    'alert-warning')); return; break;
						}
						// 验证密码复杂度参考这个
						switch(pwd.validatepwd()) {
							case 1: error.html(msgtemp('<strong>密码不能为空</strong> 请输入密码',    'alert-warning')); return; break;
						}
						error.empty();
						form.submit();
						return false;
					});
				});
			</script>
		</div>
	</div>
	
</body>
</html>