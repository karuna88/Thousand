package com.thousandeyes.minitwitter.vo;

import java.util.List;

public class UserFollowsVO {
	
	private String user;
	
	private List<String> follows;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<String> getFollows() {
		return follows;
	}

	public void setFollows(List<String> follows) {
		this.follows = follows;
	}

}
