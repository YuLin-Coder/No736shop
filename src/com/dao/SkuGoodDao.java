package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.entity.SkuGood;

public interface SkuGoodDao {
    int deleteById(Integer id);

    int insert(SkuGood record);

    int insertSelective(SkuGood record);

    SkuGood selectById(Integer id);

    int updateByIdSelective(SkuGood record);

    int updateById(SkuGood record);
    
    
    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中
    
    // ------------------------------------------------------------
    
    // 以下方法使用mybatis注解实现
    
	/**
	 * 获取列表
	 * @return
	 */
    @Select("select * from sku_good where good_id=#{goodid}")
	public List<SkuGood> getList(int goodid);
    
    /**
     * 删除
     * @param goodid
     * @return
     */
    @Delete("delete from sku_good where good_id=#{goodid}")
    public int deleteByGoodid(int goodid);

    /**
     * 获取库存
     * @param goodid
     * @param colorid
     * @param sizeid
     * @return
     */
    @Select("select * from sku_good where good_id=#{goodid} and color_id=#{colorid} and size_id=#{sizeid}")
	SkuGood getStock(@Param("goodid")int goodid, @Param("colorid")int colorid, @Param("sizeid")int sizeid);
    
    @Update("update sku_good set stock=stock-#{stock} where good_id=#{goodid} and color_id=#{colorid} and size_id=#{sizeid}")
    boolean updateStock(@Param("goodid")int goodid, @Param("colorid")int colorid, @Param("sizeid")int sizeid, @Param("stock")int stock);
}