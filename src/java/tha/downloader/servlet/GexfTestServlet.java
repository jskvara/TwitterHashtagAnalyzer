package tha.downloader.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tha.model.dao.EdgeDao;
import tha.model.dao.NodeDao;
import tha.model.dao.SnapshotDao;
import tha.model.entity.NodeEntity;
import tha.model.entity.SnapshotEntity;

public class GexfTestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6722603338080853754L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		SnapshotDao snapshotDao = new SnapshotDao();
		NodeDao nodeDao = new NodeDao();
		EdgeDao edgeDao = new EdgeDao();

		try {
			Iterable<SnapshotEntity> snapshotEntities = snapshotDao.getAll();
			for (SnapshotEntity se : snapshotEntities) {
				long snapshotId = se.getId();
				int nodesCount = nodeDao.count(snapshotId);
				int edgesCount = edgeDao.count(snapshotId);
				out.println("Snapshot: " + snapshotId + " (" + se.getCreated()
						+ "), Nodes: " + nodesCount + ", Edges: " + edgesCount);
				Iterable<NodeEntity> nodes = nodeDao.getAllBySnapshot(snapshotId);
				out.print("Node: ");
				for (NodeEntity ne : nodes) {
					out.print(ne.getHashtag() + ", ");
				}
				out.println();
			}
		} finally {
			out.close();
		}
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
