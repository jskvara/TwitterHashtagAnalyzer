package tha.model.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import tha.model.entity.EdgeEntity;

/**
 * Edge DAO
 */
public class EdgeDao {
	static {
		ObjectifyService.register(EdgeEntity.class);
	}

	protected Objectify ofy;

	public EdgeDao() {
		ofy = ObjectifyService.begin();
	}

	public Iterable<EdgeEntity> getAllBySnapshot(long snapshotId) {
		return ofy.query(EdgeEntity.class)
				.filter("snapshot", snapshotId)
				.fetch();
	}

	public void put(EdgeEntity edge) {
		ofy.put(edge);
	}

	public int count(Long snapshotId) {
		return ofy.query(EdgeEntity.class)
				.filter("snapshot", snapshotId)
				.count();
	}
}
