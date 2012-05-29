package tha.downloader;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tha.model.dao.EdgeDao;
import tha.model.dao.NodeDao;
import tha.model.dao.SnapshotDao;
import tha.model.entity.EdgeEntity;
import tha.model.entity.NodeEntity;
import tha.model.entity.SnapshotEntity;
import twitter4j.*;

public class HashtagDownloader {

	protected SnapshotDao snapshotDao = new SnapshotDao();
	protected NodeDao nodeDao = new NodeDao();
	protected EdgeDao edgeDao = new EdgeDao();
	protected Long snapshotId;

	protected static final int MAX_DEPTH = 2;
	protected static final String HASHTAG = "CVUT";

	public void download() {
		beginSnapshot();
		createSnapshot();
		finishSnapshot();
	}

	private void createSnapshot() {
		findAndSave(HASHTAG, 0);
	}

	private void findAndSave(String tag, int currentDepth){

		if( currentDepth >= MAX_DEPTH ){
			return;
		}

		// without toLowercase()
		NodeEntity currentRootTag = nodeDao.getOrCreate(tag, snapshotId);

		Query query = new Query("#" + currentRootTag.getHashtag().toLowerCase() );
		QueryResult result = searchTwitter(query);

		Set<String> nextLevelTags = new HashSet<String>();

		List<Tweet> tweets = result.getTweets();
		for (Tweet tweet : tweets) {
			saveTags(currentRootTag, nextLevelTags, tweet);
		}

		++currentDepth;

		// we need to go deeper :)
		for (String uiqueTag : nextLevelTags) {
			findAndSave(uiqueTag, currentDepth);
		}

	}

	private QueryResult searchTwitter(Query query){

		// wait some time before every other API query
		try {
			Thread.sleep(500); // milis
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Twitter twitter = new TwitterFactory().getInstance();
		try {
			return twitter.search(query);
		} catch (TwitterException e) {
			  System.err.println("Failed to search tweets: TRYING AGAIN " + e.getMessage() );
			  return searchTwitter(query);
		}
	}

	private void saveTags(NodeEntity currentRoot, Set<String> uniqueTags, Tweet tweet) {

		HashtagEntity[] hashtagEntities = tweet.getHashtagEntities();

		for (HashtagEntity hashtagEntity : hashtagEntities) {
			String tag = hashtagEntity.getText();

			// do not create edges like : CVUT - CVUT
			if( currentRoot.getHashtag().toLowerCase().equals( tag.toLowerCase() ) ) {
				continue;
			}

			if( !uniqueTags.contains( tag ) ) {

				NodeEntity nodeTo = nodeDao.getOrCreate( hashtagEntity.getText() , snapshotId);

				EdgeEntity edge = new EdgeEntity( currentRoot.getId(), nodeTo.getId(), tweet.getId(), snapshotId);
				edgeDao.put(edge);

				uniqueTags.add(tag);
			}

		}
	}

	protected void beginSnapshot() {
		SnapshotEntity snapshot = new SnapshotEntity(new Date(), 0, 0);
		snapshotDao.put(snapshot);
		snapshotId = snapshot.getId();
	}

	protected void finishSnapshot() {
		SnapshotEntity snapshot = snapshotDao.get(snapshotId);
		long duration = new Date().getTime() - snapshot.getCreated().getTime();
		snapshot.setDuration(duration);
		int nodesCount = new NodeDao().count(snapshotId);
		int edgesCount = new EdgeDao().count(snapshotId);
		snapshot.setNode(nodesCount);
		snapshot.setEdge(edgesCount);
		snapshotDao.put(snapshot);
	}
}
