package com.entity;

import java.util.List;

public class Goods {
    private Integer id;

    private String name;

    private String cover;

    private Integer price;

    private String intro;

    private Integer typeId;
    
    private Types type;
    
    private boolean show;
    
    List<SkuGood> skuList;
    
    
    public List<SkuGood> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<SkuGood> skuList) {
		this.skuList = skuList;
	}

	public boolean getShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}