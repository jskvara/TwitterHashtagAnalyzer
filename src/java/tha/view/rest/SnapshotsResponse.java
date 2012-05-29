package tha.view.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import tha.model.dao.SnapshotDao;
import tha.model.entity.SnapshotEntity;
import tha.view.xml.SnapshotXml;
import tha.view.xml.SnapshotsXml;

/**
 * Snapshots Response
 */
@Path("/snapshots")
public class SnapshotsResponse {

	protected static final String CHART_URL = "http://chart.apis.google.com/chart";
	protected SnapshotDao snapshotDao = new SnapshotDao();

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getSnapshots(@QueryParam("type") String type) {
		SnapshotsXml snapshotsEntity = new SnapshotsXml();

		Iterable<SnapshotEntity> snapshotEntities = snapshotDao.getAll();
		List<SnapshotXml> snapshots = new LinkedList<SnapshotXml>();
		for (SnapshotEntity s : snapshotEntities) {
			snapshots.add(new SnapshotXml(s));
		}
		snapshotsEntity.setSnapshots(snapshots);

		ResponseBuilder builder = Response.status(Response.Status.OK);
		builder.entity(snapshotsEntity);

		return builder.build();
	}

	@GET
	@Produces("image/png")
	public Response getSnapshotsImage() {

		InputStream is = null;
		OutputStream os = null;
		try {

			StringBuilder dates = new StringBuilder();
			StringBuilder nodes = new StringBuilder();
			StringBuilder edges = new StringBuilder();
			SimpleDateFormat formatter = new SimpleDateFormat("d.M.yyyy");
			Iterable<SnapshotEntity> snapshotEntities = snapshotDao.getAll();
			Iterator<SnapshotEntity> it = snapshotEntities.iterator();
			SnapshotEntity s = null;
			int maxNodes = 0;
			int maxEdges = 0;
			int count = 0;
			while (it.hasNext()) {
				s = it.next();
				if (s.getCreated() != null) {
					dates.append(formatter.format(s.getCreated()));
					count++;
				}
				if (s.getNode() != null) {
					if (maxNodes < s.getNode()) {
						maxNodes = s.getNode();
					}
					nodes.append(s.getNode());
				}
				if (s.getEdge() != null) {
					if (maxEdges < s.getEdge()) {
						maxEdges = s.getEdge();
					}
					edges.append(s.getEdge());
				}
				if (it.hasNext()) {
					dates.append("|");
					nodes.append(",");
					edges.append(",");
				}
			}
			System.out.println("Dates: "+ dates.toString());
			System.out.println("Nodes: "+ nodes.toString());
			System.out.println("Edges: "+ edges.toString());

			// http://imagecharteditor.appspot.com/
			String chartUrl = new StringBuilder(CHART_URL).append("?")
				.append("chxl=").append(encode("2:|"))
					.append(encode(dates.toString())) // axes labels
				.append("&chxp=").append(encode("2,1,2,3")) // axes label position (except first number
				.append("&chxr=").append("0,0,").append(maxNodes)
					.append(encode("|1,0,")).append(maxEdges)
					.append(encode("|2,0,")).append(count) // axes scale [position,min,max]
				.append("&chxs=").append(encode("0,0000FF,11.5,0,lt,676767|1,FF0000,10.5,0,lt,676767")) // y axe
				.append("&chxt=").append(encode("y,y,x")) // axes
				.append("&chs=").append(encode("440x220")) // chart size
				.append("&cht=").append(encode("lc")) // chart type
				.append("&chco=").append(encode("3072F3,FF0000")) // lines color
				.append("&chds=").append("0,").append(maxEdges)
					.append(",0,").append(maxEdges) // max data
				.append("&chd=").append("t:") // data
					.append(nodes.toString()).append(encode("|"))
					.append(edges.toString())
				.append("&chdl=").append(encode("Nodes|Edges")) // lines label
				.append("&chdlp=").append(encode("b")) // legend position - bottom
				.append("&chls=").append(encode("1|1")) // lines width
				.toString();
			System.out.println("Url: "+ chartUrl);

			URL url = new URL(chartUrl);
			//BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			is = url.openConnection().getInputStream();
//			os = new ByteArrayOutputStream();
//
//			byte[] buffer = new byte[1000];
//			int n = 0;
//
//			while (n >= 0) {
//				n = is.read(buffer, 0, buffer.length);
//				os.write(buffer, 0, buffer.length);
//			}

//			ImageIO.write((BufferedImage) image, "png", out);
//			final byte[] imgData = out.toByteArray();
//			final InputStream bigInputStream = new ByteArrayInputStream(imgData);


			return Response.ok(is).build();
		} catch (MalformedURLException e) {
			System.err.println(e);
			return Response.noContent().build();
		} catch (IOException e) {
			System.err.println(e);
			return Response.noContent().build();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	protected String encode(String s) throws UnsupportedEncodingException {
		return URLEncoder.encode(s, "UTF-8");
	}
}