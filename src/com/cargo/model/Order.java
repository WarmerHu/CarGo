package com.cargo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="order")
public class Order{
	private OrderPK pk;
	private Date book_time;
	
	@Id
	public OrderPK getPk() {
		return pk;
	}
	public void setPk(OrderPK pk) {
		this.pk = pk;
	}
	
	@Column(name="book_time")
	public Date getBook_time() {
		return book_time;
	}
	public void setBook_time(Date book_time) {
		this.book_time = book_time;
	}
	
}

@Embeddable
class OrderPK implements Serializable{
	public enum Result{
		Booking,Booked,Checked,Bought,Cancel
	};
	private Customer cmid;
	private Car carid;
	private Result result;
	
	@Column(name="result")
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
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
	@JoinColumn(name="carid")
	public Car getCarid() {
		return carid;
	}
	public void setCarid(Car carid) {
		this.carid = carid;
	}
	
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof OrderPK){ 
        	OrderPK pk=(OrderPK)obj; 
            if(this.cmid.equals(pk.cmid) && this.carid.equals(pk.carid) && this.result.equals(pk.result)){ 
                return true; 
            } 
        } 
        return false; 
    }

    @Override 
    public int hashCode() { 
        return super.hashCode(); 
    }
}
