package tha.downloader;

import com.ojn.gexf4j.core.Edge;
import com.ojn.gexf4j.core.Gexf;
import com.ojn.gexf4j.core.Node;
import com.ojn.gexf4j.core.impl.GexfImpl;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tha.model.dao.EdgeDao;
import tha.model.dao.NodeDao;
import tha.model.dao.SnapshotDao;
import tha.model.entity.EdgeEntity;
import tha.model.entity.NodeEntity;
import tha.model.entity.SnapshotEntity;

/**
 * Gexf format export
 */
public class GexfExport {

	protected SnapshotDao snapshotDao = new SnapshotDao();
	protected NodeDao nodeDao = new NodeDao();
	protected EdgeDao edgeDao = new EdgeDao();

	public Gexf getGexf() {
		Gexf gexf = new GexfImpl();
		Iterable<SnapshotEntity> snapshotEntities = snapshotDao.getAll();

		Map<String, Node> allNodes = new HashMap<String, Node>();
		List<Map<Long, Node>> snapshotNodes = new LinkedList<Map<Long, Node>>();
		int i = 0;
		for (SnapshotEntity se : snapshotEntities) {
			Map<Long, Node> nodes = new HashMap<Long, Node>();
			long snapshotId = se.getId();
			Date snapshotDate = se.getCreated();

			for (NodeEntity ne : nodeDao.getAllBySnapshot(snapshotId)) {
				Long nodeId = ne.getId();
				String hashtag = ne.getHashtag();
				Node n;

				if (!allNodes.containsKey(hashtag)) {
					n = gexf.getGraph().createNode("N"+ nodeId)
						.setLabel(ne.getHashtag())
						.setStartDate(snapshotDate);
					allNodes.put(hashtag, n);
				} else {
					n = allNodes.get(hashtag);
				}

				nodes.put(ne.getId(), n);
			}
			snapshotNodes.add(nodes);

			// set end date
			if (i > 0) {
				Map<Long, Node> previousNodes = snapshotNodes.get(i-1);
				Map<Long, Node> currentNodes = snapshotNodes.get(i);
				for (Long pn : previousNodes.keySet()) {
					if (!currentNodes.containsKey(pn)) {
						previousNodes.get(pn).setEndDate(snapshotDate);
					}
				}
			}

			i++;
		}

		Map<String, Edge> allEdges = new HashMap<String, Edge>();
		List<Map<String, Edge>> snapshotEdges = new LinkedList<Map<String, Edge>>();
		i = 0;
		for (SnapshotEntity se : snapshotEntities) {
			Map<String, Edge> edges = new HashMap<String, Edge>();
			long snapshotId = se.getId();
			Date snapshotDate = se.getCreated();

			for (EdgeEntity ee : edgeDao.getAllBySnapshot(snapshotId)) {
				NodeEntity n1 = nodeDao.get(ee.getNode());
				NodeEntity n2 = nodeDao.get(ee.getNodeTo());
				Node source = allNodes.get(n1.getHashtag());
				Node target = allNodes.get(n2.getHashtag());
				String key1 = n1.getHashtag() + "-" + n2.getHashtag();
				String key2 = n2.getHashtag() + "-" + n1.getHashtag();

				Edge e;
				if (!allEdges.containsKey(key1) &&
						!allEdges.containsKey(key2)) {
					e = source.connectTo("E" + ee.getId(), target);
					//e.setLabel("Twett:" + ee.getTweetId());
					e.setStartDate(snapshotDate);

					allEdges.put(key1, e);
				} else {
					e = allEdges.get(key1);
					if (e == null) {
						e = allEdges.get(key2);
					}
				}

				edges.put(n1.getHashtag() + "-" + n2.getHashtag(), e);
			}
			snapshotEdges.add(edges);

			// set end date
//			if (i > 0) {
//				Map<String, Edge> previousEdges = snapshotEdges.get(i-1);
//				Map<String, Edge> currentEdges = snapshotEdges.get(i);
//				for (Edge e : previousEdges.values()) {
//					if (!currentEdges.containsValue(e)) {
//						//e.getSource().get
//						//allEdges.get(e.getId())
//						e.setEndDate(snapshotDate);
//					}
//				}
//			}
//			i++;
		}

		return gexf;
	}
}
