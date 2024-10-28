package com.entity;

public class SkuGood {
    private Integer id;

    private Integer stock;

    private Integer colorId;

    private Integer sizeId;

    private Integer goodId;
    
    private SkuColor color;
    
    private SkuSize size;
    

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }
}