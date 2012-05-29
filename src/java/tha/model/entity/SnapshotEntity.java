package tha.model.entity;

import java.util.Date;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Snapshot Entity
 */
@XmlRootElement(name = "snapshot")
public class SnapshotEntity implements Entity {

	@Id private Long id = null;
	private Long duration;
	private Date created;
	private Integer node;
	private Integer edge;

	public SnapshotEntity(Date created, Integer node, Integer edge) {
		this.created = created;
		this.node = node;
		this.edge = edge;
	}

	public SnapshotEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlAttribute
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@XmlAttribute
	public Integer getEdge() {
		return edge;
	}

	public void setEdge(Integer edge) {
		this.edge = edge;
	}

	@XmlAttribute
	public Integer getNode() {
		return node;
	}

	public void setNode(Integer node) {
		this.node = node;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
}