package com.entity;

public class Shopcart {
    private Integer id;

    private Integer amount;

    private Integer goodId;

    private Integer colorId;

    private Integer sizeId;

    private Integer userId;
    
    private Goods good;
    
    private SkuColor color;
    
    private SkuSize size;
    

    public Goods getGood() {
		return good;
	}

	public void setGood(Goods good) {
		this.good = good;
	}

	public SkuColor getColor() {
		return color;
	}

	public void setColor(SkuColor color) {
		this.color = color;
	}

	public SkuSize getSize() {
		return size;
	}

	public void setSize(SkuSize size) {
		this.size = size;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}