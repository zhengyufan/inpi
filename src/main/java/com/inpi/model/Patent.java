package com.inpi.model;

import java.util.ArrayList;

public class Patent {
	
	private String docNumber;
	private String countryCode;
	private String kind;
	private String id;
	private String status;
	private String title;
	
	private ArrayList<Applicant> applicantList;
	private ArrayList<Inventor> inventorList;
	private ArrayList<Agent> agentList;
	
	public Patent(){
		
	}
	
	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Applicant> getApplicantList() {
		return applicantList;
	}

	public void setApplicantList(ArrayList<Applicant> applicantList) {
		this.applicantList = applicantList;
	}

	public ArrayList<Inventor> getInventorList() {
		return inventorList;
	}

	public void setInventorList(ArrayList<Inventor> inventorList) {
		this.inventorList = inventorList;
	}

	public ArrayList<Agent> getAgentList() {
		return agentList;
	}

	public void setAgentList(ArrayList<Agent> agentList) {
		this.agentList = agentList;
	}

	public String toString() {
		String str = "Patent "+this.getTitle()+": Inventors("+this.getInventorList().toString()+")";
		return str;
	}
}
