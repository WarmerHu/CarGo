//package com.cargo.util;
//
//import java.beans.PropertyEditorSupport;
//
//import com.cargo.model.Account.ProfileType;
//
//public class ProfileTypeEnumEditor extends PropertyEditorSupport {
//	@Override
//	public void setAsText(String text) throws IllegalArgumentException {
//		System.out.println(text + "111111111111111111111111");
//		this.setValue(ProfileType.valueOf(text));
//	}
//	
//	@Override
//	public String getAsText() {
//		return this.getValue().toString();
//	}
//}
