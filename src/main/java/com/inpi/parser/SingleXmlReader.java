package com.inpi.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.inpi.model.Agent;
import com.inpi.model.Applicant;
import com.inpi.model.Inventor;
import com.inpi.model.Patent;
import com.inpi.model.PostalAddress;  

/**
 * XmlReader reads from a Xml file and transform into a Patent
 * @author Administrator
 *
 */

public class SingleXmlReader {
	
	private static File file;
	
	public SingleXmlReader(File file){
		this.file = file;
	}
	
	public Patent getPatent() throws SAXException{
		Document doc = getDocument();
		Patent p = new Patent();
		
		/* Set the attributes of a patent */
	    Element e = (Element) doc.getElementsByTagName("fr-patent-document").item(0);
	    String title = doc.getElementsByTagName("invention-title").item(0).getTextContent();
	    
	    p.setId(e.getAttribute("id"));
	    p.setDocNumber(e.getAttribute("doc-number"));
	    p.setCountryCode(e.getAttribute("country"));
	    p.setKind(e.getAttribute("kind"));
	    p.setStatus(e.getAttribute("status"));
	    p.setTitle(title);
	    
	    ArrayList<Applicant> appList = getApplicantList();
	    ArrayList<Inventor> invList = getInventorList();
	    ArrayList<Agent> agtList = getAgentList();
	    
	    p.setApplicantList(appList);
	    p.setInventorList(invList);
	    p.setAgentList(agtList);
	    
		return p;
	}
	
	/**
	 * 
	 * @return Document builder to read XML file
	 * @throws SAXException 
	 */
	private static Document getDocument() throws SAXException{
	    //写XML文件要用到  
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    //允许名字空间  
	    factory.setNamespaceAware(true);  
	    //允许验证  
	    factory.setValidating(false);  
	    //获得DocumentBuilder的一个实例  
	    DocumentBuilder builder = null;  
	    try {  
	        builder = factory.newDocumentBuilder();  
	    } catch(ParserConfigurationException pce) {  
	        System.err.println(pce);  
	        System.exit(1);  
	    }  
	    Document doc = null;  
	    try {  
	        doc = builder.parse(file.getAbsolutePath());  
	    } catch(DOMException dom) {  
	        System.err.println(dom.getMessage());  
	        System.exit(1);  
	    } catch (IOException ioe) {  
	        System.err.println(ioe);  
	        System.exit(1);       
	    } 
	    return doc;
	}
	
	private static PostalAddress parseAddress(Element address)  throws SAXException  {
		PostalAddress padd = new PostalAddress();
    	String add1 = address.getElementsByTagName("address-1").item(0).getTextContent();
    	//String add2 = address.getElementsByTagName("address-2").item(0).getTextContent();
    	String city = address.getElementsByTagName("city").item(0).getTextContent();
    	String postcode = address.getElementsByTagName("postcode").item(0).getTextContent();
    	String country = address.getElementsByTagName("country").item(0).getTextContent();
    	
    	padd.setAddress1(add1);
    	//padd.setAddress2(add2);
    	padd.setCity(city);
    	padd.setPostcode(postcode);
    	padd.setCountry(country);
		return padd;	
	}
	
	private ArrayList<Applicant> getApplicantList() throws SAXException{
		Document doc = getDocument();
		ArrayList<Applicant> appList = new ArrayList<Applicant>(); 
		
	    /* Set the attributes of applicants */
	    NodeList appNodes = doc.getElementsByTagName("applicant");
	    for(int i = 0; i < appNodes.getLength();i++ ){
	    	Applicant a = new Applicant();
	    	Element appElement = (Element)appNodes.item(i);
	    	Element faddressbook = (Element)appElement.getElementsByTagName("addressbook").item(0);
	    	String appFamilyName = faddressbook.getElementsByTagName("last-name").item(0).getTextContent();
	    	String appGivenName = faddressbook.getElementsByTagName("first-name").item(0).getTextContent();
	    	Element appAddress = (Element)faddressbook.getElementsByTagName("address").item(0);
	    	String appid = faddressbook.getElementsByTagName("iid").item(0).getTextContent();
	    	
	    	a.setFamilyName(appFamilyName);
	    	a.setGivenName(appGivenName);
	    	a.setAddress(parseAddress(appAddress));
	    	a.setId(appid);
	    	
	    	appList.add(a);
	    }
	    
		return appList;
	}
	
	private ArrayList<Inventor> getInventorList() throws SAXException{
		Document doc = getDocument();
		ArrayList<Inventor> invList = new ArrayList<Inventor>(); 
		 /* Set the attributes of inventors */
	    NodeList invNodes = doc.getElementsByTagName("inventor");
	    for(int j = 0; j < invNodes.getLength();j++ ){
	    	Inventor inv = new Inventor();
	    	Element invElement = (Element)invNodes.item(j);
	    	Element faddressbook = (Element)invElement.getElementsByTagName("addressbook").item(0);
	    	String invFamilyName = faddressbook.getElementsByTagName("last-name").item(0).getTextContent();
	    	String invGivenName = faddressbook.getElementsByTagName("first-name").item(0).getTextContent();
	    	Element invAddress = (Element)faddressbook.getElementsByTagName("address").item(0);
	    	
	    	inv.setFamilyName(invFamilyName);
	    	inv.setGivenName(invGivenName);
	    	inv.setAddress(parseAddress(invAddress));	
	    	inv.setId();
	    	
	    	invList.add(inv);
	    }
	    return invList;
	}
		
	private ArrayList<Agent> getAgentList() throws SAXException{
		Document doc = getDocument();
		ArrayList<Agent> agtList = new ArrayList<Agent>(); 
		 /* Set the attributes of agents */
	    NodeList agtNodes = doc.getElementsByTagName("agent");
	    for(int j = 0; j < agtNodes.getLength();j++ ){
	    	Agent agt = new Agent();
	    	Element agtElement = (Element)agtNodes.item(j);
	    	Element faddressbook = (Element)agtElement.getElementsByTagName("addressbook").item(0);
	    	String agtFamilyName = faddressbook.getElementsByTagName("last-name").item(0).getTextContent();
	    	String agtGivenName = faddressbook.getElementsByTagName("first-name").item(0).getTextContent();
	    	Element agtAddress = (Element)faddressbook.getElementsByTagName("address").item(0);
	    	String agtid = faddressbook.getElementsByTagName("iid").item(0).getTextContent();
	    	
	    	agt.setFamilyName(agtFamilyName);
	    	agt.setGivenName(agtGivenName);
	    	agt.setAddress(parseAddress(agtAddress));	
	    	agt.setId(agtid);
	    	
	    	agtList.add(agt);
	    }
	    return agtList;
	}
}


