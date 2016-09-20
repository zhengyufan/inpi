package com.inpi.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import com.inpi.model.Patent;

public class ZippedXmlReader {
	
	private File zipFile;
	
	public ZippedXmlReader(File zipFile){
		this.zipFile = zipFile;
	}
	
	public ArrayList<Patent> getPatentList() throws IOException, SAXException{
		
		ArrayList<Patent> patentList = new ArrayList<Patent>();
		
		ZipFile outerZipFile = new ZipFile(zipFile.getAbsolutePath()); 
		InputStream outerZFInputStream = new BufferedInputStream(new FileInputStream(zipFile.getAbsolutePath()));
		ZipInputStream outerZFZipInputStream = new ZipInputStream(outerZFInputStream);
		ZipEntry ze;
		
		while( (ze = outerZFZipInputStream.getNextEntry()) != null ){
            if (ze.isDirectory()) {                          //judge whether it's a folder
            } else {  
            	if(ze.getName().endsWith(".zip")){
            		
            		System.out.println("Extracting file : " + ze.getName());
            		
            		File tempFile = File.createTempFile("tempFile", "zip");
            		FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
            		IOUtils.copy( outerZipFile.getInputStream(ze), tempFileOutputStream);
            		tempFileOutputStream.close();
            		
            		ZipFile innerZipFile = new ZipFile(tempFile);
            		Enumeration<? extends ZipEntry> entries = innerZipFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        if( !entry.getName().equals("TOC.xml") ){
                        	System.out.println("Getting patent from : " + entry.getName());
                        	InputStream innerZipFileInputStream = innerZipFile.getInputStream(entry);
                        	File xmlFile = new File(entry.getName());
                        	OutputStream xmlOutputStream = new FileOutputStream(xmlFile);
                        	IOUtils.copy( innerZipFileInputStream, xmlOutputStream);
                        	xmlOutputStream.close();
                        	SingleXmlReader xr = new SingleXmlReader(xmlFile);
                        	Patent p = xr.getPatent();
                        	patentList.add(p);
                        	xmlFile.delete();
                        }
                        // InputStream entryIn = innerZipFile.getInputStream(entry);
                    }
                    tempFile.delete();
            	}
            }
		}
		
		return patentList;
	}

}
