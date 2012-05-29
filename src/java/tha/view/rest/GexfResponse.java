package tha.view.rest;

import com.ojn.gexf4j.core.Gexf;
import com.ojn.gexf4j.core.impl.StaxGraphWriter;
import java.io.IOException;

import java.io.OutputStream;
import java.io.PrintStream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.StreamingOutput;
import tha.downloader.GexfExport;

/**
 * GEXF Response
 */
@Path("/gexf")
public class GexfResponse {

	GexfExport gexfExport = new GexfExport();

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public StreamingOutput getGexf() {
		return new StreamingOutput() {

			public void write(OutputStream out) throws IOException, WebApplicationException {
				try {
					Gexf gexfGraph = gexfExport.getGexf();
					StaxGraphWriter gexfWriter = new StaxGraphWriter();

					PrintStream writer = new PrintStream(out, true, "UTF-8");
					gexfWriter.writeToStream(gexfGraph, writer);

				} catch (Exception e) {
					throw new WebApplicationException(e);
				}
			}
		};
	}
}