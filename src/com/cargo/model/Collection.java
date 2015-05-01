package com.cargo.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="collection")
public class Collection{
	private CollectionPK pk;

	@Id
	public CollectionPK getPk() {
		return pk;
	}

	public void setPk(CollectionPK pk) {
		this.pk = pk;
	}
}

@Embeddable
class CollectionPK implements Serializable{
	private static final long serialVersionUID = -2851526712831154573L;
	private Account id;
	private Car carid;
	
	@ManyToOne
	@JoinColumn(name="id")
	public Account getId() {
		return id;
	}
	public void setId(Account id) {
		this.id = id;
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
        if(obj instanceof CollectionPK){ 
        	CollectionPK pk=(CollectionPK)obj; 
            if(this.id.equals(pk.id) && this.carid.equals(pk.carid)){ 
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

//public class Collection {
//	private Long id;
//	private String name;
//	private Account owner;
//	private List<Car> cars;
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public Account getOwner() {
//		return owner;
//	}
//	public void setOwner(Account owner) {
//		this.owner = owner;
//	}
//	public List<Car> getCars() {
//		return cars;
//	}
//	public void setCars(List<Car> cars) {
//		this.cars = cars;
//	}
//}
