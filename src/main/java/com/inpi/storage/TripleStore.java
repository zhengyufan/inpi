package com.inpi.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.xml.sax.SAXException;

import com.inpi.model.Agent;
import com.inpi.model.Applicant;
import com.inpi.model.Inventor;
import com.inpi.model.Patent;
import com.inpi.parser.RdfParser;
import com.inpi.parser.ZippedXmlReader;

public class TripleStore {

	public static Model model;
	
	public static void init(){
		model = ModelFactory.createDefaultModel();
	}
	
	public static void addRdfModel(Model newModel){
		model.add(newModel);
	}
	
	public static void write(String filename, String format) throws IOException{
		FileWriter out = new FileWriter( filename );
		model.write( out, "Turtle" );
		out.close();
	}
	
	public static void loadZippedXmlData(String path) throws IOException, SAXException{
		
		ZippedXmlReader zxr = new ZippedXmlReader(new File(path)); /* Zip Xml Reader*/
		RdfParser rp = new RdfParser(); /* RDF Parser */
		
		ArrayList<Patent> patentList = zxr.getPatentList();
		
		for( Patent p : patentList ){
			Model model = rp.parsePatentToRdfModel(p);
			addRdfModel(model);
		}
	}

}
