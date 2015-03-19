package com.cargo.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encrypter {
	
	

	public static String encode(String ss){
		return new BASE64Encoder().encode(ss.getBytes());
	}
	
	public static String decode(String ss) throws IOException{
		return new String(new BASE64Decoder().decodeBuffer(ss));
	}
	
	public static String encryptMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");  
	    md5.update(data);  
	    return new String(md5.digest()); 
	}
}
