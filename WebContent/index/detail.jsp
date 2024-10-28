<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>商城首页</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/iconfont.css">
	<link rel="stylesheet" href="css/global.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="css/swiper.min.css">
	<link rel="stylesheet" href="css/styles.css">
	<script src="js/jquery.min.js" charset="UTF-8"></script>
	<script src="js/jquery.magnifier.js" charset="UTF-8"></script>
	<script src="js/bootstrap.min.js" charset="UTF-8"></script>
	<script src="js/swiper.min.js" charset="UTF-8"></script>
	<script src="js/global.js" charset="UTF-8"></script>
	<script src="js/alert.min.js" charset="UTF-8"></script>
	<script src="js/loadimage.js" charset="UTF-8"></script>
	<script src="js/lcshopz.js" charset="UTF-8"></script>
	<script src="js/goods.js" charset="UTF-8"></script>
	<script src="layer/layer.js" charset="UTF-8"></script>
</head>
<body>
	
	<jsp:include page="header.jsp"/>
	
	
	<div class="content inner">
	
		<section class="item-show__div item-show__head clearfix">

			<div class="pull-left">

				<ol class="breadcrumb">
					<li><a href="index">首页</a></li>
					<li class="active"><a href="goods?typeid=${type.id}">${type.name}</a></li>
				</ol>

				<div class="item-pic__box" id="magnifier">
					<div class="small-box">
						<img class="cover" src="${good.cover}" alt="${good.name}">
						<span class="hover"></span>
					</div>
					<!-- 商品缩略图, 先不做
					<div class="thumbnail-box">
						<a href="javascript:;" class="btn btn-default btn-prev"></a>
						<div class="thumb-list">
							<ul class="wrapper clearfix">
								<li class="item active" data-src="${good.cover}"><img class="cover" src="${good.cover}" alt="商品预览图"></li>
								<li class="item" data-src="${good.cover}"><img class="cover" src="${good.cover}" alt="商品预览图"></li>
								<li class="item" data-src="${good.cover}"><img class="cover" src="${good.cover}" alt="商品预览图"></li>
							</ul>
						</div>
						<a href="javascript:;" class="btn btn-default btn-next"></a>
					</div> -->

					<!-- 图片放大 -->
					<div class="big-box">
						<img src="${good.cover}" alt="${good.name}">
					</div>
					<script>$(function () {$('#magnifier').magnifier();});</script>

				</div>

				<div class="item-info__box" style="position: relative;">
					<div class="item-title">
						<div class="name ep2">${good.name}</div>
						<div class="sale cr"></div>
					</div>

					<div class="item-price bgf5">
						<div class="price-box clearfix">
							<div class="price-panel pull-left">
								售价：<span class="price" id="price">￥${good.price} <!-- 原价<s class="fz16 c9">￥1.00</s>--></span>
							</div>
						</div>
					</div>

					<style>.select a{ font-weight: bold;border: 1px solid #b31e22;}</style>

					<div class="item-key">

						<div class="item-sku">
							<dl class="item-prop clearfix">
								<dt class="item-metatit">颜色：</dt>
								<dd>
									<ul data-property="颜色" class="clearfix" id="sku_color">
									
										<c:forEach var="color" items="${colorList}">
											<li data="${color.id}">
												<a href="javascript:;" role="button" data-value="${color.name}" aria-disabled="true">
													<span>${color.name}</span>
												</a>
											</li>
										</c:forEach>
									
									</ul>
								</dd>
							</dl>
						
							<dl class="item-prop clearfix">
								<dt class="item-metatit">尺寸：</dt>
								<dd>
									<ul data-property="尺寸" class="clearfix" id="sku_size">
									
										<c:forEach var="size" items="${sizeList}">
											<li data="${size.id}" >
												<a href="javascript:;" role="button" data-value="${size.name}" aria-disabled="true">
													<span>${size.name}</span>
												</a>
											</li>
										</c:forEach>
										
									</ul>
								</dd>
							</dl>
						</div>
						<script type="text/javascript">
						$(function () {
							// 默认选中第一个
							$("#sku_color").children().first().attr("class", "select");
							$("#sku_size").children().first().attr("class", "select");
							// 记录选择的属性
							var goodid = ${good.id};
							var colorid = $("#sku_color").children().first().attr("data");
							var sizeid = $("#sku_size").children().first().attr("data");
							reloadStock(goodid, colorid, sizeid); // 初始化库存
							// 选择事件
							$("#sku_color").children().on("click", function(){
								$(this).siblings().removeAttr("class");
								$(this).attr("class", "select");
								colorid = $(this).attr("data");
								reloadStock(goodid, colorid, sizeid);
							});
							$("#sku_size").children().on("click", function(){
								$(this).siblings().removeAttr("class");
								$(this).attr("class", "select");
								sizeid = $(this).attr("data");
								reloadStock(goodid, colorid, sizeid);
							});
							
							// 刷新库存
							function reloadStock(goodid, colorid, sizeid){
								$.get("stock", {goodid:goodid, colorid:colorid, sizeid:sizeid}, function(data){
									$("#stock").text(data);
								}, "json");
							}
							
						});	
						</script>
						
						<div class="item-amount clearfix bgf5">
							<div class="item-metatit">数量：</div>
							<div class="amount-box">
								<div class="amount-widget">
									<input class="amount-input" name="goods_num" id="goods_num" value="1" maxlength="8" title="请输入购买量" type="text">
									<div class="amount-btn">
										<a class="amount-but add"></a>
										<a class="amount-but sub"></a>
									</div>
								</div>
								<div class="item-stock">
									<span style="margin-left: 10px;">库存 <b id="stock">0</b> 件</span> 
									<!-- <a href="" >&nbsp;收藏商品&nbsp;</a> -->
								</div>
								<script>
									$(function () {
										// 数量输入检测
										$('.amount-input').onlyReg({reg: /[^0-9]/g});
										$('.amount-widget').on('click','.amount-but',function() {
											var stock = parseInt($('#stock').html());
											var num = parseInt($('.amount-input').val());
											if (!num) num = 0;
											if ($(this).hasClass('add')) {
												if (num > stock - 1){
													layer.msg("您输入的数量超过库存上限");
												}
												$('.amount-input').val(num + 1);
											} else if ($(this).hasClass('sub')) {
												if (num == 1){
													layer.msg("您输入的数量有误");
												}
												$('.amount-input').val(num - 1);
											}
										});
									});
								</script>

							</div>

						</div>

						<style>
				            #shopcar_popup{width: 300px; display:block; float:left;height: 130px;border:3px #e43a3d solid;display:none;background:#fff url(images/tuc_03.jpg) 10px center no-repeat ; line-height:30px; font-size:14px; font-family:"微软雅黑"; margin-left:75px; padding-left:63px; padding-bottom:10px; padding-right:10px; padding-top:10px; position:absolute;margin-top: 30px; z-index: 100; top: 120px;left: 250px;}
				            #shopcar_popup span{ cursor:pointer; color:#e43a3d; font-weight:bold}
				            .attrlink td{ min-height:38px;line-height:38px}
						</style>
						<span id="shopcar_popup" style="display: none;">弹出层</span>
						<div class="item-action clearfix bgf5">
							<a href="javascript:;" id="now_buy" data-addfastbuy="true" role="button" class="item-action__buy">立即购买</a>
							<a href="javascript:;" id="add_shopcart" rel="nofollow" data-addfastbuy="true" role="button" class="item-action__basket">
								<i class="iconfont icon-shopcart"></i> 加入购物车
							</a>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
			$("#now_buy").on("click", function(){
				// 验证
				var num = parseInt($('#goods_num').val());
				if(num<=0){
					layer.msg("请正确的输入数量");
					return false;
				}
				var stock = parseInt($('#stock').html());
				if(num > stock){
					layer.msg("库存不足");
					return false;
				}
				// 请求
				var goodid = ${good.id};
				var colorid = $("#sku_color").children(".select").attr("data");
				var sizeid = $("#sku_size").children(".select").attr("data");
				$.post("buy", {goodId:goodid, colorId:colorid, sizeId:sizeid, amount:num}, function(data){
					if(data > 0){
						location.href="topay?orderid="+data;
					}else if(data==-111){
						layer.msg("请先登录");
					}else{
						layer.msg("出错啦")
					}
				})
			});
			
			$("#add_shopcart").on("click", function(){
				// 验证
				var num = parseInt($('#goods_num').val());
				if(num<=0){
					layer.msg("请正确的输入数量");
					return false;
				}
				var stock = parseInt($('#stock').html());
				if(num > stock){
					layer.msg("库存不足");
					return false;
				}
				// 请求
				var goodid = ${good.id};
				var colorid = $("#sku_color").children(".select").attr("data");
				var sizeid = $("#sku_size").children(".select").attr("data");
				$.post("cart", {goodId:goodid, colorId:colorid, sizeId:sizeid, amount:num}, function(data){
					if(data > 0){
						$("#shopcar_popup").html("已成功加入购物车！<br>购物车中有<span>&nbsp;"+data+"&nbsp;</span>件商品<br><span onclick='window.open(\"shopcart\",\"_self\")'>去购物车结算</span>&nbsp;&nbsp;<span onclick=\"javascript:fadeOut('shopcar_popup')\">继续购物</span>"); 
						$("#shopcar_popup").css("display",'block');
					}else if(data==-111){
						layer.msg("请先登录");
					}else{
						layer.msg("出错啦")
					}
				});
			});
			</script>
			

			<div class="pull-right picked-div">
				<div class="lace-title">
					<span class="c6">爆款推荐</span>
				</div>

				<div class="swiper-container picked-swiper">
					<div class="swiper-wrapper">
					
						<div class="swiper-slide">
							<c:forEach var="top" items="${topList}" varStatus="status">
								<a href="detail?goodid=${top.good.id}" class="picked-item">
									<img src="${top.good.cover}" alt="${top.good.name}" class="cover">
									<span class="look_price">¥${top.good.price}</span>
									<!-- 三个一组 -->
									<c:if test="${status.count%3==0}">
										</div><div class="swiper-slide">
									</c:if>
								</a>
							</c:forEach>
						</div>
						
					</div>
				</div>
				<div class="picked-button-prev"></div>
				<div class="picked-button-next"></div>
				<script>
					$(document).ready(function(){ 
						var picked_swiper = new Swiper('.picked-swiper', {
							loop : true,
							direction: 'vertical',
							prevButton:'.picked-button-prev',
							nextButton:'.picked-button-next',
						});
					});
				</script>

			</div>
		</section>


		<!-- 下部分 -->
		<section class="item-show__div item-show__body posr clearfix">

			<div class="item-nav-tabs">
				<ul class="nav-tabs nav-pills clearfix" role="tablist" id="item-tabs">
					<li role="presentation" class="active"><a href="#detail" role="tab" data-toggle="tab" aria-controls="detail" aria-expanded="true">商品详情</a></li>
					<!-- <li role="presentation"><a href="#evaluate" role="tab" data-toggle="tab" aria-controls="evaluate">累计评价 <span class="badge">0</span></a></li> -->
					<!-- <li role="presentation"><a href="#service" role="tab" data-toggle="tab" aria-controls="service">售后服务</a></li> -->
				</ul>
			</div>

			<div class="pull-left">
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active" id="detail" aria-labelledby="detail-tab">
						<div class="item-detail__info clearfix" id="desc-module-1">
							<div class="record">商品编号：${good.id}</div>
							<!-- <div class="record">上架时间：</div>
							<div class="record">商品毛重：</div>
							<div class="record">商品库存：</div> -->
						</div>

						<div class="rich-text" id="desc-module-2">
							<p style="text-align:center;">
								${good.intro}
							<!-- 
								<img src="http://t.shuaishou.com/Get/290827578" /><br />
								<img src="http://t.shuaishou.com/Get/290827599" /><br />
								<img src="http://t.shuaishou.com/Get/290827617" /><br />
								<img src="http://t.shuaishou.com/Get/290827635" /><br />
								<img src="http://t.shuaishou.com/Get/290827653" /><br />
								<img src="http://t.shuaishou.com/Get/290827671" /><br />
								<img src="http://t.shuaishou.com/Get/290827690" /><br />
								<img src="http://t.shuaishou.com/Get/290827707" /><br />
								<img src="http://t.shuaishou.com/Get/290827725" /><br />
								<img src="http://t.shuaishou.com/Get/290827737" /><br />
								<img src="http://t.shuaishou.com/Get/290827755" /><br />
								<img src="http://t.shuaishou.com/Get/290827771" /><br />
								<img src="http://t.shuaishou.com/Get/290827788" /><br />
								<img src="http://t.shuaishou.com/Get/290827804" /><br />
								<img src="http://t.shuaishou.com/Get/290827821" /><br />
								<img src="http://t.shuaishou.com/Get/290827838" />  -->
							</p>
						</div>

					</div>

			    </div>

			</div>

			<div class="pull-right">
				<div class="tab-content" id="descCate">
					<div role="tabpanel" class="tab-pane fade in active" id="detail-tab" aria-labelledby="detail-tab">
						<div class="descCate-content bgf5">
							<dd class="dc-idsItem selected">
								<a href="#desc-module-1"><i class="iconfont icon-dot"></i> 规格参数</a>
							</dd>
							<dd class="dc-idsItem">
								<a href="#desc-module-2"><i class="iconfont icon-selected"></i> 产品介绍</a>
							</dd>
						</div>
					</div>
				</div>
			</div>

			<script>
				$(document).ready(function(){
					$('#descCate').smartFloat(0);
					$('.dc-idsItem').click(function() {
						$(this).addClass('selected').siblings().removeClass('selected');
					});
					$('#item-tabs a[data-toggle="tab"]').on('show.bs.tab', function (e) {
						$('#descCate #' + $(e.target).attr('aria-controls') + '-tab')
						.addClass('in').addClass('active').siblings()
						.removeClass('in').removeClass('active');
					});
				});
			</script>

		</section>
		
	</div>
	
	
	<jsp:include page="footer.jsp"/>

</body>
</html>