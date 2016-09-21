package com.inpi.parser;

import java.util.*;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;

import com.inpi.model.Agent;
import com.inpi.model.Applicant;
import com.inpi.model.Inventor;
import com.inpi.model.Patent;

/**
 * RdfParser is used to parse one patent into 
 * a Jena RDF model
 * 
 * @author Administrator
 *
 */

public class RdfParser {

	/* uri */
	private static final String inpiUri = "http://givingsense.eu/pact/2016/primpi#";
	private static final String dbpediaOntolgyUri = "http://dbpedia.org/ontology/";
	private static final String countryUri = dbpediaOntolgyUri+"country";
	private static final String dbpediaUri = "http://dbpedia.org/resource/";
	private static final String franceUri = dbpediaUri+"France";
	private static final String angleterreUri = dbpediaUri+"England";
	
	private static  String hasDocNumberUri = inpiUri+"hasDocNumber";
	private static  String hasApplicantUri = inpiUri+"hasApplicant";
	private static  String hasInventorUri = inpiUri+"hasInventor";
	private static  String hasAgentUri = inpiUri+"hasAgent";
	private static  String PatentDocumentUri = inpiUri+"PatentDocument";
	
	/* Jena model, resources and properties */
	private Model model;
	private Resource patentDocument;
	private Property hasDocNumber;
	private Property hasApplicant;
	private Property hasInventor;
	private Property hasAgent;
	private Property country;
	
	private Hashtable<String, String>  countryTab;
	
	/* init function */	
	private void init(){
		
		/* Init Jena Configurations */
		model = ModelFactory.createDefaultModel();
		patentDocument = model.createResource(PatentDocumentUri);
		hasDocNumber = model.createProperty(hasDocNumberUri);
		hasApplicant = model.createProperty(hasApplicantUri);
		hasInventor = model.createProperty(hasInventorUri);
		hasAgent = model.createProperty(hasAgentUri);
		country = model.createProperty(countryUri);
		countryTab = new Hashtable<String, String>();
		countryTab.put("FR", franceUri);
		countryTab.put("EN", angleterreUri);
					
	}
	
	public Model parsePatentToRdfModel(Patent p){
		
		init();
		
		ArrayList<Applicant> appList = p.getApplicantList();
		ArrayList<Inventor> invList = p.getInventorList();
		ArrayList<Agent> agtList = p.getAgentList();
		
		Resource patent = addPatent(p);
		for( Applicant app : appList){
			Resource applicant = addApplicant(app);
			linkApplicantToPatent(applicant, patent);
		}
		for( Inventor inv : invList){
			Resource inventor = addInventor(inv);
			linkInventorToPatent(inventor, patent);
		}
		for( Agent agt : agtList){
			Resource agent = addAgent(agt);
			linkAgentToPatent(agent, patent);
		}
		
		return this.model;
	}

	private Resource addPatent(Patent p){
		String paturi = inpiUri + p.getDocNumber();
		Resource patent = model.createResource(paturi);
		patent.addProperty(RDFS.label, p.getTitle()); // tir� du <invention-title>
		patent.addProperty(RDF.type, patentDocument);// dit que l'objet dont je parle est de type patentDocument
		patent.addProperty(country, countryTab.get(p.getCountryCode()));
		return patent;
	}
	
	private Resource addApplicant(Applicant a){
		String appuri = inpiUri + "Applicant_" + 
						a.getFamilyName().replaceAll(" ", "_") + "_" + 
						a.getGivenName().replaceAll(" ", "_") + "_" + 
						a.getId();
		Resource applicant = model.createResource(appuri);  
		applicant.addProperty(RDFS.label, a.getFamilyName().toUpperCase()); // tir� du <applicant><last-name>
		applicant.addProperty(VCARD.Family, a.getFamilyName().toUpperCase()); // tir� du <applicant><last-name>
		applicant.addProperty(VCARD.Given, a.getGivenName().toUpperCase()); // tir� du <applicant><first-name>
		//app.addProperty(VCARD.ADR, a.getAddress().toString()); // tir� du <applicant><address><address-1>
		applicant.addProperty(VCARD.ADR, model.createResource()
				                        .addProperty(VCARD.Street, a.getAddress().getAddress1())
				                        .addProperty(VCARD.Locality, a.getAddress().getAddress2())
				                        .addProperty(VCARD.Region, a.getAddress().getCity())
				                        .addProperty(VCARD.Country, a.getAddress().getCountry())
				                        .addProperty(VCARD.Pcode, a.getAddress().getPostcode()));
		
		return applicant;
	}
	
	private Resource addInventor(Inventor inv){
		String invuri = inpiUri + "Inventor_" + 
						inv.getFamilyName().replaceAll(" ", "_") + "_" + 
						inv.getGivenName().replaceAll(" ", "_") + "_" + 
						inv.getId();
		Resource inventor = model.createResource(invuri); // creation of a blank node
		inventor.addProperty(RDFS.label, inv.getFamilyName().toUpperCase()); // tir� du <inventor><last-name>
		inventor.addProperty(VCARD.Family, inv.getFamilyName().toUpperCase()); // tir� du <inventor><last-name>
		inventor.addProperty(VCARD.Given, inv.getGivenName().toUpperCase()); // tir� du <inventor><first-name>
		inventor.addProperty(VCARD.ADR, model.createResource()
						                .addProperty(VCARD.Street, inv.getAddress().getAddress1())
						                .addProperty(VCARD.Locality, inv.getAddress().getAddress2())
						                .addProperty(VCARD.Region, inv.getAddress().getCity())
						                .addProperty(VCARD.Country, inv.getAddress().getCountry())
						                .addProperty(VCARD.Pcode, inv.getAddress().getPostcode()));
		return inventor;
	}
	
	private Resource addAgent(Agent agt){
		String agturi = inpiUri + "Agent_" + 
						agt.getFamilyName().replaceAll(" ", "_") + "_" + 
						agt.getGivenName().replaceAll(" ", "_") + "_" +
						agt.getId();
		Resource agent = model.createResource(agturi); // creation of a blank node
		agent.addProperty(RDFS.label, agt.getFamilyName()); // tir� du <agent><last-name>
		agent.addProperty(VCARD.Family, agt.getFamilyName()); // tir� du <agent><last-name>
		agent.addProperty(VCARD.Given, agt.getGivenName()); // tir� du <agent><first-name>
		agent.addProperty(VCARD.ADR, agt.getAddress().toString()); // tir� du <agent><address><address-1>
		agent.addProperty(VCARD.ADR, model.createResource()
						                .addProperty(VCARD.Street, agt.getAddress().getAddress1())
						                .addProperty(VCARD.Locality, agt.getAddress().getAddress2())
						                .addProperty(VCARD.Region, agt.getAddress().getCity())
						                .addProperty(VCARD.Country, agt.getAddress().getCountry())
						                .addProperty(VCARD.Pcode, agt.getAddress().getPostcode()));
		return agent;
	}
	
	private void linkApplicantToPatent(Resource applicant, Resource patent) {
		patent.addProperty(hasApplicant, applicant);
	}
	
	private void linkInventorToPatent(Resource inventor, Resource patent) {
		patent.addProperty(hasInventor, inventor);
	}
	
	private void linkAgentToPatent(Resource agent, Resource patent) {
		patent.addProperty(hasAgent, agent);
	}

}
