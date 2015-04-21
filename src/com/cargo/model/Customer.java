package com.cargo.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import net.minidev.json.JSONObject;

@Entity
@Table(name="customer")
public class Customer {
	
	public enum Gender {
		Male, Female
	};
	
	private String cmid;
	private String name;
	private String passwd;
	private String phone;
	private Gender gender;
	
	@Id
	@Column(name="cmid")
	public String getCmid() {
		return cmid;
	}
	public boolean setCmid(String cmid) {
		Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	    Matcher m = p.matcher(cmid);
	    if(m.matches()){
	    	this.cmid = cmid;
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
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="gender")
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		obj.put("cmid", cmid);
		obj.put("name", name);
		obj.put("passwd", passwd);
		obj.put("phone", phone);
		obj.put("gender", getGender().toString());
		return obj;
	}
	

}
