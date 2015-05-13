package com.cargo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.minidev.json.JSONObject;


//test
@Entity
public class CarTechnique {
	public enum Gearbox{
		MT, AT, CVT, DSG
	}
	
	public enum DriveType{
		前置前驱, 前置后驱, 前置四驱, 中置后驱, 中置四驱, 后置后驱, 后置四驱
	}
	
	public enum ResistanceType{
		电动阻力, 液压助力
	}
	
	private Long id;
	private Gearbox gearbox;
	private int maxSpeed;
	private String tyre;
	private int gearNum;
	private DriveType driveType;
	private ResistanceType resistanceType;
	
	public JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("gearbox", getGearbox().toString());
		obj.put("maxSpeed", maxSpeed);
		obj.put("trye", tyre);
		obj.put("gearNum", gearNum);
		obj.put("driveType", getDriveType().toString());
		obj.put("resistanceType", getResistanceType().toString());
		return obj;
		
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	public Gearbox getGearbox() {
		return gearbox;
	}

	public void setGearbox(Gearbox gearbox) {
		this.gearbox = gearbox;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getTyre() {
		return tyre;
	}

	public void setTyre(String tyre) {
		this.tyre = tyre;
	}

	public int getGearNum() {
		return gearNum;
	}

	public void setGearNum(int gearNum) {
		this.gearNum = gearNum;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	public DriveType getDriveType() {
		return driveType;
	}

	public void setDriveType(DriveType driveType) {
		this.driveType = driveType;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	public ResistanceType getResistanceType() {
		return resistanceType;
	}

	public void setResistanceType(ResistanceType resistanceType) {
		this.resistanceType = resistanceType;
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
