package com.inpi.jersey.resource;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.xml.sax.SAXException;

import com.inpi.storage.TripleStore;

@Path("patent")
public class PatentResource {

	@POST
    @Consumes(MediaType.TEXT_HTML)
    public String loadAllDefaultPatents(String path) throws IOException, SAXException {
		TripleStore.init();
    	TripleStore.loadZippedXmlData(path);
    	return "Dataset loaded";
    }
}
