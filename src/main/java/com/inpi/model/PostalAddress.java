package com.inpi.model;

public class PostalAddress {
/*
 * used to xml structure like
 * 							<address-1>186, BOULEVARD DE CRETEIL</address-1>
							<city>SAINT MAUR DES FOSSES</city>
							<postcode>94100</postcode>
							<country>FR</country>

 */
	String address1 = "";
	String address2 = "";
	String city = "";
	String postcode = "";
	String country = "";
	
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString(){
		String add = getAddress1() + " " +  
					 getAddress2() + ", " +
					 getCity() + ", " +
					 getPostcode() + ", " +
					 getCountry();
		return add;
	}
}