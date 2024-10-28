package com.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ItemsDao;
import com.dao.OrdersDao;
import com.entity.Items;
import com.entity.Orders;
import com.entity.Shopcart;
import com.entity.Users;

/**
 * 商品订单服务
 */
@Service	// 注解为service层spring管理bean
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class OrderService {

	@Autowired
	private OrdersDao orderDao;
	@Autowired
	private ItemsDao itemDao;
	@Autowired
	private GoodService goodService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private ShopcartService shopcartService;
	@Autowired
	private UserService userService;
	
	
	/**
	 * 保存订单
	 * @param order
	 */
	public int save(List<Shopcart> shopcartList, Users user) {
		if(Objects.isNull(shopcartList) || shopcartList.isEmpty()) {
			return -1;
		}
		int total = 0;
		int amount = shopcartList.size();
		for(Shopcart cart : shopcartList) {
			total += cart.getGood().getPrice() * cart.getAmount();
		}
		Orders order = new Orders();
		order.setTotal(total);
		order.setAmount(amount);
		order.setUserId(user.getId());
		order.setStatus(Orders.STATUS_UNPAY);
		order.setSystime(new Date());
		orderDao.insert(order);
		int orderid = order.getId();
		for(Shopcart cart : shopcartList){
			Items item = new Items();
			item.setOrderId(orderid);
			item.setGoodId(cart.getGoodId());
			item.setPrice(cart.getGood().getPrice());
			item.setAmount(cart.getAmount());
			item.setColorId(cart.getColorId());
			item.setSizeId(cart.getSizeId());
			itemDao.insert(item);
			// 减库存
			skuService.lessStock(cart.getGoodId(), cart.getColorId(), cart.getSizeId(), cart.getAmount());
		}
		// 清空购物车
		shopcartService.clean(user.getId());
		return orderid;
	}
	
	/**
	 * 订单支付
	 * @param order
	 */
	public void pay(Orders order) {
		Orders old = orderDao.selectById(order.getId());
		// 模拟支付完成
		old.setStatus(Orders.STATUS_PAYED);
		old.setPaytype(order.getPaytype());
		old.setName(order.getName());
		old.setPhone(order.getPhone());
		old.setAddress(order.getAddress());
		orderDao.updateById(old);
	}
	
	/**
	 * 获取订单列表
	 * @param page
	 * @param row
	 * @return
	 */
	public List<Orders> getList(byte status, int page, int row) {
		return pack(status>0 ? orderDao.getListByStatus(status, row * (page-1), row) : orderDao.getList(row * (page-1), row));
	}
	
	/**
	 * 获取总数
	 * @return
	 */
	public int getTotal(byte status) {
		return status>0 ? orderDao.getTotalByStatus(status) : orderDao.getTotal();
	}

	/**
	 * 订单发货
	 * @param id
	 * @return 
	 */
	public boolean dispose(int id) {
		Orders order = orderDao.selectById(id);
		order.setStatus(Orders.STATUS_SEND);
		return orderDao.updateByIdSelective(order) > 0;
	}
	
	/**
	 * 订单完成
	 * @param id
	 * @return 
	 */
	public boolean finish(int id) {
		Orders order = orderDao.selectById(id);
		order.setStatus(Orders.STATUS_FINISH);
		return orderDao.updateByIdSelective(order) > 0;
	}

	/**
	 * 删除订单
	 * @param id
	 */
	public boolean delete(int id) {
		return orderDao.deleteById(id) > 0;
	}
	
	/**
	 * 获取某用户全部订单
	 * @param userid
	 */
	public List<Orders> getListByUserid(int userid) {
		return pack(orderDao.getListByUserid(userid));
	}

	/**
	 * 通过id获取
	 * @param orderid
	 * @return
	 */
	public Orders get(int orderid) {
		return pack(orderDao.selectById(orderid));
	}
	
	/**
	 * 获取订单项目列表
	 * @param orderid
	 * @return
	 */
	public List<Items> getItemList(int orderid){
		List<Items> itemList = itemDao.getItemList(orderid);
		for(Items item : itemList) {
			item.setGood(goodService.get(item.getGoodId()));
			item.setColor(skuService.getColor(item.getColorId()));
			item.setSize(skuService.getSize(item.getSizeId()));
		}
		return itemList;
	}
	
	

	/**
	 * 封装
	 * @param order
	 * @return
	 */
	private Orders pack(Orders order) {
		if(Objects.nonNull(order)) {
			order.setItemList(this.getItemList(order.getId()));
			order.setUser(userService.get(order.getUserId()));
		}
		return order;
	}
	
	/**
	 * 封装
	 * @param order
	 * @return
	 */
	private List<Orders> pack(List<Orders> list) {
		if(Objects.nonNull(list) && !list.isEmpty()) {
			for(Orders order : list) {
				order = pack(order);
			}
		}
		return list;
	}
}
