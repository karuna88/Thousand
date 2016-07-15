package com.thousandeyes.minitwitter.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.thousandeyes.minitwitter.exception.MiniTwitterException;
import com.thousandeyes.minitwitter.exception.MiniTwitterUserException;
import com.thousandeyes.minitwitter.vo.UserTweetVO;
import com.thousandeyes.minitwitter.vo.UserVO;

@Repository("miniTwitterDAO")
public class MiniTwitterDAO implements IMiniTwitterDAO{
	
	@Autowired
	@Qualifier("miniTwitterJDBCTemplate")
	MiniTwitterJDBCTemplate miniTwitterJDBCTemplate;
	
	public void followUser(UserVO userVO) throws MiniTwitterException
	{
		checkUsers(userVO.getUser(),userVO.getFollowUser());
		
		String sql="SELECT USER_ID,FOLLOWS_USER_ID FROM USER_FOLLOWS WHERE USER_ID = ? AND FOLLOWS_USER_ID= ?";
		List<Map<String,Object>> userFollowsList = getMiniTwitterJDBCTemplate().getJdbcTemplate().queryForList(sql,userVO.getUser(),
				userVO.getFollowUser());
		if(userFollowsList !=null && userFollowsList.size() > 0)
		{
			throw new MiniTwitterUserException(userVO.getUser()+" already follows "+userVO.getFollowUser());
		}
		sql="INSERT INTO USER_FOLLOWS(USER_ID,FOLLOWS_USER_ID) VALUES(?,?)";
		getMiniTwitterJDBCTemplate().getJdbcTemplate().update(sql,userVO.getUser(),
				userVO.getFollowUser());
	}
	
	public void unfollowUser(UserVO userVO) throws MiniTwitterException
	{
		checkUsers(userVO.getUser(),userVO.getUnfollowUser());
		
		String sql = "SELECT USER_ID,FOLLOWS_USER_ID FROM USER_FOLLOWS WHERE USER_ID = ? AND FOLLOWS_USER_ID = ?";
		List<Map<String, Object>> userFollowersList = getMiniTwitterJDBCTemplate().getJdbcTemplate().queryForList(sql,userVO.getUser(),
				userVO.getUnfollowUser());
		if(userFollowersList !=null && userFollowersList.size() == 0)
		{
			throw new MiniTwitterUserException(userVO.getUser()+" is not following "+userVO.getUnfollowUser());
		}
		sql="DELETE FROM USER_FOLLOWS WHERE USER_ID = ? AND FOLLOWS_USER_ID = ?";
		getMiniTwitterJDBCTemplate().getJdbcTemplate().update(sql,userVO.getUser(),
				userVO.getUnfollowUser());
	}
	
	public List<String> getUserFollowsList(String user) throws MiniTwitterException
    {
			checkUser(user);
			String sql = "SELECT FOLLOWS_USER_ID FROM USER_FOLLOWS WHERE USER_ID = ?";
			List<String> followsList = getMiniTwitterJDBCTemplate().getJdbcTemplate().queryForList(sql, String.class, user);
			return followsList;
    }
	
	public List<String> getFollowersList(String user) throws MiniTwitterException
    {
			checkUser(user);
			String sql = "SELECT USER_ID FROM USER_FOLLOWS WHERE FOLLOWS_USER_ID = ?";
			List<String> followersList = getMiniTwitterJDBCTemplate().getJdbcTemplate().queryForList(sql, String.class, user);
			return followersList;
		
    }
	
	public List<UserTweetVO> getUserTweets(String user,String search) throws MiniTwitterException
	{
			checkUser(user);
			List<String> userList = getUserFollowsList(user);
			userList.add(user);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userList", userList);
			List<UserTweetVO> tweets = null;
			if(search !=null && !search.equalsIgnoreCase(""))
			{
				String sql = "SELECT USER_ID,TWEET FROM USER_TWEETS WHERE USER_ID IN (:userList) AND TWEET LIKE :search";
				
				paramMap.put("search", "%" + search + "%");
				tweets = getMiniTwitterJDBCTemplate().
						getNamedParameterJdbcTemplate().query(sql, paramMap, new UserTweetVORowMapper());
					
			}
			else
			{
				String sql = "SELECT USER_ID, TWEET FROM USER_TWEETS WHERE USER_ID IN (:userList)";
				
				tweets = getMiniTwitterJDBCTemplate().
						getNamedParameterJdbcTemplate().query(sql, paramMap, new UserTweetVORowMapper());
				
			}
			
			return tweets;
		
	}
	
	public List<Map<String, Object>> checkUser(String user) throws MiniTwitterUserException
	{
		String sql = "SELECT USER_ID FROM USER_ACCOUNT WHERE USER_ID = ?";
		List<Map<String,Object>> userList = getMiniTwitterJDBCTemplate().getJdbcTemplate().queryForList(sql,user);
		
		if(userList !=null && userList.size() > 0)
		{
			return  userList;
		}else
		{
			throw new MiniTwitterUserException(user +": Invalid User, Please enter correct user details");
		}
	}
	
	public List<Map<String,Object>> checkUsers(String user1, String user2) throws MiniTwitterException
	{
		
		String sql = "SELECT USER_ID FROM USER_ACCOUNT WHERE USER_ID IN (:userList)";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		List<String> userList = new ArrayList<String>();
		userList.add(user1);
		userList.add(user2);
		paramMap.put("userList", userList);
	
		List<Map<String,Object>>  usrlist = getMiniTwitterJDBCTemplate().getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
		
		if(usrlist != null && usrlist.size() == 1)
		{
			if(!usrlist.get(0).get("USER_ID").equals(user1))
			{
				throw new MiniTwitterUserException(user1+": Invalid User, Please enter correct user details");
			}
			if(!usrlist.get(0).get("USER_ID").equals(user2))
			{
				throw new MiniTwitterUserException(user2+": Invalid User, Please enter correct user details");
			}
		}
		if(usrlist != null && usrlist.size() == 0)
		{
			throw new MiniTwitterUserException(user1+": Invalid User and "+user2+ ": Invalid  User, Please enter correct user details");
		}
		if(user1.equals(user2))
		{
			throw new MiniTwitterUserException("Both users are same, Please enter correct user details");
		}
		
		return usrlist;
	}

	public MiniTwitterJDBCTemplate getMiniTwitterJDBCTemplate() {
		return miniTwitterJDBCTemplate;
	}

	public void setMiniTwitterJDBCTemplate(
			MiniTwitterJDBCTemplate miniTwitterJDBCTemplate) {
		this.miniTwitterJDBCTemplate = miniTwitterJDBCTemplate;
	}


	
	

}
