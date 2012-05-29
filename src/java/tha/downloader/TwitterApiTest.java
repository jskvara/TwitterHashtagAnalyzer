package tha.downloader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterApiTest {

	private int maxDepth;
	private StringBuilder data = new StringBuilder();

	private static final String BREAK_LINE = "\n";

	public TwitterApiTest( String startTag, int maxDepth ){
		this.maxDepth = maxDepth;
		findAndPrint(startTag, 0);
	}

	private void findAndPrint(String tag, int currentDepth){

		if( currentDepth >= maxDepth ){
			return;
		}

	    Twitter twitter = new TwitterFactory().getInstance();
	    Query query = new Query("#" + tag);

		try {
			QueryResult result = twitter.search(query);
			Set<String> uniqueTags = new HashSet<String>();

			List<Tweet> tweets = result.getTweets();
		    for (Tweet tweet : tweets) {
		    	addUniqueTags(tag, uniqueTags, tweet.getHashtagEntities());
		    }

		    data.append(BREAK_LINE);
		    data.append("--- level " + currentDepth + " ---"+ BREAK_LINE);

		    printRelevantTags("#" + tag ,uniqueTags);

		    ++currentDepth;
		    // we need to go deepr :)
		    for (String uiqueTag : uniqueTags) {
				findAndPrint(uiqueTag, currentDepth);
			}


		} catch (TwitterException e) {
			  e.printStackTrace();
	          data.append("Failed to search tweets: " + e.getMessage() + BREAK_LINE);
		}

	}

	private void printRelevantTags(String referenceTag, Set<String> uniqueTags) {
		data.append(referenceTag);
                data.append(BREAK_LINE);
		for (String tag : uniqueTags) {
			data.append(" -- " + tag + BREAK_LINE);
		}
	}

	private void addUniqueTags(String searchinchFor, Set<String> uniqueTags,HashtagEntity[] hashtagEntities) {
		for (HashtagEntity hashtagEntity : hashtagEntities) {
			String tag = hashtagEntity.getText().toLowerCase();
			// do not add currently searched tag, it would make edges like : CVUT - CVUT with infinite loops
			if( searchinchFor.equals( tag ) ) {
				continue;
			}

			uniqueTags.add(tag);
		}
	}

	public char[] getData() {
		return data.toString().toCharArray();
	}

}
