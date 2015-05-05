package com.cargo.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Entity
@Table(name="favorite")
public class Favorite {
	private Long id;
	private String name;
	private Account owner;
	private List<Car> cars = new ArrayList<Car>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne()
	@JoinColumn(name="ownerid",nullable=false)
	public Account getOwner(){
		return owner;
	}
	public void setOwner(Account owner) {
		this.owner = owner;
	}
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="collection_car",joinColumns={@JoinColumn(name="collectionid")},inverseJoinColumns={@JoinColumn(name="carid")}) 
	public List<Car> getCars() {
		return cars;
	}
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	public void addCar(Car car){
		cars.add(car);
	}
	
	public void removeCar(Car car){
		cars.remove(car);
	}
	
	@Column(name="name",nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		JSONArray cars_json = new JSONArray();
		for(Car car : cars){
			cars_json.add(car.toJSON());
		}
		obj.put("cars", cars_json);
		return obj;
	}
	
}
