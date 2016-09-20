package com.inpi.jersey.service;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import com.inpi.model.Agent;
import com.inpi.model.Applicant;
import com.inpi.model.Inventor;
import com.inpi.storage.TripleStore;

public class TripleStoreAccessor {
	
	public static ResultSet findPatentListByInventor(String givenname, String familyname){
		
		 // Execute the query and obtain results
	    QueryExecution qe;
	    ResultSet results;
	    
		/* Check if applicant exists */
		String query = 
				"PREFIX inpi: <http://givingsense.eu/pact/2016/primpi#>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"   +
				"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#>\n"  +
				"\n" +
				"SELECT ?Patent \n" +
				"WHERE{\n" +
				"	?Patent a inpi:PatentDocument; \n" +
				"				inpi:hasInventor ?Inventor. \n" +
				"	?Inventor rdfs:label '" + familyname.replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
//				"			vcard:ADR '" + i.getAddress().toString().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"			vcard:Family '" + familyname.replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"           vcard:Given '" + givenname.replaceAll("\n", "").replaceAll("'", " ") + "'. \n" +
				"} \n"
				;
		
		qe = QueryExecutionFactory.create(query, TripleStore.model);
		results =  qe.execSelect();
		
		return results;
	}

	/**
	 * Find if applicant exists in triple store
	 * If exists, return the URI of applicant,
	 * else return ""
	 * 
	 * @param applicant
	 * @return
	 */
	public String findApplicant(Applicant a){
		String appUri = "";
		
		 // Execute the query and obtain results
	    QueryExecution qe;
	    ResultSet results;
	    
		/* Check if applicant exists */
		String query = 
				"PREFIX inpi: <http://givingsense.eu/pact/2016/primpi#>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"   +
				"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#>\n"  +
				"\n" +
				"SELECT ?Applicant \n" +
				"WHERE{\n" +
				"	?Patent a inpi:PatentDocument; \n" +
				"				inpi:hasApplicant ?Applicant. \n" +
				"	?Applicant rdfs:label '" + a.getFamilyName().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"			vcard:ADR '" + a.getAddress().toString().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"			vcard:Family '" + a.getFamilyName().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"			vcard:Given '" + a.getGivenName().replaceAll("\n", "").replaceAll("'", " ") + "'. \n" +
				"} \n"
				;
		
		qe = QueryExecutionFactory.create(query, TripleStore.model);
		results =  qe.execSelect();
		
		if( results.hasNext() ){
			appUri = results.next().toString();
			System.out.println("\n**************************************************************************");
	    	System.out.println("                  Applicant " + appUri + " exists!");
	    	System.out.println("**************************************************************************");
		    // Output query results    
		    ResultSetFormatter.out(System.out, results);
		    System.out.println("");
		}
		
		return appUri;
	}
	
	public String findInventor(Inventor i){
		String invUri = "";
		
		 // Execute the query and obtain results
	    QueryExecution qe;
	    ResultSet results;
	    
		/* Check if inventor exists */
		String query = 
				"PREFIX inpi: <http://givingsense.eu/pact/2016/primpi#>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"   +
				"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#>\n"  +
				"\n" +
				"SELECT ?Inventor \n" +
				"WHERE{\n" +
				"	?Patent a inpi:PatentDocument; \n" +
				"				inpi:hasInventor ?Inventor. \n" +
				"	?Inventor rdfs:label '" + i.getFamilyName().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"			vcard:ADR '" + i.getAddress().toString().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"			vcard:Family '" + i.getFamilyName().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"           vcard:Given '" + i.getGivenName().replaceAll("\n", "").replaceAll("'", " ") + "'. \n" +
				"} \n"
				;
		
		qe = QueryExecutionFactory.create(query, TripleStore.model);
		results =  qe.execSelect();
		
		
		if( results.hasNext() ){
			invUri = results.next().toString();
			System.out.println("\n**************************************************************************");
	    	System.out.println("                  Inventor " + invUri + " exists!");
	    	System.out.println("**************************************************************************");
		    // Output query results    
		    ResultSetFormatter.out(System.out, results);
		    System.out.println("");
		}
		
		return invUri;
	}
	
	public String findAgent(Agent agt){
		String agtUri = "";
		
		 // Execute the query and obtain results
	    QueryExecution qe;
	    ResultSet results;
	    
		/* Check if agent exists */
		String query = 
				"PREFIX inpi: <http://givingsense.eu/pact/2016/primpi#>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"   +
				"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#>\n"  +
				"\n" +
				"SELECT ?Agent \n" +
				"WHERE{\n" +
				"	?Patent a inpi:PatentDocument; \n" +
				"				inpi:hasAgent ?Agent. \n" +
				"	?Agent rdfs:label '" + agt.getFamilyName().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"			vcard:ADR '" + agt.getAddress().toString().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"			vcard:Family '" + agt.getFamilyName().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"			vcard:Given '" + agt.getGivenName().replaceAll("\n", "").replaceAll("'", " ") + "'. \n" +
				"} \n"
				;
		
		qe = QueryExecutionFactory.create(query, TripleStore.model);
		results =  qe.execSelect();
		
		if( results.hasNext() ){
			agtUri = results.next().toString();
			System.out.println("\n**************************************************************************");
	    	System.out.println("                  Agent " + agtUri + " exists!");
	    	System.out.println("**************************************************************************");
		    // Output query results    
		    ResultSetFormatter.out(System.out, results);
		    System.out.println("");
		}
		
		return agtUri;
	}
}
