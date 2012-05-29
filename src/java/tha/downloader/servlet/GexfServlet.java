package tha.downloader.servlet;

import com.ojn.gexf4j.core.Gexf;
import com.ojn.gexf4j.core.Node;
import com.ojn.gexf4j.core.impl.GexfImpl;
import com.ojn.gexf4j.core.impl.StaxGraphWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tha.model.dao.EdgeDao;
import tha.model.dao.NodeDao;
import tha.model.dao.SnapshotDao;
import tha.model.entity.EdgeEntity;
import tha.model.entity.NodeEntity;
import tha.model.entity.SnapshotEntity;

/**
 * Gexf export servlet
 */
public class GexfServlet extends HttpServlet {

	protected SnapshotDao snapshotDao = new SnapshotDao();
	protected NodeDao nodeDao = new NodeDao();
	protected EdgeDao edgeDao = new EdgeDao();

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		try {
			if (request.getParameter("snapshot") == null ||
					request.getParameter("snapshot").equals("")) {
				Iterable<SnapshotEntity> snapshotEntities = snapshotDao.getAll();

				for (SnapshotEntity se : snapshotEntities) {
					long snapshotId = se.getId();
					String link = "<a href=\"?snapshot=" + snapshotId + "\">"
							+ snapshotId + "</a><br />";
					out.write(link.getBytes());
				}
			} else {
				String snapshotId = request.getParameter("snapshot");
				Long id = Long.valueOf(snapshotId);

				Gexf gexfGraph = getSnapshot(id);
				StaxGraphWriter gexfWriter = new StaxGraphWriter();

				PrintStream writer = new PrintStream(out, true, "UTF-8");
				gexfWriter.writeToStream(gexfGraph, writer);
			}
		} finally {
			out.close();
		}
	}

	protected Gexf getSnapshot(long snapshotId) {
		Gexf gexf = new GexfImpl();
		SnapshotEntity se = snapshotDao.get(snapshotId);
		Date snapshotDate = se.getCreated();
		Map<Long, Node> allNodes = new HashMap<Long, Node>();

		for (NodeEntity ne : nodeDao.getAllBySnapshot(snapshotId)) {
			Long nodeId = ne.getId();
			Node n = gexf.getGraph().createNode("N"+ nodeId)
					.setLabel(ne.getHashtag())
					.setStartDate(snapshotDate)
					.setEndDate(snapshotDate);
			allNodes.put(nodeId, n);
		}

		for (EdgeEntity ee : edgeDao.getAllBySnapshot(snapshotId)) {
			Node source = allNodes.get(ee.getNode());
			Node target = allNodes.get(ee.getNodeTo());

			source.connectTo("E" + ee.getId(), target);
		}

		return gexf;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
}
