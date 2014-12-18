package de.gm.bookdb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/jsonServices")
public class ManageBookDBj1 {

	@GET
	@Path("/print/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book produceJSON( @PathParam("id") String id ) {

		Book st = new Book( "Perl Hacks", "chromatic", Integer.valueOf(id) );

		return st;
	}

}