package de.gm.bookdb;

import java.io.IOException;
import java.util.Map;

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
		// TODO Auto-generated constructor stub
	}

	Map bookDir;
	

	public ManageBookDBj1(Map bookDir) {
		super();
		try {
			this.bookDir = new FileHashMap("/local/book_dir.map");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/print/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book produceJSON( @PathParam("id") String id ) {

		Book st = new Book( "Perl Hacks", "chromatic", Integer.valueOf(id) );

		return st;
	}
	
	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consumeJSON( Book book ) {

		String output = book.toString();

		return Response.status(200).entity(output).build();
	}

}