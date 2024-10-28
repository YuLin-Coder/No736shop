package com.entity;

public class Items {
    private Integer id;

    private Integer price;

    private Integer amount;

    private Integer orderId;

    private Integer goodId;
    
    private Integer colorId;
    
    private Integer sizeId;

	private float total;
	
	private Goods good;
	
	private SkuColor color;
	
	private SkuSize size;
    
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

	public Goods getGood() {
		return good;
	}

	public void setGood(Goods good) {
		this.good = good;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }
}