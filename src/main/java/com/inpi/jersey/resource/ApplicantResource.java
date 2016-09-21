package com.inpi.jersey.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import com.inpi.jersey.service.TripleStoreAccessor;

@Path("applicant")
public class ApplicantResource {

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPatentListByApplicant(  @FormParam("given-name") String givenname,
    										@FormParam("family-name") String familyname) {
    	
    	ResultSet results = TripleStoreAccessor.findPatentListByApplicant(givenname.toUpperCase(), familyname.toUpperCase());
    	String output = ResultSetFormatter.asText(results);
    	
        return output;
    }
}
