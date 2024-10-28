<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>商城首页</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/iconfont.css">
	<link rel="stylesheet" href="css/global.css">
	<link rel="stylesheet" href="css/bootstrap.css">
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
		<c:forEach var="data" items="${dataList}" varStatus="status">
		
			<section class="scroll-floor floor-${status.count}">
				<div class="floor-title" style="color:#666;">
					<i class="iconfont icon-fushi fz16"></i> ${data.type.name}
					<a href="" class="more"><i class="iconfont icon-more"></i></a>
				</div>
				<div class="con-box">
					<a class="left-img hot-img" href="goods?typeid=${data.type.id}">
						<img src="${data.type.cover}" alt="" class="cover">
					</a>
					<div class="right-box">
					
						<c:forEach var="good" items="${data.goodList}">
						
							<a href="detail?goodid=${good.id}" class="floor-item">
								<div class="item-img hot-img">
									<img src="${good.cover}" alt="${good.name}" class="cover">
								</div>
								<div class="price clearfix">
									<span class="pull-left cr fz16">￥${good.price}</span>
									<!-- <span class="pull-right c6">进货价</span> -->
								</div>
								<div class="name ep" title="${good.name}">${good.name}</div>
							</a>
							
						</c:forEach>
						
					</div>
				</div>
			</section>
		
		</c:forEach>
		
	</div>
	
	
	<jsp:include page="footer.jsp"/>

</body>
</html>