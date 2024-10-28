package com.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Orders;
import com.entity.Shopcart;
import com.entity.Users;
import com.service.GoodService;
import com.service.OrderService;
import com.service.ShopcartService;
import com.service.SkuService;
import com.service.TypeService;
import com.service.UserService;
import com.util.SafeUtil;

/**
 * 用户相关接口
 */
@Controller
@RequestMapping("/index")
public class UserController{
	
	@Resource
	private UserService userService;
	@Resource
	private OrderService orderService;
	@Resource
	private GoodService goodService;
	@Resource
	private TypeService typeService;
	@Resource
	private ShopcartService shopcartService;
	@Resource
	private SkuService skuService;

	
	/**
	 * 用户注册
	 * @return
	 */
	@GetMapping("/register")
	public String reg(Model model) {
		model.addAttribute("flag", -1); // 注册页面
		return "/index/register.jsp";
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@PostMapping("/register")
	public String register(Users user, Model model){
		if (user.getUsername().isEmpty()) {
			model.addAttribute("msg", "用户名不能为空!");
			return "/index/register.jsp";
		}else if (userService.isExist(user.getUsername())) {
			model.addAttribute("msg", "用户名已存在!");
			return "/index/register.jsp";
		}else {
			String password = user.getPassword();
			userService.add(user);
			user.setPassword(password);
			return "/index/index"; // 注册成功后转去登录
		}
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	@GetMapping("/login")
	public String log() {
		return "/index/index";
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	@PostMapping("/login")
	public String login(@RequestParam(required=false, defaultValue="0")int flag, Users user, HttpSession session, Model model) {
		model.addAttribute("typeList", typeService.getList());
		if(flag==-1) {
			flag = 6; // 登录页面
			return "/index/index";
		}
		if(userService.checkUser(user.getUsername(), user.getPassword())){
			Users loginUser = userService.get(user.getUsername());
			session.setAttribute("user", loginUser);
			// 还原购物车数量
			session.setAttribute("total", shopcartService.getTotal(loginUser.getId()));
			return "redirect:index";
		} else {
			model.addAttribute("msg", "用户名或密码错误!");
			return "/index/index";
		}
	}

	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		session.removeAttribute("order");
		return "/index/index";
	}
	
	/**
	 * 查看购物车
	 * @return
	 */
	@RequestMapping("/shopcart")
	public String shopcart(Model model, HttpSession session) {
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("msg", "请先登录!");
			return "/index/index";
		}
		model.addAttribute("typeList", typeService.getList());
		model.addAttribute("shopcartList", shopcartService.getList(user.getId()));
		model.addAttribute("totalPrice", shopcartService.getTotalPrice(user.getId()));
		return "/index/shopcart.jsp";
	}
	
	/**
	 * 购买
	 * @return
	 */
	@RequestMapping("/buy")
	public @ResponseBody int buy(Shopcart shopcart, HttpSession session, Model model){
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			return -111;
		}
		shopcart.setUserId(user.getId());
		shopcart.setGood(goodService.get(shopcart.getGoodId()));
		// 验证库存
//		int stock = skuService.getStock(shopcart.getGoodId(), shopcart.getColorId(), shopcart.getSizeId());
//		if(shopcart.getAmount() > stock) {
//			model.addAttribute("msg", "商品 [ " + shopcart.getGood().getName() + " ] 库存不足! 当前库存只有: " + stock);
//		}
		return orderService.save(Arrays.asList(shopcart), user);
	}
	
