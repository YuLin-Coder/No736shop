<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>购物车</title>
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
				<div class="title">购物车</div>
				<form id="form1" method="post" action="save" class="shopcart-form__box">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th width="150">
									<!-- <label class="checked-label"><input type="checkbox" id ='checkall' onclick="cart_chk_all($(this).attr('checked'),2)" value="" checked="CHECKED"><i></i> 全选</label>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="DelShopCartAll()">删除</a> -->
								</th>
								<th width="300">商品信息</th>
								<th width="150">单价</th>
								<th width="200">数量</th>
								<th width="200">总价</th>
								<th width="80">操作</th>
							</tr>
						</thead>
						<tbody>
						
							<c:forEach var="shopcart" items="${shopcartList}">
								
								<tr id="tr_${shopcart.id}" class="gwc_actual_z">
									<th scope="row">
										<label class="checked-label">
											<!-- <input name="id[]" id="id0" value="768" checked="CHECKED" onclick="cart_chk_one(0)" type="checkbox"><i></i> -->
											<a href="detail?goodid=${shopcart.good.id}"><img src="${shopcart.good.cover}" alt="" class="cover"></a>
										</label>
									</th>
									<td>
										<div class="name ep3">${shopcart.good.name}</div>
										<div class="type c9"> ${shopcart.color.name} ${shopcart.size.name}</div>
									</td>
									<td>¥<span id="price_${shopcart.id}">${shopcart.good.price}</span></td>
									<td>
										<div class="cart-num__box">
											<input class="sub" value="-" onclick="less(${shopcart.id})" type="button">
											<input class="val" id="amount_${shopcart.id}" value="${shopcart.amount}" type="text" readonly="readonly" onkeyup="this.value=this.value.replace(/\D/g,'')">
											<input class="add" value="+" onclick="add(${shopcart.id})" type="button">
										</div>
									</td>
									<td>¥<span id="total_${shopcart.id}">${shopcart.good.price * shopcart.amount}</span></td>
									<td><a href="javascript:;" onclick="del(${shopcart.id}, this);">删除</a></td>
								</tr>
							
							</c:forEach>
							
						</tbody>
					</table>
					<div class="user-form-group tags-box shopcart-submit pull-right">
						<c:if test="${totalPrice > 0}">
							<button type="submit" class="btn" id="jiesuan">提交订单</button>
						</c:if>
					</div>
					<div class="checkbox shopcart-total">
						<!-- &nbsp;&nbsp;<label style="font-weight:bold;"><input type="checkbox" id ='checkall' onclick="cart_chk_all($(this).attr('checked'),2)" value="" checked="CHECKED"><i></i> 全选</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="DelShopCartAll()">删除</a> -->
						<div class="pull-right">
							<!-- 已选商品 <span id="totalnum">2</span> 件 -->
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合计 <b class="cr">¥ <span class="fz24" id="totalprice">${totalPrice}</span></b>
						</div>
					</div>
				</form>
			</div>
		</section>
	</div>
	
	
	<jsp:include page="footer.jsp"/>
	
	<script type="text/javascript">
		function add(skuid){
			$.post("add", {skuid:skuid}, function(data){
				if(data){
					var amount = parseInt($('#amount_'+skuid).val()) + 1;
					$('#amount_'+skuid).val(amount);
					var price = parseInt($('#price_'+skuid).text());
					$('#total_'+skuid).text(price * amount);
					reloadTotal(); // 更新总金额
				}else{
					layer.msg("出错啦!");
				}
			});
		}
		function less(skuid){
			$.post("less", {skuid:skuid}, function(data){
				if(data){
					var amount = parseInt($('#amount_'+skuid).val()) - 1;
					if(amount <= 0){ // 数量减没了就删掉
						del(skuid);
						return;
					}
					$('#amount_'+skuid).val(amount);
					var price = parseInt($('#price_'+skuid).text());
					$('#total_'+skuid).text(price * amount);
					reloadTotal(); // 更新总金额
				}else{
					layer.msg("出错啦!");
				}
			});
		}
		function del(skuid){
			$.post("delete", {skuid:skuid}, function(data){
				if(data){
					$('#tr_'+skuid).fadeOut(500, function(){
						$('#tr_'+skuid).remove()
					});
					reloadTotal(); // 更新总金额
				}else{
					layer.msg("出错啦!");
				}
			});
		}
		
		// 刷新总金额
		function reloadTotal(){
			$.get("total", function(data){
				if(data>=0){
					$("#totalprice").text(data);
					if(data==0){ // 购物车无物品时, 隐藏提交按钮
						$("#jiesuan").fadeOut(500);
					}
				}else{
					layer.msg("出错啦!");
				}
			});
		}
		
		$(function(){
			if("${msg}"!=""){
				layer.alert("${msg}");
			}
		});
	</script>

</body>
</html>