package com.inpi.jersey.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.xml.sax.SAXException;

import com.inpi.jersey.service.OperatingSystemDetector;
import com.inpi.storage.TripleStore;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class UploadFileResource {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile( @FormDataParam("file") InputStream uploadedInputStream,
								@FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException, SAXException {

		String uploadedFileLocation = "";
		
		if(OperatingSystemDetector.isWindows()){
			uploadedFileLocation = "C://" + fileDetail.getFileName();	
		}
		else if(OperatingSystemDetector.isMac()){
			uploadedFileLocation = "file:///" + fileDetail.getFileName();
		}
		else if(OperatingSystemDetector.isUnix()){
			uploadedFileLocation = "/" + fileDetail.getFileName();
		}

		// save to file
		writeToFile(uploadedInputStream, uploadedFileLocation);
		
		// Load zip file to Triple Store
		TripleStore.init();
		TripleStore.loadZippedXmlData(uploadedFileLocation);
		
		String output = "<h1>File is successfully uploaded. Data has been transformed into RDF model and saved in triple store!</h1>" + 
						"<p>To search for patent, please click on the link below :</p> " + 
						"<li><a href='../../search-patent-list-by-inventor.jsp'>Search patent list by inventor.</a></li>";

		return Response.status(200).entity(output).build();

	}

	// save uploaded file to new location on the server
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}