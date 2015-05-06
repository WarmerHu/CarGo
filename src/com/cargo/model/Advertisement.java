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
import javax.persistence.Table;

import net.minidev.json.JSONObject;

@Entity
@Table(name="advertisement")
public class Advertisement {
	
	public enum ADState{
		Apply, Revoke, Fail, Approval, Expired
	}
	
	public enum Position{
		Home, Top, Side
	}
	
	private Long id;
	private String picture;
	private String word;
	private String link;
//	private Date time;
	private ADState adstate;
	private Position position;
	private Account owner;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="picture",nullable=false)
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Column(name="link")
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="adstate",nullable=false)
	public ADState getAdstate() {
		return adstate;
	}
	public void setAdstate(ADState adstate) {
		this.adstate = adstate;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="position",nullable=false,columnDefinition="int default 0")
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name="owner",nullable=false)
	public Account getOwner() {
		return owner;
	}
	public void setOwner(Account owner) {
		this.owner = owner;
	}
	
//	@Column(name="time")
//	public Date getTime() {
//		return time;
//	}
//	public void setTime(Date time) {
//		this.time = time;
//	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("picture", picture);
		obj.put("link", link);
		obj.put("word", word);
//		obj.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));
		obj.put("adstate", getAdstate().toString());
		obj.put("position", getPosition().toString());
		obj.put("account_id", owner.getId());
		obj.put("account", owner.getName());
		return obj;
	}
	
	@Column(name="word")
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
}
