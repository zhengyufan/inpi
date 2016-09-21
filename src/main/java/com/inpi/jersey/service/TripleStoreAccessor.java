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
				"SELECT ?Patent_Uri ?Patent_Name \n" +
				"WHERE{\n" +
				"	?Patent_Uri a inpi:PatentDocument; \n" +
				"		rdfs:label ?Patent_Name; \n" +
				"		inpi:hasInventor ?Inventor. \n" +
				"	?Inventor rdfs:label '" + familyname.replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
//				"		vcard:ADR '" + i.getAddress().toString().replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"		vcard:Family '" + familyname.replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"		vcard:Given '" + givenname.replaceAll("\n", "").replaceAll("'", " ") + "'. \n" +
				"} \n"
				;
		
		qe = QueryExecutionFactory.create(query, TripleStore.model);
		results =  qe.execSelect();
		
		return results;
	}
	
	public static ResultSet findPatentListByApplicant(String givenname, String familyname){
		
		 // Execute the query and obtain results
	    QueryExecution qe;
	    ResultSet results;
	    
		/* Check if applicant exists */
		String query = 
				"PREFIX inpi: <http://givingsense.eu/pact/2016/primpi#>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"   +
				"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#>\n"  +
				"\n" +
				"SELECT ?Patent_Uri ?Patent_Name \n" +
				"WHERE{\n" +
				"	?Patent_Uri a inpi:PatentDocument; \n" +
				"		rdfs:label ?Patent_Name; \n" +
				"		inpi:hasApplicant ?Applicant. \n" +
				"	?Applicant rdfs:label '" + familyname.replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"		vcard:Family '" + familyname.replaceAll("\n", "").replaceAll("'", " ") + "'; \n" +
				"		vcard:Given '" + givenname.replaceAll("\n", "").replaceAll("'", " ") + "'. \n" +
				"} \n"
				;
		
		qe = QueryExecutionFactory.create(query, TripleStore.model);
		results =  qe.execSelect();
		
		return results;
	}
}
