package com.cargo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.minidev.json.JSONObject;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="car")
public class Car{
	
	public enum CarType{
		New, Used
	}
	private Long id;
	private int stock;
	private String picture;
	private String brand;
	private String model;
	private CarType type;
	private Account account;
	private String description;
	private int price;
	private List<Comment> Comments;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne()
	@JoinColumn(name="accountid",nullable=false)
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	@Column(name="stock",nullable=false,columnDefinition="int default 0")
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	@Enumerated(EnumType.ORDINAL)
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
	
	@Column(name="price",columnDefinition="int default 0")
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy="car",fetch=FetchType.EAGER)
	public List<Comment> getComments() {
		return Comments;
	}
	public void setComments(List<Comment> comments) {
		Comments = comments;
	}
	
	@Override
	public boolean equals(Object obj) {
		return getId().equals(((Car)obj).getId());
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
		obj.put("owner", account.getName());
		return obj;
	}

}