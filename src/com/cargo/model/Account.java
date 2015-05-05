package com.cargo.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.minidev.json.JSONObject;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="account")
public class Account {
	
	public enum ProfileType {
		Buyer, Solder, Admin
	};
	
	public enum Gender {
		Man, Lady
	};

	private Long id;
	private String name;
	private String password;
	private String auth_token;
	private Gender gender;
	private ProfileType type;
	private String telephone;
	private String email;
	private String address;
	private String city;
	private List<Favorite> collections = new ArrayList<Favorite>();
	private List<Car> cars=new ArrayList<Car>();
//	private List<Advertisement> ad=new ArrayList<Advertisement>();
	
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "gender")
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name="type",nullable=false,columnDefinition="int default 0")
	public ProfileType getType() {
		return type;
	}

	public void setType(ProfileType type) {
		this.type = type;
	}

	@Column(name = "telephone",length=13,nullable=false)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Column(name = "email",nullable=false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "name",nullable=false,length=16)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password",length=30,nullable=false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "auth_token")
	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	@OneToMany(mappedBy="account",cascade=CascadeType.ALL)
	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	@Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy="owner",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	public List<Favorite> getCollections() {
		return collections;
	}

	public void setCollections(List<Favorite> collections) {
		this.collections = collections;
	}
	
//	@OneToMany(mappedBy="account",cascade=CascadeType.ALL)
//	public List<Advertisement> getAd() {
//		return ad;
//	}
//
//	public void setAd(List<Advertisement> ad) {
//		this.ad = ad;
//	}

	public JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		obj.put("password", password);
		obj.put("email", email);
		obj.put("address", address);
		obj.put("city", city);
		obj.put("telephone", telephone);
		if(getGender() != null){
			obj.put("gender", getGender().name());
		}
		obj.put("type", getType().name());
		return obj;
	}
}
