package tha.model.entity;

import javax.persistence.Id;

/**
 * Edge entity
 */
public class EdgeEntity implements Entity {

	@Id
	private Long id = null;
	private Long node;
	private Long nodeTo;
	private Long tweetId;
	private Long snapshot;

	public EdgeEntity() {
	}

	public EdgeEntity( Long node, Long nodeTo, Long tweetId, Long snapshot) {
		this.node = node;
		this.nodeTo = nodeTo;
		this.tweetId = tweetId;
		this.snapshot = snapshot;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNode() {
		return node;
	}

	public void setNode(Long node) {
		this.node = node;
	}

	public Long getNodeTo() {
		return nodeTo;
	}

	public void setNodeTo(Long nodeTo) {
		this.nodeTo = nodeTo;
	}

	public Long getTweetId() {
		return tweetId;
	}

	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
	}

	public Long getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(Long snapshot) {
		this.snapshot = snapshot;
	}
}
