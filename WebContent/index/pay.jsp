<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>订单支付</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/iconfont.css">
	<link rel="stylesheet" href="css/global.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="css/swiper.min.css">
	<link rel="stylesheet" href="css/styles.css">
	<script src="js/jquery.min.js" charset="UTF-8"></script>
	<script src="js/bootstrap.min.js" charset="UTF-8"></script>
	<script src="js/swiper.min.js" charset="UTF-8"></script>
	<script src="js/global.js" charset="UTF-8"></script>
	<script src="js/alert.min.js" charset="UTF-8"></script>
	<script src="layer/layer.js" charset="UTF-8"></script>
</head>
<body>
	
	<jsp:include page="header.jsp"/>
	
	
	<div class="content clearfix bgf5">
		<section class="user-center inner clearfix">
			<div class="user-content__box clearfix bgf">
				<div class="title">订单确认 </div>
				
				<form action="pay" method="post" class="shopcart-form__box">
					<input name="id" type="hidden" value="${order.id}">
					 
					<div class="shop-title">收货地址</div>
					<div class="addr-radio user-addr__form form-horizontal">
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">姓名：</label>
							<div class="col-sm-6">
								<input class="form-control" name="name" value="${sessionScope.user.name}" placeholder="请输入姓名" type="text" required="required">
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">电话：</label>
							<div class="col-sm-6">
								<input class="form-control" name="phone" value="${sessionScope.user.phone}" placeholder="请输入电话" type="text" required="required">
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">地址：</label>
							<div class="col-sm-6">
								<input class="form-control" name="address" value="${sessionScope.user.address}" placeholder="请输入地址" type="text" required="required">
							</div>
						</div>
					</div>
					
					<div class="shop-title">订单详情</div>
					<div class="shop-order__detail">
						<table class="table">
							<thead>
								<tr>
									<th width="150">商品封面</th>
									<th width="300">商品信息</th>
									<th width="200">单价</th>
									<th width="200">数量</th>
									<th width="200">总价</th>
								</tr>
							</thead>
							<tbody>
							
								<c:forEach var="item" items="${order.itemList}">
									<tr>
										<th scope="row">
											<div class="img">
												<img src="${item.good.cover}" alt="${item.good.name}" class="cover">
											</div>
										</th>
										<td>
											<div class="name ep3">${item.good.name}</div>
											<div class="type c9">${item.color.name} ${item.size.name}</div>
										</td>
										<td>${item.price}</td>
										<td>${item.amount}</td>
										<td>¥${item.price * item.amount}</td>
									</tr>
								</c:forEach>
								
							</tbody>
						</table>
					</div>
				
					<div class="shop-title">支付方式</div>
					<div class="pay-mode__box">
						<div class="radio-line radio-box active">
							<label class="radio-label ep">
								<input name="paytype" value="1" autocomplete="off" type="radio" checked="checked"><i class="iconfont icon-radio"></i>
								<img src="images/icons/wechat.gif" alt="微信支付">
							</label>
							<div class="pay-value">支付<b class="fz16 cr" id="total3"> ${order.total} </b>元</div>
						</div>
						<div class="radio-line radio-box">
							<label class="radio-label ep">
								<input name="paytype" value="2" autocomplete="off" type="radio"><i class="iconfont icon-radio"></i>
								<img src="images/icons/alipay.gif" alt="支付宝支付">
							</label>
							<div class="pay-value">支付<b class="fz16 cr" id="total2"> ${order.total} </b>元</div>
						</div>
					</div>
					<div class="user-form-group shopcart-submit">
						<button type="submit" class="btn">继续支付</button>	
					</div>

				</form>
			</div>
		</section>
	</div>

	
	<jsp:include page="footer.jsp"/>
	
	<script>
		$(document).ready(function(){
			if("${msg}"!=""){
				layer.alert("${msg}", function(){
					location.href="order";
				});
			}
			
			// 选择支付方式
			$(this).on('change','input',function() {
				$(this).parents('.radio-box').addClass('active').siblings().removeClass('active');
			})
		});
	</script>
	
</body>
</html>