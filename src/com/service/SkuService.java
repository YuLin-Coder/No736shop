package com.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.SkuColorDao;
import com.dao.SkuGoodDao;
import com.dao.SkuSizeDao;
import com.entity.SkuColor;
import com.entity.SkuGood;
import com.entity.SkuSize;

/**
 * SKU服务
 */
@Service	// 注解为service层spring管理bean
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class SkuService {

	@Autowired	
	private SkuColorDao skuColorDao;
	@Autowired	
	private SkuSizeDao skuSizeDao;
	@Autowired	
	private SkuGoodDao skuGoodDao;
	
	
	/**
	 * 获取颜色列表
	 * @return
	 */
	public List<SkuColor> getColorList(){
		return skuColorDao.getList();
	}
	
	/**
	 * 获取颜色列表
	 * @return
	 */
	public List<SkuColor> getColorList(int goodid){
		return skuColorDao.getListByGoodid(goodid);
	}
	
	/**
	 * 获取尺寸列表
	 * @return
	 */
	public List<SkuSize> getSizeList(){
		return skuSizeDao.getList();
	}
	
	/**
	 * 获取尺寸列表
	 * @return
	 */
	public List<SkuSize> getSizeList(int goodid){
		return skuSizeDao.getListByGoodid(goodid);
	}
	
	/**
	 * 获取商品列表
	 * @return
	 */
	public List<SkuGood> getGoodList(int goodid){
		List<SkuGood> skuList = skuGoodDao.getList(goodid);
		if(Objects.nonNull(skuList) && !skuList.isEmpty()) {
			for(SkuGood sku : skuList) {
				sku.setColor(skuColorDao.selectById(sku.getColorId()));
				sku.setSize(skuSizeDao.selectById(sku.getSizeId()));
			}
		}
		return skuList;
	}

	/**
	 * 获取颜色
	 * @param id
	 * @return
	 */
	public SkuColor getColor(int id) {
		return skuColorDao.selectById(id);
	}
	
	/**
	 * 获取尺寸
	 * @param id
	 * @return
	 */
	public SkuSize getSize(int id) {
		return skuSizeDao.selectById(id);
	}
	
	
	/**
	 * 添加颜色
	 * @param type
	 * @return
	 */
	public Integer addColor(String name) {
		SkuColor color = new SkuColor();
		color.setName(name);
		return skuColorDao.insert(color);
	}
	
	/**
	 * 添加尺寸
	 * @param type
	 * @return
	 */
	public Integer addSize(String name) {
		SkuSize size = new SkuSize();
		size.setName(name);
		return skuSizeDao.insert(size);
	}
	
	/**
	 * 添加商品
	 * @param type
	 * @return
	 */
	public Integer addGood(SkuGood good) {
		return skuGoodDao.insert(good);
	}

	/**
	 * 删除颜色
	 * @param type
	 */
	public boolean deleteColor(int id) {
		return skuColorDao.deleteById(id) > 0;
	}
	
	/**
	 * 删除尺寸
	 * @param type
	 */
	public boolean deleteSize(int id) {
		return skuSizeDao.deleteById(id) > 0;
	}
	
	/**
	 * 删除商品
	 * @param type
	 */
	public boolean deleteGood(int goodid) {
		return skuGoodDao.deleteByGoodid(goodid) > 0;
	}

	/**
	 * 获取库存
	 * @param goodid
	 * @param colorid
	 * @param sizeid
	 * @return
	 */
	public int getStock(int goodid, int colorid, int sizeid) {
		SkuGood skuGood = skuGoodDao.getStock(goodid, colorid, sizeid);
		return Objects.isNull(skuGood) || Objects.isNull(skuGood.getStock()) ? 0 : skuGood.getStock();
	}
	
	/**
	 * 减库存
	 * @param goodid
	 * @param colorid
	 * @param sizeid
	 * @param stock
	 * @return
	 */
	public boolean lessStock(int goodid, int colorid, int sizeid, int stock) {
		return skuGoodDao.updateStock(goodid, colorid, sizeid, stock);
	}
	
}
