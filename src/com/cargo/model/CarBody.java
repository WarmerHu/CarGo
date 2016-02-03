package com.cargo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.minidev.json.JSONObject;


@Entity
@Table(name="carBody")
public class CarBody {
	private Long id;
	private int length;
	private int width;
	private int height;
	private int door;
	private int seat;
	private int wheelbase;
	private int weight;
	private int fuelTank;
	private int trunkSpace;
	private int groundClearance;
	private String guarantee;
	
	public JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("length", length);
		obj.put("width", width);
		obj.put("height", height);
		obj.put("door", door);
		obj.put("seat", seat);
		obj.put("wheelbase", wheelbase);
		obj.put("weight", weight);
		obj.put("fuelTank", fuelTank);
		obj.put("trunkSpace", trunkSpace);
		obj.put("groundClearance", groundClearance);
		obj.put("guarantee", guarantee);
		return obj;
		
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getDoor() {
		return door;
	}
	public void setDoor(int door) {
		this.door = door;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	
	public int getWheelbase() {
		return wheelbase;
	}
	public void setWheelbase(int wheelbase) {
		this.wheelbase = wheelbase;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getFuelTank() {
		return fuelTank;
	}
	public void setFuelTank(int fuelTank) {
		this.fuelTank = fuelTank;
	}
	public int getTrunkSpace() {
		return trunkSpace;
	}
	public void setTrunkSpace(int trunkSpace) {
		this.trunkSpace = trunkSpace;
	}
	public int getGroundClearance() {
		return groundClearance;
	}
	public void setGroundClearance(int groundClearance) {
		this.groundClearance = groundClearance;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
