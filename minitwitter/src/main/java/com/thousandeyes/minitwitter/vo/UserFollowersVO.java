package com.thousandeyes.minitwitter.vo;

import java.util.List;

public class UserFollowersVO {
	
	private String user;
	
	private List<String> followers;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<String> getFollowers() {
		return followers;
	}

	public void setFollowers(List<String> followers) {
		this.followers = followers;
	}
	
	

}
