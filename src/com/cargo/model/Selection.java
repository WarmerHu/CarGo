package com.cargo.model;


public class Selection {
	
	public enum CarType{
		New, Used
	}
	private String keyword;
	private CarType type;
	private String brand;
	private String model;
	private String city;
	private int loPrice = 0;
	private int hiPrice = -1;
	private String suppliers;
	public String getKeyword() {
		return keyword;
	}
	public int getLowPrice() {
		return loPrice;
	}
	public void setLoPrice(int loPrice) {
		this.loPrice = loPrice;
	}
	public int getHiPrice() {
		return hiPrice;
	}
	public void setHiPrice(int hiPrice) {
		this.hiPrice = hiPrice;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(String suppliers) {
		this.suppliers = suppliers;
	}
	public CarType getType() {
		return type;
	}
	public void setType(CarType type) {
		this.type = type;
	}
	
	
}
