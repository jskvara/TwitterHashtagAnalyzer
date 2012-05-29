package tha.model.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import tha.model.entity.NodeEntity;

/**
 *
 */
public class NodeDao {
	static {
		ObjectifyService.register(NodeEntity.class);
	}

	protected Objectify ofy;

	public NodeDao() {
		ofy = ObjectifyService.begin();
	}

	public NodeEntity getOrCreate(String hashtag, long snapshotId) {
		NodeEntity node = ofy.query(NodeEntity.class)
				.filter("hashtag", hashtag)
				.filter("snapshot", snapshotId)
				.limit(1)
				.get();

		if (node == null) {
			node = new NodeEntity(null, hashtag, snapshotId);
			put(node);
		}

		return node;
	}

	public NodeEntity get(long nodeId) {
		return ofy.find(NodeEntity.class, nodeId);
	}

	public Iterable<NodeEntity> getAllBySnapshot(long snapshotId) {
		return ofy.query(NodeEntity.class)
				.filter("snapshot", snapshotId)
				.fetch();
	}

	public void put(NodeEntity node) {
		ofy.put(node);
	}

	public int count(Long snapshotId) {
		return ofy.query(NodeEntity.class)
				.filter("snapshot", snapshotId)
				.count();
	}
}
