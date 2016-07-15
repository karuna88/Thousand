package com.thousandeyes.minitwitter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.thousandeyes.minitwitter.dao.IMiniTwitterDAO;
import com.thousandeyes.minitwitter.exception.MiniTwitterApplicationException;
import com.thousandeyes.minitwitter.exception.MiniTwitterException;
import com.thousandeyes.minitwitter.vo.UserFollowersVO;
import com.thousandeyes.minitwitter.vo.UserFollowsVO;
import com.thousandeyes.minitwitter.vo.UserTweetVO;
import com.thousandeyes.minitwitter.vo.UserTweetsVO;
import com.thousandeyes.minitwitter.vo.UserVO;

@Service("miniTwitterService")
public class MiniTwitterService implements IMiniTwitterService{
	
	@Autowired
	@Qualifier("miniTwitterDAO")
	private IMiniTwitterDAO miniTwitterDAO;
	
	public void followUser(UserVO userVO) throws MiniTwitterException
	{
		try
		{
			getIMiniTwitterDAO().followUser(userVO);
		}catch(MiniTwitterException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			throw new MiniTwitterApplicationException("Application Error");
		}
	}
	
	public void unfollowUser(UserVO userVO) throws MiniTwitterException
	{
		try
		{
			getIMiniTwitterDAO().unfollowUser(userVO);
		}catch(MiniTwitterException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			throw new MiniTwitterApplicationException("Application Error");
		}
	}
	
    public UserFollowsVO getUserFollowsList(String user) throws MiniTwitterException
    {
    	UserFollowsVO userFollowsVO = new UserFollowsVO();
    	try
    	{
    		List<String> followsList = getIMiniTwitterDAO().getUserFollowsList(user);
    		userFollowsVO.setUser(user);
    		userFollowsVO.setFollows(followsList);
    	}
    	catch(MiniTwitterException ex)
		{
			throw ex;
		}
    	catch(Exception ex)
		{
			throw new MiniTwitterApplicationException("Application Error");
		}
    	 return userFollowsVO;
    }
    
    
    public UserFollowersVO getFollowersList(String user) throws MiniTwitterException
    {
    	UserFollowersVO userFollowersVO = new UserFollowersVO();
    	try
    	{
    	List<String> followersList = getIMiniTwitterDAO().getFollowersList(user);
    	  userFollowersVO.setUser(user);
    	  userFollowersVO.setFollowers(followersList);
	    }
		catch(MiniTwitterException ex)
		{
			throw ex;
		}
    	catch(Exception ex)
		{
			throw new MiniTwitterApplicationException("Application Error");
		}
    	 return userFollowersVO;
    }
    
    public List<UserTweetsVO> getUserTweets(String user,String search) throws MiniTwitterException
    {
    	List<UserTweetVO> userTweetList =  getIMiniTwitterDAO().getUserTweets(user, search);
    	List<UserTweetsVO> combinedTweetsList = new ArrayList<UserTweetsVO> ();
    	if(userTweetList !=null && userTweetList.size() > 0)
    	{
    		combinedTweetsList = combineUserTweets(userTweetList);
    	}
    	
    	return combinedTweetsList;
    	
    }
    
    public List<UserTweetsVO> combineUserTweets(List<UserTweetVO> list)
    {
    	Map<String,String> userTweetsMap= new HashMap<String,String>();
    	List<UserTweetsVO> userTweetsList = new ArrayList<UserTweetsVO>();
    	
    	for(UserTweetVO userTweetVO : list)
    	{
    		if(userTweetsMap.get(userTweetVO.getUser()) == null)
    		{
    			userTweetsMap.put(userTweetVO.getUser(), userTweetVO.getTweet());
    		}
    		else
    		{
    			userTweetsMap.put(userTweetVO.getUser(), userTweetsMap.get(userTweetVO.getUser())+","+userTweetVO.getTweet());
    		}
    	}
    		
			Iterator<String> it = userTweetsMap.keySet().iterator();
			String key = null;
			List<String> tweetsList= null;
			while(it.hasNext())
			{
				key = it.next();
				tweetsList = Arrays.asList(userTweetsMap.get(key).split(","));
				UserTweetsVO userTweetsVO = new UserTweetsVO();
				userTweetsVO.setUser(key);
				userTweetsVO.setTweets(tweetsList);
				userTweetsList.add(userTweetsVO);
			}
    	
    	
    	return userTweetsList;
    	
    }
	public IMiniTwitterDAO getIMiniTwitterDAO() {
		return miniTwitterDAO;
	}

	public void setIMiniTwitterDAO(IMiniTwitterDAO miniTwitterDAO) {
		this.miniTwitterDAO = miniTwitterDAO;
	}
	
	
	

}
