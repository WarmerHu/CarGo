package com.cargo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.minidev.json.JSONObject;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="car")
@DynamicUpdate(true)
public class Car{
	
	public enum CarType{
		New,Used
	}
	private Long carid;
	private Long stock;
	private Shop shopid;
	private String picture;
	private String brand;
	private String model;
	private CarType ctype;
	private Account owner;
	private String description;
	private String price;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="carid")
	public Long getCarid() {
		return carid;
	}
	public void setCarid(Long carid) {
		this.carid = carid;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	public Account getOwner() {
		return owner;
	}
	public void setOwner(Account owner) {
		this.owner = owner;
	}
	
	@Column(name="stock")
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	
	@ManyToOne
	@JoinColumn(name="shopid")
	public Shop getshopid() {
		return shopid;
	}
	public void setshopid(Shop shopid) {
		this.shopid = shopid;
	}
	
	@Column(name="picture")
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Column(name="brand")
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Column(name="model")
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	@Column(name="ctype")
	@Enumerated(EnumType.ORDINAL)
	public CarType getCtype() {
		return ctype;
	}
	public void setCtype(CarType ctype) {
		this.ctype = ctype;
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
		obj.put("carid", carid);
		if(shopid != null){
			obj.put("shopid", shopid.toJSON());
		}
		obj.put("description", description);
		obj.put("picture", picture);
		if(getCtype() != null){
			obj.put("ctype", getCtype().toString());
		}
		obj.put("brand", brand);
		obj.put("model", model);
		obj.put("price", price);
		obj.put("stock", stock);
		return obj;
	}

}