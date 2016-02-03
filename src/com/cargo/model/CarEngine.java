package com.cargo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.minidev.json.JSONObject;

@Entity
@Table(name="carEngine")
public class CarEngine {
	
	public enum IntakeType{
		机械增压, 涡轮增压, 自然吸气
	}
	
	public enum OilFeedType{
		化油器, 单点电喷, 多点电喷, 直喷
	}
	
	private Long id;
	private int displacement;
	private IntakeType intake;
	private int cylinder;
	private int maxPower;
	private int maxTorque;
	private int fuelLabel;
	private OilFeedType oilFeed;
	private String environmental;
	
	
	public JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("displacement", displacement);
		obj.put("intake", getIntake().toString());
		obj.put("cylinder", cylinder);
		obj.put("maxPower", maxPower);
		obj.put("maxTorque", maxTorque);
		obj.put("fuelLabel", fuelLabel);
		obj.put("oilFeed", getOilFeed().toString());
		obj.put("environmental", environmental);
		return obj;
		
	}


	public int getDisplacement() {
		return displacement;
	}


	public void setDisplacement(int displacement) {
		this.displacement = displacement;
	}


	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	public IntakeType getIntake() {
		return intake;
	}


	public void setIntake(IntakeType intake) {
		this.intake = intake;
	}


	public int getCylinder() {
		return cylinder;
	}


	public void setCylinder(int cylinder) {
		this.cylinder = cylinder;
	}


	public int getMaxPower() {
		return maxPower;
	}


	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}


	public int getMaxTorque() {
		return maxTorque;
	}


	public void setMaxTorque(int maxTorque) {
		this.maxTorque = maxTorque;
	}


	public int getFuelLabel() {
		return fuelLabel;
	}


	public void setFuelLabel(int fuelLabel) {
		this.fuelLabel = fuelLabel;
	}


	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	public OilFeedType getOilFeed() {
		return oilFeed;
	}


	public void setOilFeed(OilFeedType oilFeed) {
		this.oilFeed = oilFeed;
	}


	public String getEnvironmental() {
		return environmental;
	}


	public void setEnvironmental(String environmental) {
		this.environmental = environmental;
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