	/**
	 * 购买
	 * @return
	 */
	@RequestMapping("/cart")
	public @ResponseBody int cart(Shopcart shopcart, HttpSession session, Model model){
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			return -111;
		}
		shopcart.setUserId(user.getId());
		shopcartService.save(shopcart);
		int total = shopcartService.getTotal(user.getId());
		session.setAttribute("total", total);
		return total;
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("/add")
	public @ResponseBody boolean add(int skuid, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			return false;
		}
		return shopcartService.add(skuid);
	}
	
	/**
	 * 减少
	 */
	@RequestMapping("/less")
	public @ResponseBody boolean less(int skuid, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			return false;
		}
		return shopcartService.less(skuid);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public @ResponseBody boolean delete(int skuid, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			return false;
		}
		shopcartService.delete(skuid);
		session.setAttribute("total", shopcartService.getTotal(user.getId()));
		return true;
	}
	
	/**
	 * 总金额
	 * @return
	 */
	@RequestMapping("/total")
	public @ResponseBody int total(HttpSession session){
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			return -111;
		}
		return shopcartService.getTotalPrice(user.getId());
	}
	
	
	/**
	 * 提交订单
	 * @return
	 */
	@RequestMapping("/save")
	public String save(ServletRequest request, HttpSession session, Model model){
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("msg", "请先登录!");
			return "/index/index";
		}
		List<Shopcart> shopcartList = shopcartService.getList(user.getId());
		if(Objects.isNull(shopcartList) || shopcartList.isEmpty()) {
			model.addAttribute("msg", "购物车没有商品");
			return shopcart(model, session);
		}
		// 验证库存
		for(Shopcart cart : shopcartList) {
			int stock = skuService.getStock(cart.getGoodId(), cart.getColorId(), cart.getSizeId());
			if(cart.getAmount() > stock) {
				model.addAttribute("msg", "商品 [ " + cart.getGood().getName() + " ] 库存不足! 当前库存只有: " + stock);
				return shopcart(model, session);
			}
		}
		int orderid = orderService.save(shopcartList, user);
		if(orderid > 0) {
			// 清空购物车
			session.setAttribute("total", shopcartService.getTotal(user.getId()));
			// 跳转支付
			return "redirect:topay?orderid="+orderid;
		} 

		model.addAttribute("msg", "出了点问题");
		return shopcart(model, session);
	}
	
	/**
	 * 支付页面
	 * @return
	 */
	@RequestMapping("/topay")
	public String topay(int orderid, ServletRequest request, HttpSession session) {
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", "请先登录!");
			return "/index/index";
		}
		request.setAttribute("typeList", typeService.getList());
		request.setAttribute("order", orderService.get(orderid));
		return "/index/pay.jsp";
	}
	
	/**
	 * 支付(模拟)
	 * @return
	 */
	@RequestMapping("/pay")
	public String pay(Orders order, ServletRequest request, HttpSession session) {
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", "请先登录!");
			return "/index/index";
		}
		// 模拟支付
		orderService.pay(order);
		request.setAttribute("typeList", typeService.getList());
		request.setAttribute("order", orderService.get(order.getId()));
		request.setAttribute("msg", "支付成功! 即将跳转到订单列表");
		return "/index/pay.jsp";
	}
	
	/**
	 * 查看订单
	 * @return
	 */
	@RequestMapping("/order")
	public String order(HttpSession session, Model model){
		model.addAttribute("flag", 12);
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("msg", "请登录后查看订单!");
			return "/index/index";
		}
		model.addAttribute("typeList", typeService.getList());
		model.addAttribute("orderList", orderService.getListByUserid(user.getId()));
		return "/index/order.jsp";
	}
	
	
	/**
	 * 个人信息
	 * @return
	 */
	@RequestMapping("/my")
	public String my(HttpSession session, Model model){
		model.addAttribute("flag", 11);
		model.addAttribute("typeList", typeService.getList());
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("msg", "请先登录!");
			return "/index/index";
		}
		model.addAttribute("user", user);
		return "/index/my.jsp";
	}
	
	
	/**
	 * 修改信息
	 * @return
	 */
	@RequestMapping("/updateUser")
	public String updateUser(Users user, HttpSession session, Model model){
		model.addAttribute("flag", 11);
		model.addAttribute("typeList", typeService.getList());
		Users userLogin = (Users) session.getAttribute("user");
		if (userLogin == null) {
			model.addAttribute("msg", "请先登录!");
			return "/index/index";
		}
		// 修改资料
		Users u = userService.get(userLogin.getId());
		u.setName(user.getName());
		u.setPhone(user.getPhone());
		u.setAddress(user.getAddress());
		userService.update(u);  // 更新数据库
		session.setAttribute("user", u); // 更新session
		model.addAttribute("msg", "信息修改成功!");
		return "/index/my.jsp";
	}
	
	
	/**
	 * 修改信息
	 * @return
	 */
	@RequestMapping("/updatePassword")
	public String updatePassword(Users user, HttpSession session, Model model){
		model.addAttribute("flag", 11);
		model.addAttribute("typeList", typeService.getList());
		Users userLogin = (Users) session.getAttribute("user");
		if (userLogin == null) {
			model.addAttribute("msg", "请先登录!");
			return "/index/index";
		}
		// 修改密码
		Users u = userService.get(userLogin.getId());
		if(user.getPasswordNew()!=null && !user.getPasswordNew().trim().isEmpty()) {
			if (user.getPassword()!=null && !user.getPassword().trim().isEmpty() 
					&& SafeUtil.encode(user.getPassword()).equals(u.getPassword())) {
				if (user.getPasswordNew()!=null && !user.getPasswordNew().trim().isEmpty()) {
					u.setPassword(SafeUtil.encode(user.getPasswordNew()));
				}
				userService.update(u);  // 更新数据库
				session.setAttribute("user", u); // 更新session
				model.addAttribute("msg", "密码修改成功!");
				return "redirect:logout";
			}else {
				model.addAttribute("msg", "原密码错误!");
			}
		}
		return "/index/index";
	}
	
}