package com.thousandeyes.minitwitter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.thousandeyes.minitwitter.vo.UserTweetVO;

public class UserTweetVORowMapper implements RowMapper<UserTweetVO>
	{
		public UserTweetVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserTweetVO userTweetVO = new UserTweetVO();
			userTweetVO.setUser(rs.getString("USER_ID"));
			userTweetVO.setTweet(rs.getString("TWEET"));
			return userTweetVO;
		}
		
	}

