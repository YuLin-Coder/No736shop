package com.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Admins;
import com.entity.Goods;
import com.entity.Tops;
import com.entity.Types;
import com.entity.Users;
import com.service.AdminService;
import com.service.GoodService;
import com.service.OrderService;
import com.service.SkuService;
import com.service.TopService;
import com.service.TypeService;
import com.service.UserService;
import com.util.PageUtil;
import com.util.SafeUtil;
import com.util.UploadUtil;

/**
 * 后台相关接口
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final int rows = 10;

	@Autowired
	private AdminService adminService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodService goodService;
	@Autowired
	private TopService topService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private SkuService skuService;

	/**
	 * 管理员登录
	 * @return
	 */
	@GetMapping("/login")
	public String log() {
		return "/admin/login.jsp";
	}
	
	/**
	 * 管理员登录
	 * @return
	 */
	@PostMapping("/login")
	public String login(Admins admin, HttpServletRequest request, HttpSession session) {
		if (adminService.checkUser(admin.getUsername(), admin.getPassword())) {
			session.setAttribute("username", admin.getUsername());
			return "redirect:typeList";
		}
		request.setAttribute("msg", "用户名或密码错误!");
		return "/admin/login.jsp";
	}

	/**
	 * 退出
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("admin");
		return "/admin/login.jsp";
	}
	
	/**
	 * 后台首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		request.setAttribute("msg", "恭喜你! 登录成功了");
		return "/admin/index.jsp";
	}

	/**
	 * 类目列表
	 * 
	 * @return
	 */
	@RequestMapping("/typeList")
	public String typeList(HttpServletRequest request) {
		request.setAttribute("flag", 1);
		request.setAttribute("typeList", typeService.getList());
		return "/admin/type_list.jsp";
	}

	/**
	 * 类目添加
	 * 
	 * @return
	 */
	@RequestMapping("/typeAdd")
	public String typeAdd(HttpServletRequest request) {
		request.setAttribute("flag", 1);
		return "/admin/type_add.jsp";
	}
	
	/**
	 * 类目添加
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/typeSave")
	public String typeSave(Types type, MultipartFile file,
			@RequestParam(required=false, defaultValue="1") int page) throws Exception {
		type.setCover(UploadUtil.upload(file));
		typeService.add(type);
		return "redirect:typeList?flag=1&page="+page;
	}

	/**
	 * 类目更新
	 * 
	 * @return
	 */
	@RequestMapping("/typeEdit")
	public String typeEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 1);
		request.setAttribute("type", typeService.get(id));
		return "/admin/type_edit.jsp";
	}

	/**
	 * 类目更新
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/typeUpdate")
	public String typeUpdate(Types type, MultipartFile file,
			@RequestParam(required=false, defaultValue="1") int page) throws Exception {
		if (Objects.nonNull(file) && !file.isEmpty()) {
			type.setCover(UploadUtil.upload(file));
		}
		typeService.update(type);
		return "redirect:typeList?flag=1&page="+page;
	}

	/**
	 * 类目删除
	 * 
	 * @return
	 */
	@RequestMapping("/typeDelete")
	public String typeDelete(Types type, 
			@RequestParam(required=false, defaultValue="1") int page) {
		typeService.delete(type);
		return "redirect:typeList?flag=1&page="+page;
	}

	/**
	 * sku列表
	 * 
	 * @return
	 */
	@RequestMapping("/skuList")
	public String skuList(@RequestParam(required=false, defaultValue="0")byte status, HttpServletRequest request) {
		request.setAttribute("flag", 2);
		request.setAttribute("status", status);
		request.setAttribute("skuList", status>0 ? skuService.getSizeList() : skuService.getColorList());
		return "/admin/sku_list.jsp";
	}
	
	/**
	 * sku添加
	 * 
	 * @return
	 */
	@RequestMapping("/skuSave")
	public String skuSave(String name, int status) {
		if(status > 0) {
			skuService.addSize(name);
		}else {
			skuService.addColor(name);
		}
		return "redirect:skuList?status="+status;
	}
	
	/**
	 * sku删除
	 * 
	 * @return
	 */
	@RequestMapping("/skuDelete")
	public String skuDelete(int id, int status) {
		if(status > 0) {
			skuService.deleteSize(id);
		}else {
			skuService.deleteColor(id);
		}
		return "redirect:skuList?status="+status;
	}
	
	
	/**
	 * 产品列表
	 * 
	 * @return
	 */
	@RequestMapping("/goodList")
	public String goodList(@RequestParam(required=false, defaultValue="0")byte status, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		request.setAttribute("flag", 3);
		request.setAttribute("page", page);
		request.setAttribute("status", status);
		request.setAttribute("goodList", goodService.getList(status, page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, goodService.getTotal(status), page, rows));
		return "/admin/good_list.jsp";
	}

	/**
	 * 产品添加
	 * 
	 * @return
	 */
	@RequestMapping("/goodAdd")
	public String goodAdd(HttpServletRequest request) {
		request.setAttribute("flag", 3);
		request.setAttribute("typeList", typeService.getList());
		request.setAttribute("colorList", skuService.getColorList());
		request.setAttribute("sizeList", skuService.getSizeList());
		return "/admin/good_add.jsp";
	}

	/**
	 * 产品添加
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goodSave")
	public String goodSave(Goods good, MultipartFile file,
			@RequestParam(required=false, defaultValue="1") int page) throws Exception {
		good.setCover(UploadUtil.upload(file));
		goodService.add(good);
		return "redirect:goodList?flag=3&page="+page;
	}

	/**
	 * 产品更新
	 * 
	 * @return
	 */
	@RequestMapping("/goodEdit")
	public String goodEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 3);
		request.setAttribute("typeList", typeService.getList());
		request.setAttribute("colorList", skuService.getColorList());
		request.setAttribute("sizeList", skuService.getSizeList());
		request.setAttribute("good", goodService.get(id));
		return "/admin/good_edit.jsp";
	}

	/**
	 * 产品更新
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goodUpdate")
	public String goodUpdate(Goods good, MultipartFile file, 
			@RequestParam(required=false, defaultValue="1") int page) throws Exception {
		if (Objects.nonNull(file) && !file.isEmpty()) {
			good.setCover(UploadUtil.upload(file));
		}
		goodService.update(good);
		return "redirect:goodList?flag=3&page="+page;
	}

	/**
	 * 产品删除
	 * 
	 * @return
	 */
	@RequestMapping("/goodDelete")
	public String goodDelete(int id, 
			@RequestParam(required=false, defaultValue="1") int page) {
		goodService.delete(id);
		return "redirect:goodList?flag=3&page="+page;
	}
	
	/**
	 * 添加推荐
	 * @return
	 */
	@RequestMapping("/topSave")
	public @ResponseBody String topSave(Tops tops, 
			@RequestParam(required=false, defaultValue="0")byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		int id = topService.add(tops);
		return id > 0 ? "ok" : null;
	}
	
	/**
	 * 删除推荐
	 * @return
	 */
	@RequestMapping("/topDelete")
	public @ResponseBody String topDelete(Tops tops, 
			@RequestParam(required=false, defaultValue="0")byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		boolean flag = topService.delete(tops);
		return flag ? "ok" : null;
	}

	/**
	 * 订单列表
	 * 
	 * @return
	 */
	@RequestMapping("/orderList")
	public String orderList(@RequestParam(required=false, defaultValue="0")byte status, HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") int page) {
		request.setAttribute("flag", 4);
		request.setAttribute("status", status);
		request.setAttribute("orderList", orderService.getList(status, page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, orderService.getTotal(status), page, rows));
		return "/admin/order_list.jsp";
	}

	/**
	 * 订单发货
	 * 
	 * @return
	 */
	@RequestMapping("/orderDispose")
	public String orderDispose(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.dispose(id);
		return "redirect:orderList?flag=4&status="+status+"&page="+page;
	}
	
	/**
	 * 订单完成
	 * 
	 * @return
	 */
	@RequestMapping("/orderFinish")
	public String orderFinish(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.finish(id);
		return "redirect:orderList?flag=4&status="+status+"&page="+page;
	}

	/**
	 * 订单删除
	 * 
	 * @return
	 */
	@RequestMapping("/orderDelete")
	public String orderDelete(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.delete(id);
		return "redirect:orderList?flag=4&status="+status+"&page="+page;
	}

	/**
	 * 顾客管理
	 * 
	 * @return
	 */
	@RequestMapping("/userList")
	public String userList(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") int page) {
		request.setAttribute("flag", 5);
		request.setAttribute("userList", userService.getList(page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, userService.getTotal(), page, rows));
		return "/admin/user_list.jsp";
	}

	/**
	 * 顾客添加
	 * 
	 * @return
	 */
	@RequestMapping("/userAdd")
	public String userAdd(HttpServletRequest request) {
		request.setAttribute("flag", 5);
		return "/admin/user_add.jsp";
	}

	/**
	 * 顾客添加
	 * 
	 * @return
	 */
	@RequestMapping("/userSave")
	public String userSave(Users user, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		if (userService.isExist(user.getUsername())) {
			request.setAttribute("msg", "用户名已存在!");
			return "/admin/user_add.jsp";
		}
		userService.add(user);
		return "redirect:userList?flag=5&page="+page;
	}

	/**
	 * 顾客密码重置页面
	 * 
	 * @return
	 */
	@RequestMapping("/userRe")
	public String userRe(int id, HttpServletRequest request) {
		request.setAttribute("flag", 5);
		request.setAttribute("user", userService.get(id));
		return "/admin/user_reset.jsp";
	}

	/**
	 * 顾客密码重置
	 * 
	 * @return
	 */
	@RequestMapping("/userReset")
	public String userReset(Users user, 
			@RequestParam(required=false, defaultValue="1") int page) {
		String password = SafeUtil.encode(user.getPassword());
		user = userService.get(user.getId());
		user.setPassword(password);
		userService.update(user);
		return "redirect:userList?page="+page;
	}

	/**
	 * 顾客更新
	 * 
	 * @return
	 */
	@RequestMapping("/userEdit")
	public String userEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 5);
		request.setAttribute("user", userService.get(id));
		return "/admin/user_edit.jsp";
	}

	/**
	 * 顾客更新
	 * 
	 * @return
	 */
	@RequestMapping("/userUpdate")
	public String userUpdate(Users user, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.update(user);
		return "redirect:userList?flag=5&page="+page;
	}

	/**
	 * 顾客删除
	 * 
	 * @return
	 */
	@RequestMapping("/userDelete")
	public String userDelete(Users user, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.delete(user);
		return "redirect:userList?flag=5&page="+page;
	}

	/**
	 * 管理员列表
	 * 
	 * @return
	 */
	@RequestMapping("/adminList")
	public String adminList(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		request.setAttribute("flag", 6);
		request.setAttribute("adminList", adminService.getList(page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, adminService.getTotal(), page, rows));
		return "/admin/admin_list.jsp";
	}

	/**
	 * 管理员添加
	 * 
	 * @return
	 */
	@RequestMapping("/adminAdd")
	public String adminAdd(HttpServletRequest request) {
		request.setAttribute("flag", 6);
		return "/admin/admin_add.jsp";
	}
	
	/**
	 * 管理员密码重置
	 * 
	 * @return
	 */
	@RequestMapping("/adminRe")
	public String adminRe(int id, HttpServletRequest request) {
		request.setAttribute("flag", 6);
		request.setAttribute("admin", adminService.get(id));
		return "/admin/admin_reset.jsp";
	}

	/**
	 * 管理员密码重置
	 * 
	 * @return
	 */
	@RequestMapping("/adminReset")
	public String adminReset(Admins admin, HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") int page) {
		String password = SafeUtil.encode(admin.getPassword());
		admin = adminService.get(admin.getId());
		admin.setPassword(password);
		adminService.update(admin);
		return "redirect:adminList?page="+page;
	}

	/**
	 * 管理员添加
	 * 
	 * @return
	 */
	@RequestMapping("/adminSave")
	public String adminSave(Admins admin, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		if (adminService.isExist(admin.getUsername())) {
			request.setAttribute("msg", "用户名已存在!");
			return "/admin/admin_add.jsp";
		}
		adminService.add(admin);
		return "redirect:adminList?flag=6&page="+page;
	}

	/**
	 * 管理员修改
	 * 
	 * @return
	 */
	@RequestMapping("/adminEdit")
	public String adminEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 6);
		request.setAttribute("admin", adminService.get(id));
		return "/admin/admin_edit.jsp";
	}

	/**
	 * 管理员更新
	 * 
	 * @return
	 */
	@RequestMapping("/adminUpdate")
	public String adminUpdate(Admins admin, 
			@RequestParam(required=false, defaultValue="1") int page) {
		admin.setPassword(SafeUtil.encode(admin.getPassword()));
		adminService.update(admin);
		return "redirect:adminList?flag=6&page="+page;
	}

	/**
	 * 管理员删除
	 * 
	 * @return
	 */
	@RequestMapping("/adminDelete")
	public String adminDelete(Admins admin, 
			@RequestParam(required=false, defaultValue="1") int page) {
		adminService.delete(admin);
		return "redirect:adminList?flag=6&page="+page;
	}

}
