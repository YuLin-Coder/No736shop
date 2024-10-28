package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Goods;
import com.entity.Tops;
import com.entity.Types;
import com.service.GoodService;
import com.service.SkuService;
import com.service.TopService;
import com.service.TypeService;
import com.util.PageUtil;

/**
 * 前台相关接口
 */
@Controller
@RequestMapping("/index")
public class IndexController{
	
	private static final int rows = 8; // 默认每页数量

	@Autowired
	private TopService topService;
	@Autowired
	private GoodService goodService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private SkuService skuService;
	

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		request.setAttribute("flag", -1);
		List<Types> typeList = typeService.getList();
		request.setAttribute("typeList", typeList);
		List<Map<String, Object>> dataList = new ArrayList<>();
		for(Types type : typeList) {
			Map<String, Object> map = new HashMap<>();
			map.put("type", type);
			map.put("goodList", goodService.getListByType(type.getId(), 1, 8)); // 取前8个
			dataList.add(map);
		}
		request.setAttribute("dataList", dataList);
		return "/index/index.jsp";
	}
	
	/**
	 * 推荐列表
	 * @return
	 */
	@RequestMapping("/top")
	public String tops(int typeid, @RequestParam(required=false, defaultValue="1")int page, HttpServletRequest request) {
		request.setAttribute("flag", typeid==2 ? 7 : 8);
		request.setAttribute("typeList", typeService.getList());
		request.setAttribute("goodList", goodService.getList(typeid, page, rows));
		request.setAttribute("pageHtml", PageUtil.getPageHtml(request, goodService.getTotal(typeid), page, rows));
		return "/index/goods.jsp";
	}
	
	/**
	 * 商品列表
	 * @return
	 */
	@RequestMapping("/goods")
	public String goods(int typeid, @RequestParam(required=false, defaultValue="1")int page, HttpServletRequest request){
		request.setAttribute("flag", typeid);
		request.setAttribute("typeList", typeService.getList());
		request.setAttribute("topList", topService.getList(Tops.TYPE_SUPPER, 1, 4));
		if (typeid > 0) {
			request.setAttribute("type", typeService.get(typeid));
		}
		request.setAttribute("goodList", goodService.getListByType(typeid, page, rows));
		request.setAttribute("pageHtml", PageUtil.getPageHtml(request, goodService.getTotalByType(typeid), page, rows));
		return "/index/goods.jsp";
	}
	
	/**
	 * 商品详情
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(int goodid, HttpServletRequest request){
		request.setAttribute("typeList", typeService.getList());
		request.setAttribute("topList", topService.getList(Tops.TYPE_SUPPER, 1, 4));
		Goods good = goodService.get(goodid);
		request.setAttribute("good", good);
		request.setAttribute("type", typeService.get(good.getTypeId()));
		request.setAttribute("colorList", skuService.getColorList(goodid));
		request.setAttribute("sizeList", skuService.getSizeList(goodid));
		return "/index/detail.jsp";
	}
	
	/**
	 * 搜索
	 * @return
	 */
	@RequestMapping("/search")
	public String search(String name, @RequestParam(required=false, defaultValue="1")int page, HttpServletRequest request) {
		if (Objects.nonNull(name) && !name.trim().isEmpty()) {
			request.setAttribute("goodList", goodService.getListByName(name, page, rows));
			request.setAttribute("pageHtml", PageUtil.getPageHtml(request, goodService.getTotalByName(name), page, rows));
		}
		request.setAttribute("typeList", typeService.getList());
		return "/index/goods.jsp";
	}
	
	/**
	 * 查询库存
	 * @param goodid
	 * @param colorid
	 * @param sizeid
	 * @return
	 */
	@GetMapping("/stock")
	public @ResponseBody int stock(int goodid, int colorid, int sizeid) {
		return skuService.getStock(goodid, colorid, sizeid);
	}

}