package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.entity.SkuColor;

public interface SkuColorDao {
    int deleteById(Integer id);

    int insert(SkuColor record);

    int insertSelective(SkuColor record);

    SkuColor selectById(Integer id);

    int updateByIdSelective(SkuColor record);

    int updateById(SkuColor record);  
    
    
    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中
    
    // ------------------------------------------------------------
    
    // 以下方法使用mybatis注解实现
    
	/**
	 * 获取列表
	 * @return
	 */
    @Select("select * from sku_color order by id desc")
	public List<SkuColor> getList();
    
	/**
	 * 获取列表
	 * @return
	 */
    @Select("select * from sku_color where id in (select color_id from sku_good where good_id=#{goodid})")
	List<SkuColor> getListByGoodid(int goodid);
}