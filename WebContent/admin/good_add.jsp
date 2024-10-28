<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>商品添加</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="css/bootstrap.css" />
</head>
<body>
<div class="container">
	<%@include file="header.jsp"%>

	<br><br>
	<form class="form-horizontal" action="goodSave" method="post" enctype="multipart/form-data" onsubmit="return check()">
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">名称</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="name" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">价格</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="price" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">介绍</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="intro" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_file" class="col-sm-1 control-label">封面</label> 
			<div class="col-sm-6">
				<input type="file" name="file"  id="input_file" required="required">推荐尺寸: 500 * 500
			</div>
		</div>
		<div class="form-group">
			<label for="select_topic" class="col-sm-1 control-label">类目</label>
			<div class="col-sm-6">
				<select class="form-control" id="select_topic" name="typeId">
					<c:forEach var="type" items="${typeList}">
						<option value="${type.id}">${type.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<hr>
		
		<div class="form-group">
			<label for="select_topic" class="col-sm-1 control-label">属性</label>
			<div class="col-sm-2">
				<select class="form-control" id="select_color">
					<c:forEach var="color" items="${colorList}">
						<option value="${color.id}">${color.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-2">
				<select class="form-control" id="select_size">
					<c:forEach var="size" items="${sizeList}">
						<option value="${size.id}">${size.name}</option>
					</c:forEach>
				</select>
			</div>
			<a class="btn btn-primary" id="sku_add">添加属性</a>
			
			<br><br><br>
			<div class="col-sm-offset-1 col-sm-6">
				<table class="table table-bordered table-hover" id="table_sku">
					<tr>
						<th width="10%">颜色</th>
						<th width="10%">尺寸</th>
						<th width="10%">库存</th>
						<th width="5%">操作</th>
					</tr>
				</table>
			</div>
		</div>
		
		<hr>
		
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="btn btn-success">提交保存</button>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
var index = 0;
$(function(){
	
	// 添加sku
	$("#sku_add").on("click", function(){
		var colorId = $("#select_color").val();
		var sizeId = $("#select_size").val();
		var color = $("#select_color").find("option:selected").text();
		var size = $("#select_size").find("option:selected").text();
		// 判断是否重复
		var isHave = false;
		$("#table_sku").find("tr").each(function(n, v){
			if($(v).find(".color").val()==colorId && $(v).find(".size").val()==sizeId){
				isHave = true;
				return;
			}
		});
		if(isHave){
			alert("属性重复了");
			return;
		}
		// 添加
		var html = '<tr>' 
			+ '<td><p>' + color + '</p></td>'
			+ '<td><p>' + size + '</p></td>'
			+ '<input type="hidden" name="skuList['+index+'].colorId" value="'+colorId+'" class="color">'
			+ '<input type="hidden" name="skuList['+index+'].sizeId" value="'+sizeId+'" class="size">'
			+ '<td><input type="text" name="skuList['+index+'].stock" required="required" placeholder="请输入库存"></td>'
			+ '<td><a class="btn btn-danger sku_delete">删除</a></td>';
		$("#table_sku").append(html);
		index ++;
	});
	
	// 删除sku
	$(document).on("click", ".sku_delete", function(){
		$(this).parents("tr").remove();
	});
		
});

// 表单提交前检测
function check(){
	if($("#table_sku").find("tr").size() <= 1){
		alert("必须添加属性和库存信息");
		return false;
	}
}
</script>
</body>
</html>