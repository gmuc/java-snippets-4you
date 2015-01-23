package de.gm.bookdb;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.clapper.util.misc.FileHashMap;

@Path("/jsonServices")
public class ManageBookDBj1 {
	
	public ManageBookDBj1() {
		super();
		try {
			this.bookDir = new FileHashMap<Integer, String>("/local/book_dir.map", 0);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	FileHashMap<Integer, String> bookDir;
	

	public ManageBookDBj1(FileHashMap<Integer, String> bookDir) {
		super();
			this.bookDir = bookDir;
	}
	
	@GET
	@Path("/print/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book produceJSON(@PathParam("id") String id) {

		Integer key = Integer.valueOf(id);
		String data = (String) bookDir.get( key );
		
		Book st = null;

		if (data != null) {
			String[] bookData = data.split(":");

			st = new Book(bookData[0], bookData[1], Integer.valueOf(id));
		}
		
		return st;
	}
	
	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJSON( Book book ) {

		String output = book.toString();
		
		Integer id = book.getId();
		String bookData = book.getAuthor() + ":" + book.getTitle();
		
		bookDir.put(id, bookData);

		try {
			bookDir.save();
			bookDir.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(output).build();
	}

}