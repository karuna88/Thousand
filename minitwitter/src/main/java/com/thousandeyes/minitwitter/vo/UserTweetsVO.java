package com.thousandeyes.minitwitter.vo;

import java.util.List;

public class UserTweetsVO {
	
	private String user;
	
	private List<String> tweets;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<String> getTweets() {
		return tweets;
	}

	public void setTweets(List<String> tweets) {
		this.tweets = tweets;
	}
	
	

}
