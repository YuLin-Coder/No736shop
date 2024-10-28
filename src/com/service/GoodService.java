package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.GoodsDao;
import com.entity.Goods;
import com.entity.SkuGood;
import com.entity.Tops;

/**
 * 商品服务
 */
@Service	// 注解为service层spring管理bean
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class GoodService {

	@Autowired	
	private GoodsDao goodDao;
	@Autowired
	private TopService topService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private SkuService skuService;
	
	
	/**
	 * 获取列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getList(int status, int page, int size){
		if (status == 0) {
			return packGood(goodDao.getList(size * (page-1), size));
		}
		List<Tops> topList = topService.getList((byte)status, page, size);
		if(topList!=null && !topList.isEmpty()) {
			List<Goods> goodList = new ArrayList<>();
			for(Tops top : topList) {
				goodList.add(packGood(goodDao.selectById(top.getGoodId())));
			}
			return goodList;
		}
		return null;
	}

	/**
	 * 获取产品总数
	 * @return
	 */
	public long getTotal(int status){
		if (status == 0) {
			return goodDao.getTotal();
		}
		return topService.getTotal((byte)status);
	}
	
	/**
	 * 通过名称获取产品列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByName(String name, int page, int size){
		return packGood(goodDao.getListByName(name, size * (page-1), size));
	}
	
	/**
	 * 通过名称获取产品总数
	 * @return
	 */
	public long getTotalByName(String name){
		return goodDao.getTotalByName(name);
	}

	/**
	 * 通过分类搜索
	 * @param typeid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByType(int typeid, int page, int size) {
		return typeid > 0 ? packGood(goodDao.getListByType(typeid, size * (page-1), size)) : goodDao.getList(size * (page-1), size);
	}
	
	/**
	 * 获取数量
	 * @param typeid
	 * @return
	 */
	public long getTotalByType(int typeid){
		return typeid > 0 ? goodDao.getTotalByType(typeid) : goodDao.getTotal();
	}
	
	/**
	 * 通过id获取
	 * @param productid
	 * @return
	 */
	public Goods get(int id) {
		return packGood(goodDao.selectById(id));
	}
	
	/**
	 * 添加
	 * @param product
	 */
	public boolean add(Goods good) {
		return goodDao.insert(good) > 0 && this.addSkuGood(good.getId(), good.getSkuList());
	}

	/**
	 * 修改
	 * @param product
	 * @return 
	 */
	public boolean update(Goods good) {
		return goodDao.updateById(good) > 0 && this.addSkuGood(good.getId(), good.getSkuList());
	}
	
	/**
	 * 添加商品sku
	 * @param goodid
	 * @param skuList
	 */
	private boolean addSkuGood(int goodid, List<SkuGood> skuList) {
		skuService.deleteGood(goodid);
		if(Objects.nonNull(skuList) && !skuList.isEmpty()) {
			for(SkuGood skuGood : skuList) {
				if(Objects.nonNull(skuGood.getColorId()) && Objects.nonNull(skuGood.getSizeId())) {
					skuGood.setGoodId(goodid);
					skuService.addGood(skuGood);
				}
			}
		}
		return true;
	}
	
	/**
	 * 删除商品
	 * 先删除此商品的推荐信息
	 * @param product
	 */
	public boolean delete(int goodid) {
		topService.deleteByGoodid(goodid);
		return goodDao.deleteById(goodid) > 0;
	}
	

	/**
	 * 封装商品
	 * @param list
	 * @return
	 */
	private List<Goods> packGood(List<Goods> list) {
		for(Goods good : list) {
			good = packGood(good);
		}
		return list;
	}

	/**
	 * 封装商品
	 * @param good
	 * @return
	 */
	private Goods packGood(Goods good) {
		if(good != null) {
			// 类目信息
			good.setType(typeService.get(good.getTypeId()));
			// 属性列表
			good.setSkuList(skuService.getGoodList(good.getId()));
			// 商品推荐情况
			List<Tops> topList = topService.getListByGoodid(good.getId());
			if (Objects.nonNull(topList) && !topList.isEmpty()) {
				good.setShow(true);
			}
		}
		return good;
	}

}