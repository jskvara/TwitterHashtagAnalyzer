package tha.model.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import tha.model.entity.SnapshotEntity;

/**
 * Snapshot DAO
 */
public class SnapshotDao {
	static {
		ObjectifyService.register(SnapshotEntity.class);
	}

	protected Objectify ofy;

	public SnapshotDao() {
		ofy = ObjectifyService.begin();
	}

	public SnapshotEntity get(Long id) {
		SnapshotEntity snapshot = ofy.find(SnapshotEntity.class, id);

		return snapshot;
	}

	public Iterable<SnapshotEntity> getAll() {
		return ofy.query(SnapshotEntity.class).fetch();
	}

	public void put(SnapshotEntity snapshot) {
		ofy.put(snapshot);
	}

	public void delete(SnapshotEntity snapshot) {
		ofy.delete(snapshot);
	}

}