package com.inpi.model;

import com.inpi.model.PostalAddress; 

public class Inventor {
	
	private String familyName;
	private String givenName;
	private PostalAddress address;
	private String id;
	
	public void setId(){
		String id = String.valueOf( this.address.toString().hashCode() );
		id = id.replaceAll(" ", "_").replaceAll("-", "");
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public PostalAddress getAddress() {
		return address;
	}
	public void setAddress(PostalAddress address) {
		this.address = address;
	}
	
	public String toString() {
		String str = "["+this.getGivenName()+this.getFamilyName()+"]";
		return str;
	}
}
