package com.thousandeyes.minitwitter.dao;

import java.util.List;

import com.thousandeyes.minitwitter.exception.MiniTwitterException;
import com.thousandeyes.minitwitter.vo.UserTweetVO;
import com.thousandeyes.minitwitter.vo.UserVO;

public interface IMiniTwitterDAO {
	
	
	public void followUser(UserVO userVO) throws MiniTwitterException;
	
	public void unfollowUser(UserVO userVO) throws MiniTwitterException;
	
	public List<String> getUserFollowsList(String user) throws MiniTwitterException;
	
	public List<String> getFollowersList(String user) throws MiniTwitterException;
	
	public List<UserTweetVO> getUserTweets(String user,String search) throws MiniTwitterException;

}
