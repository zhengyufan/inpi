package com.inpi.model;

import com.inpi.model.PostalAddress; 

public class Applicant {
	
	private String familyName;
	private String givenName;
    private PostalAddress address;
    private String id;
    
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    
}
