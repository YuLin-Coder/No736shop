<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<title>我的订单</title>
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
			<div class="pull-left bgf">
				<!-- <a href="" class="title">欢迎页</a> -->
				<dl class="user-center__nav">
					<dt>个人中心</dt>
					<a href="my"><dd <c:if test="${flag==11}">class="active"</c:if>>我的信息</dd></a>
				</dl>
				<dl class="user-center__nav">
					<dt>订单中心</dt>
					<a href="order"><dd <c:if test="${flag==12}">class="active"</c:if>>我的订单</dd></a>
				</dl>
			</div>
			
			<div class="pull-right">
				<div class="user-content__box clearfix bgf">
					<div class="title">我的订单</div>
					<div class="order-list__box bgf">
						<div class="order-panel">
							<ul class="nav user-nav__title" role="tablist">
								<li class="nav-item active"><a href="order">所有订单</a></li>
								<!-- 
								<li class="nav-item "><a href="">待付款 <span class="cr">0</span></a></li>
								<li class="nav-item "><a href="">待发货 <span class="cr">0</span></a></li>
								<li class="nav-item "><a href="">待收货 <span class="cr">0</span></a></li>
								<li class="nav-item "><a href="">待评价 <span class="cr">0</span></a></li>
								 -->
							</ul>

							<div class="tab-content">
								<div role="tabpanel" class="tab-pane fade in active" id="all">
									<table class="table text-center">
										<tbody>
											<c:if test="${orderList==null}">
												<tr class="order-empty">
													<td colspan="6">
														<div class="empty-msg">最近没有任何订单，家里好像缺了点什么！<br><a href="index">要不瞧瞧去？</a></div>
													</td>
												</tr>
											</c:if>
											<c:if test="${orderList!=null}">
												<tr>
													<th width="550">商品信息</th>
													<th width="120">订单金额</th>
													<th width="120">交易状态</th>
													<th width="120">交易操作</th>
												</tr>
												<c:forEach var="order" items="${orderList}">
													<tr class="order-item" id="tr319">
														<td>
															<label>
																<span>订单号: ${order.id}</span>
																<c:forEach var="item" items="${order.itemList}">
																	<div class="card">
																		<div class="img"><a href="detail?goodid=${item.good.id}"><img src="${item.good.cover}" alt="" class="cover"></a></div>
																		<div class="name ep2"><a href="detail?goodid=${item.good.id}">${item.good.name}</a></div>
																		<div class="format">${item.color.name} ${item.size.name}</div>
																		<div class="favour">￥${item.price}  x  ${item.amount}件</div>
																	</div>
																</c:forEach>
																<span>下单时间: <fmt:formatDate value="${order.systime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
															</label>
														</td>
														<td>￥${order.total}</td>
														<td class="state">
															<c:if test="${order.status==1}">未付款</c:if>
															<c:if test="${order.status==2}">待发货</c:if>
															<c:if test="${order.status==3}">已发货</c:if>
															<c:if test="${order.status==4}">已完成</c:if>
														</td>
														<td class="order">
															<c:if test="${order.status==1}">
																<a href="topay?orderid=${order.id}" class="but but-primary">立即付款</a>
															</c:if>
															<!-- <a href="javascript:void(0);" onclick="if(confirm( '确定取消订单? ')==true)return DelShopOrder(319);" class="but c3">取消订单</a> -->
														</td>
													</tr>
												</c:forEach>
											</c:if>
											
										</tbody>
									</table>
									<!-- 
									<div class="page text-right clearfix" style="margin-top: 40px">
										<span class="pro_link">共0页0条记录</span>
									</div> -->
								</div>
							</div>
						</div>
					</div>
				
				</div>
			</div>
		</section>
	</div>

</body>
</html>