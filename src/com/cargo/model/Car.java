package com.cargo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.minidev.json.JSONObject;

@Entity
@Table(name="car")
public class Car{
	
	public enum CarType{
		New, Used
	}
	private Long id;
	private Long stock;
	private String picture;
	private String brand;
	private String model;
	private CarType type;
	private Account owner;
	private String description;
	private String price;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(cascade=CascadeType.ALL,optional=true)
	@JoinColumn(name="owner",nullable=false)
	public Account getOwner() {
		return owner;
	}
	public void setOwner(Account owner) {
		this.owner = owner;
	}
	
	@Column(name="stock",nullable=false)
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	
	@Column(name="carType",nullable=false)
	public CarType getType() {
		return type;
	}
	public void setType(CarType type) {
		this.type = type;
	}
	@Column(name="picture",nullable=false)
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Column(name="brand",nullable=false)
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Column(name="model",nullable=false)
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="price")
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("description", description);
		obj.put("picture", picture);
		obj.put("type", getType().toString());
		obj.put("brand", brand);
		obj.put("model", model);
		obj.put("price", price);
		obj.put("stock", stock);
		obj.put("owner", owner.toJSON());
		return obj;
	}

}