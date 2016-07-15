package com.thousandeyes.minitwitter.service;

import java.util.List;
import java.util.Map;

import com.thousandeyes.minitwitter.exception.MiniTwitterException;
import com.thousandeyes.minitwitter.vo.UserFollowersVO;
import com.thousandeyes.minitwitter.vo.UserFollowsVO;
import com.thousandeyes.minitwitter.vo.UserTweetVO;
import com.thousandeyes.minitwitter.vo.UserTweetsVO;
import com.thousandeyes.minitwitter.vo.UserVO;

public interface IMiniTwitterService {
	
	public void followUser(UserVO userVO) throws MiniTwitterException;
	
	public void unfollowUser(UserVO userVO) throws MiniTwitterException;
	
    public UserFollowsVO getUserFollowsList(String user) throws MiniTwitterException;

    public UserFollowersVO getFollowersList(String user) throws MiniTwitterException;
    
    public List<UserTweetsVO> getUserTweets(String user,String search) throws MiniTwitterException;
	

}
