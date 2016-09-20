package com.inpi.test;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.inpi.storage.TripleStore;

public class Test1 {

	public static void main(String[] args) throws SAXException, IOException{
		
		String zippedXmlPath = "src/main/resources/FR_FRAMDST36_2014_01.zip";
		TripleStore.init(); // Triple Store
		TripleStore.loadZippedXmlData(zippedXmlPath); // Load Default Dataset into Triple Store
		
		String filename = "output.rdf"; // Write all the triples in Triple Store into a file
		TripleStore.write(filename, "Turtle"); 
	}

}
