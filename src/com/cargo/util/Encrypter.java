package com.cargo.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.Base64Utils;

import com.cargo.model.Account;

public class Encrypter {
	
	

	public static String encode(String ss){
		return Base64Utils.encodeToString(ss.getBytes());
	}
	
	public static String decode(String ss) throws IOException{
		if(ss == null){
			return "";
		}
		return new String(Base64Utils.decodeFromString(ss));
	}
	
	public static String encryptMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");  
	    md5.update(data);  
	    return new String(md5.digest()); 
	}
	
	public static String encode(Account a){
		return encode(a.getName() + " " + a.getPassword());
	}
}
