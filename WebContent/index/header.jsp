<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
    <link rel="stylesheet" href="css/bootstrap.css" />
    <link rel="stylesheet" href="css/nav.css" />
    <link rel="stylesheet" href="iconfont/iconfont.css">
    <!-- <script src="js/jquery.min.js"></script> -->
    <!-- <script src="js/bootstrap.js"></script> -->
    <script src="js/zshop.js"></script>
</head>
<body>
	<div class="navbar navbar-default clear-bottom">
	<div class="container">
         <div class="navbar-header">
             <a class="navbar-brand logo-style">
                 <img class="brand-img" src="images/shop.png" alt="logo" height="65">
             </a>
         </div>
         <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
             <ul class="nav navbar-nav">
                 <li class="active">
                     <a href="index" style="background: #ff7300;">在线商城</a>
                 </li>
                 <c:if test="${sessionScope.user!=null}">
                 <li>
                     <a href="order">我的订单</a>
                 </li>
                 <li>
                     <a href="shopcart">购物车</a>
                 </li>
                 <li class="dropdown">
                     <a href="my">会员中心</a>
                 </li>
                 </c:if>
             </ul>
             <ul class="nav navbar-nav navbar-right">
            		 <c:if test="${sessionScope.user==null}">
	                 <li>
	                     <a href="#" data-toggle="modal" data-target="#loginModal">登陆</a>
	                 </li>
	                 <li>
	                     <a href="#" data-toggle="modal" data-target="#registModal">注册</a>
	                 </li>
	             </c:if>
	             <c:if test="${sessionScope.user!=null}">    
	                 <li class="userName">
	                     您好：${user.username}
	                 </li>
	                 <li class="dropdown">
	                     <a href="#" class="dropdown-toggle user-active" data-toggle="dropdown" role="button">
	                         <img class="img-circle" src="images/user.jpeg" height="30" />
	                         <span class="caret"></span>
	                     </a>
	                     <ul class="dropdown-menu">
	                         <li>
	                             <a href="#" data-toggle="modal" data-target="#modifyPasswordModal">
	                                 <i class="glyphicon glyphicon-cog"></i>修改密码
	                             </a>
	                         </li>
	                         <li>
	                             <a href="logout">
	                                 <i class="glyphicon glyphicon-off"></i> 退出
	                             </a>
	                         </li>
	                     </ul>
	                 </li>
                 </c:if>
             </ul>
         </div>
     </div>
	</div>
	
	<!-- 首页导航栏 -->
	<div class="top-nav" style="width: 1200px;margin: 0 auto;background: #ff7300;">
		<div class="nav-box inner">
			<ul class="nva-list">
				<a href="index"><li <c:if test="${flag==-1}">class="active"</c:if> style="border-right: 1px solid #fff;">首页</li></a>
				<c:forEach var="type" items="${typeList}">
					<a href="goods?typeid=${type.id}"><li <c:if test="${flag==type.id}">class="active"</c:if> style="border-right: 1px solid #fff;">${type.name}</li></a>
				</c:forEach>
			</ul>
			<ul class="nva-list" style="float:right;">	
			</ul>
		</div>
	</div>

	 <!-- 修改密码模态框 start -->
    <div class="modal fade" id="modifyPasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改密码</h4>
                </div>
                <form action="updatePassword" class="form-horizontal" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">原密码：</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="password" name="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">新密码：</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="password" name="passwordNew">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">重复密码：</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="password" name="repassword">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="text-align:center;">
                        <button type="submit" class="btn btn-warning">修&nbsp;&nbsp;改</button>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- 修改密码模态框 end -->

    <!-- 登录模态框 start  -->
    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <!-- 用户名密码登陆 start -->
            <div class="modal-content" id="login-account">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">用户登录</h4>
                </div>
                <form action="login" class="form-horizontal" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户名：</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="text" placeholder="请输入用户名" name="username">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="password" placeholder="请输入密码" name="password">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="text-align: center">
                        <button type="submit" class="btn btn-warning">登&nbsp;&nbsp;陆</button> &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    </div>
                </form>
            </div>
            <!-- 用户名密码登陆 end -->
            <!-- 短信快捷登陆 start -->
            <div class="modal-content" id="login-sms" style="display: none;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">短信快捷登陆</h4>
                </div>
                <form class="form-horizontal" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">手机号：</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="text" placeholder="请输入手机号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">验证码：</label>
                            <div class="col-sm-4">
                                <input class="form-control" type="text" placeholder="请输入验证码">
                            </div>
                            <div class="col-sm-2">
                                <button class="pass-item-timer" type="button">发送验证码</button>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="text-align: center">
                        <a class="btn-link">忘记密码？</a> &nbsp;
                        <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                        <button type="submit" class="btn btn-warning">登&nbsp;&nbsp;陆</button> &nbsp;&nbsp;
                        <a class="btn-link" id="btn-account-back">用户名密码登录</a>
                    </div>
                </form>
            </div>
            <!-- 短信快捷登陆 end -->
        </div>
    </div>
    <!-- 登录模态框 end  -->

    <!-- 注册模态框 start -->
    <div class="modal fade" id="registModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">用户注册</h4>
                </div>
                <form action="register" class="form-horizontal" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户账号:</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="text" name="username">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户密码:</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="password" name="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">确认密码:</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="password">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="text-align: center">
                        <button type="submit" class="btn btn-warning">注&nbsp;&nbsp;册</button>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- 注册模态框 end -->
</body>
</html>