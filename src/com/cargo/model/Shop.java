package com.cargo.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.minidev.json.JSONObject;



@Entity
@Table(name="shop")
public class Shop {
	
	private String shopid;
	private String name;
	private String passwd;
	private String manager;
	private String phone;
	private String address;
	private String picture;
	
	@Column(name="manager")
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="picture")
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Id
	@Column(name="shopid")
	public String getshopid() {
		return shopid;
	}
	public boolean setshopid(String shopid) {
		Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	    Matcher m = p.matcher(shopid);
	    if(m.matches()){
	    	this.shopid = shopid;
	    	return true;
	    }
	    return false;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="passwd")
	public String getPassd() {
		return passwd;
	}
	public void setPassd(String passwd) {
		this.passwd = passwd;
	}
	
	@Column(name="phone")
	public String getPhone() {
		return phone;
	}
	public boolean setPhone(String phone) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	    Matcher m = p.matcher(phone);
	    if(m.matches()){
	    	this.phone = phone;
	    	return true;
	    }
	    return false;
	}
	
	
	public JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		obj.put("shopid", shopid);
		obj.put("name", name);
		obj.put("manager", manager);
		obj.put("passwd", passwd);
		obj.put("address", address);
		obj.put("phone", phone);
		obj.put("picture", picture);
		return obj;
	}
	

}
