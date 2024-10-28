// JavaScript Document
/*
window.onload=function(){


	var color = new Select("color",{
		Radio :true	,	//是否为单选,默认为true,如果设置为true,Default,Max选项将失效
		OnClick:function(selected){
			document.form.color.value = selected.join(",")
		}
		
	});
	
	var size = new Select("size",{
		OnClick:function(selected){
			document.form.size.value = selected.join(",")
		}
	});
	
	var other = new Select("other",{
		Radio:false,
		Max:5,
		Default:"袜子,扣子",
		OnClick:function(selected){
			document.form.other.value = selected.join(",")
		}
	});
		
}
*/

function Select(id,config){
	this.config = config||{};
	this.id = typeof(id)=='string'?document.getElementById(id):id;
	this.items = this.id.getElementsByTagName("li");
	this.selectClass = "select";
	this.selected = new Array();
	var _this = this;
	this.selectOpt = function(value,opt){
		var exist = false;
		for(var i=0;i<_this.selected.length;i++){
			if(_this.selected[i]==value){
				exist = true ;
				if(opt=="remove"){ _this.selected.splice(i,1);}
				break;
			}
		}
		if(!exist && opt=="add"){_this.selected.push(value);}
	};
	//初始化对象
	(function(_this){
		//是否有默认配置的选择项
		if(_this.config.Default){
			var arr = _this.config.Default.split(",");
			for(var i=0;i<arr.length;i++)_this.selectOpt(arr[i],"add");
		}
		for(var i=0;i<_this.items.length;i++){
			//将defalut中配置的项加上样式
			if( _this.selected.join(",").indexOf(_this.items[i].getAttribute('dataValue'))>-1 && _this.items[i].className==""){
				_this.items[i].className=_this.selectClass;
			}
			//如果有样式中定义了默认
			if(_this.items[i].className==_this.selectClass){
				_this.selectOpt(_this.items[i].getAttribute('dataValue'),"add");
			}
			//加点单击事件
			_this.items[i].onclick=function(){
				//是否为单选
				var radio = _this.config.Raido?_this.config.Raido:true;
				if(_this.config.Radio==null || _this.config.Radio){
					if(this.className!=_this.selectClass){
						var items = this.parentNode.getElementsByTagName("li");
						for(var i=0;i<items.length;i++){
							items[i].className="";
						}
						_this.selected.length = 0;
						_this.selected.push(this.getAttribute('dataValue'));
						this.className=_this.selectClass;
					}
				}else{
					var Max = _this.config.Max?_this.config.Max:1;
					if(this.className==_this.selectClass){
						this.className="";
						_this.selectOpt(this.getAttribute('dataValue'),"remove");
					}else{
						if(_this.selected.length>=Max){
							alert("最多只能选择"+Max+"项");
						}else{
							this.className=_this.selectClass;
							_this.selectOpt(this.getAttribute('dataValue'),"add");
						}
					}
				}
				//去掉那个虚线框
				//this.firstChild.blur();
				//调用回调函数
				if(_this.config.OnClick)_this.config.OnClick.call(this,_this.selected);
				return false;
			}
		}
	})(_this);	
}


/* 获取不同规格商品价格 */
function getprice(id,img)
{
	 img=(typeof(img)=="undefined")?'pc':img; 
	var norm_str;  
	var names =document.getElementsByName("goods_norm");  
	var t = true;
	for(i=0;i<names.length;i++){  
		if(i==0){
			norm_str = names[i].value;
		}
		else
		{
			norm_str += ','+names[i].value;
		}
		if(names[i].value=='')
		{
			t = false;
		}
	} 
	if(t)
	{
		$("#goodsnum").html("");
		$.ajax({
			url : "item_show.php?act=selectprice&id="+id+"&norm="+norm_str+"&default="+Math.random(),
			type:'get',
			dataType:'json',
			success:function(data){
				if(data!=0)
				{
					$("#price").html("￥"+data.price);
					$("#goods_price").val(data.salesprice);
					$("#show_housenum").val(data.housenum);
					$(".show_housenum").html(data.housenum);
					if($("#goods_num").val())
					{
						var $goods_num = parseInt($("#goods_num").val());
						var data_housenum = parseInt((data.housenum));
						if($goods_num>data_housenum)
						{
							$("#goods_num").val(data.housenum);
						}
					}else
					{
						$("#goods_num").val(1);
					}
					if(img!='phone')
					{
						
						document.getElementById("gwc").style.display="";
						document.getElementById("NowBuy").style.display="";
						document.getElementById("col").style.display="";
						document.getElementById("dh").style.display="";
					}else
					{
						
						document.getElementById("gwc").style.display="";
						document.getElementById("NowBuy").style.display="";
					}
				}
				else
				{
					
					var value='此商品已售罄';
					$("#price").html(value);
					$("#goods_price").val(value);
					$("#show_housenum").val(0);
					$(".show_housenum").html(0);
					document.getElementById("gwc").style.display="none";
					document.getElementById("NowBuy").style.display="none";
					document.getElementById("col").style.display="none";
					document.getElementById("dh").style.display="none";
				}
			}
		});
	}
}