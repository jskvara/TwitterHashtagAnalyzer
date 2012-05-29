package tha.view.xml;
import tha.model.entity.*;

import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Snapshot Entity
 */
@XmlRootElement(name = "snapshot")
public class SnapshotXml {

	private SnapshotEntity snapshotEntity;

	public SnapshotXml(SnapshotEntity snapshotEntity) {
		this.snapshotEntity = snapshotEntity;
	}

	public SnapshotXml() {
	}

	@XmlAttribute
	public Date getCreated() {
		return snapshotEntity.getCreated();
	}

	@XmlAttribute
	public Integer getEdge() {
		return snapshotEntity.getEdge();
	}

	@XmlAttribute
	public Integer getNode() {
		return snapshotEntity.getNode();
	}
}