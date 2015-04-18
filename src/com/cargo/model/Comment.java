package com.cargo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="commment")
public class Comment {
	private Long id;
	private Customer cmid;
	private Car carid;
	private Date time;
	private String description;
	private String picture;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="cmid")
	public Customer getCmid() {
		return cmid;
	}
	public void setCmid(Customer cmid) {
		this.cmid = cmid;
	}
	
	@ManyToOne
	@JoinColumn(name="cmid")
	public Car getCarid() {
		return carid;
	}
	public void setCarid(Car carid) {
		this.carid = carid;
	}
	
	@Column(name="time")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="picture")
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
