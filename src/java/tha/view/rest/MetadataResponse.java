package tha.view.rest;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import tha.view.xml.AuthorXml;
import tha.view.xml.MetadataXml;

/**
 * Metadata Response
 */
@Path("/metadata")
public class MetadataResponse {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getMetadata() {
		MetadataXml metadata = new MetadataXml();
		metadata.setName("Twitter");
		metadata.setDescription("Aplikace analyzuje tzv. #hastagy, které používají uživatelé sociální sítě Twitter. Pro zvolený hastag zjistí, jaké další hashtagy byly uživateli v tweetu použity. Toto prohledávání se dále provádí do nastavené hloubky s nově získanými hastagy, z čehož je vytvořen graf souvisejících hashtagů.");
		metadata.setLastUpdate(new Date(0));
		metadata.setUpdateInterval(7 * 24 * 3600);
		List<AuthorXml> authors = new LinkedList<AuthorXml>();
		authors.add(new AuthorXml("Jakub Škvára", "Praha"));
		authors.add(new AuthorXml("Jakub Římal", "Praha"));
		authors.add(new AuthorXml("Antonín Daněk", "České Budějovice"));
		metadata.setAuthors(authors);

		ResponseBuilder builder = Response.status(Response.Status.OK);
		builder.entity(metadata);

		return builder.build();
	}
}
