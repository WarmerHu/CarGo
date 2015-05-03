package com.cargo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

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
@Table(name="orders")
public class Order{
	public enum Result{
		Booking,Booked,Checked,Bought,Cancel
	};
	
	private Long id;
	private Date book_time;
	private Account buyer;
	private Car car;
	private Result result;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="result",nullable=false)
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
	@ManyToOne
	@JoinColumn(name="buyer")
	public Account getBuyer() {
		return buyer;
	}
	public void setBuyer(Account buyer) {
		this.buyer = buyer;
	}
	
	@ManyToOne
	@JoinColumn(name="car")
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	
	@Column(name="book_time")
	public Date getBook_time() {
		return book_time;
	}
	public void setBook_time(Date book_time) {
		this.book_time = book_time;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("car",car.toJSON());
		obj.put("buyer",buyer.toJSON());
		obj.put("book_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(book_time));
		obj.put("result", result.name());
		return obj;
	}
	
}
