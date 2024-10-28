<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>商品列表</title>
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
</head>
<body>
	
	<jsp:include page="header.jsp"/>
	
	
	<div class="content inner">
	
		<section class="filter-section clearfix">
		
			<ol class="breadcrumb">
				<li><a href="index">首页</a></li>
				<li class="active"><a href="goods?typeid=${type.id}">${type.name}</a></li>
			</ol>
			
			
			<div class="sort-box bgf5"><!-- 
				<div class="sort-text">排序：</div>
				<a href=""><div class="sort-text">销量 <i class="iconfont icon-sortUp"></i></div></a>
				<a href=""><div class="sort-text">评价 <i class="iconfont icon-sortUp"></i></div></a>
				<a href=""><div class="sort-text">价格 <i class="iconfont icon-sortUp"></i></div></a> -->
				<!-- <div class="sort-total pull-right">共673个商品</div> -->
			</div>
		</section>
		
		<section class="item-show__div clearfix">
			<div class="pull-left">
				<div class="item-list__area clearfix">
					
					<c:forEach var="good" items="${goodList}">
					
						<div class="item-card">
							<a href="detail?goodid=${good.id}" class="photo">
								<img src="${good.cover}" alt="${good.name}" class="cover">
								<div class="name">${good.name}</div>
							</a>
							<div class="middle">
								<div class="price"><small>￥</small>${good.price}</div>
								<div class="sale"><a href="detail?goodid=${good.id}">查看详情</a></div>
							</div>
							<div class="buttom">
								<!-- <div>销量 <b></b></div> -->
								<!-- <div>人气 <b>175</b></div> -->
								<div></div>
								<!-- <div>评论 <b>0</b></div> -->
							</div>
						</div>
						
					</c:forEach>
					
				</div>
				
				<!-- 分页 -->
				${pageHtml}
				
			</div>
			
			
			<div class="pull-right">
				
				<div class="desc-segments__content">
					<div class="lace-title">
						<span class="c6">爆款推荐</span>
					</div>
					<div class="picked-box">
					
						<c:forEach var="top" items="${topList}">
							<a href="detail?goodid=${top.good.id}" class="picked-item">
								<img src="${top.good.cover}" alt="${top.good.name}" class="cover">
								<span class="look_price">¥${top.good.price}</span>
							</a>
						</c:forEach>
						
					</div>
				</div>
			</div>
		</section>
		
	</div>
	
	
	<jsp:include page="footer.jsp"/>

</body>
</html>