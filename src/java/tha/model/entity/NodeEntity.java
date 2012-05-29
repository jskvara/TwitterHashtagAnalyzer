package tha.model.entity;

import javax.persistence.Id;

/**
 * Node entity
 */
public class NodeEntity implements Entity {

	@Id
	private Long id;
	private String hashtag;
	private Long snapshot;

	public NodeEntity() {
	}

	public NodeEntity(Long id, String hashtag, Long snapshot) {
		this.id = id;
		this.hashtag = hashtag;
		this.snapshot = snapshot;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public Long getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(Long snapshot) {
		this.snapshot = snapshot;
	}
}
