package com.cargo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

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
@Table(name="comment")
public class Comment {
	private Long id;
	private Account owner;
	private Car car;
	private Date time;
	private String content;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="ownerid")
	public Account getOwner() {
		return owner;
	}
	public void setOwner(Account owner) {
		this.owner = owner;
	}
	
	@ManyToOne
	@JoinColumn(name="carid")
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	
	@Column(name="time")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("account_id", owner.getId());
		obj.put("account", owner.getName());
		obj.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));
		obj.put("content", content);
		return obj;
	}
}
