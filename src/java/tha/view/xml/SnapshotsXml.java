package tha.view.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Snapshot entity
 *
 * Example:
<?xml version="1.0" encoding="UTF-8"?>
<snapshots>
   <snapshot created="2011-02-03T20:26:00.501Z" node="45" edge="387" />
   <snapshot created="2011-02-04T20:26:00.501Z" node="52" edge="402" />
   <snapshot created="2011-02-05T20:26:00.501Z" node="36" edge="320" />
   ...
</snapshots>
 */
@XmlRootElement(name = "snapshots")
public class SnapshotsXml {

	private List<SnapshotXml> snapshots;

	public SnapshotsXml() {
	}

	public SnapshotsXml(List<SnapshotXml> snapshots) {
		this.snapshots = snapshots;
	}

	@XmlElement(name = "snapshot")
	public List<SnapshotXml> getSnapshots() {
		return snapshots;
	}

	public void setSnapshots(List<SnapshotXml> snapshots) {
		this.snapshots = snapshots;
	}
}