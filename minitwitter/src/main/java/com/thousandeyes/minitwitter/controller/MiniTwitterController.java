package com.thousandeyes.minitwitter.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thousandeyes.minitwitter.exception.MiniTwitterApplicationException;
import com.thousandeyes.minitwitter.exception.MiniTwitterException;
import com.thousandeyes.minitwitter.exception.MiniTwitterUserException;
import com.thousandeyes.minitwitter.service.IMiniTwitterService;
import com.thousandeyes.minitwitter.vo.UserFollowersVO;
import com.thousandeyes.minitwitter.vo.UserFollowsVO;
import com.thousandeyes.minitwitter.vo.UserTweetsVO;
import com.thousandeyes.minitwitter.vo.UserVO;

@RestController("miniTwitterController")
public class MiniTwitterController {

	@Autowired
	@Qualifier("miniTwitterService")
	IMiniTwitterService miniTwitterService;
	
	@RequestMapping(value="/followUser",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> followUser(@RequestBody UserVO userVO)
	{
		
		ResponseEntity<String> responseEntity = null;
		try{
			getIMiniTwitterService().followUser(userVO);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Success Message","Successfully Updated,"+userVO.getUser()+" follows "+userVO.getFollowUser() );
		responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}catch(MiniTwitterException ex)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Error Message",ex.getMessage());
			if(ex instanceof MiniTwitterApplicationException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			else if(ex instanceof MiniTwitterUserException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
			}
			
			return responseEntity;
		}
		return responseEntity;
	}


	@RequestMapping(value="/unfollowUser",method=RequestMethod.DELETE, consumes="application/json")
	public ResponseEntity<String> unfollowUser(@RequestBody UserVO userVO)
	{
		ResponseEntity<String> responseEntity = null;
		try
		{
			getIMiniTwitterService().unfollowUser(userVO);
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("Success Message","Successfully Updated,"+userVO.getUser()+" unfollows "+userVO.getUnfollowUser() );

			responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}catch(MiniTwitterException ex)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Error Message",ex.getMessage());
			if(ex instanceof MiniTwitterApplicationException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			else if(ex instanceof MiniTwitterUserException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
			}
			
			return responseEntity;
		}
		return responseEntity;
	}
	
	@RequestMapping(value="/getUserFollowsList",method=RequestMethod.GET, consumes="application/json",produces="application/json")
	public ResponseEntity<String> getUserFollowsList(@RequestParam("user")String user)
	{
		
		ResponseEntity<String> responseEntity = null;
		try {
			UserFollowsVO userFollowsVO = getIMiniTwitterService().getUserFollowsList(user);
			ObjectMapper objMapper = new ObjectMapper();
			String jsonString = objMapper.writeValueAsString(userFollowsVO);
			responseEntity = new ResponseEntity<String>(jsonString, HttpStatus.OK);
		}catch(MiniTwitterException ex)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Error Message",ex.getMessage());
			if(ex instanceof MiniTwitterApplicationException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			else if(ex instanceof MiniTwitterUserException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
			}
			
			return responseEntity;
		}
		catch (JsonProcessingException e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Error Message","Application Error");
			responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@RequestMapping(value="/getUserFollowersList",method=RequestMethod.GET, consumes="application/json",produces="application/json")
	public ResponseEntity<String> getFollowersList(@RequestParam("user")String user)
	{
		ResponseEntity<String> responseEntity = null;
		try {
			UserFollowersVO userFollowersVO = getIMiniTwitterService().getFollowersList(user);
			ObjectMapper objMapper = new ObjectMapper();
			String jsonString = objMapper.writeValueAsString(userFollowersVO);
			responseEntity = new ResponseEntity<String>(jsonString, HttpStatus.OK);
		}catch(MiniTwitterException ex)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Error Message",ex.getMessage());
			if(ex instanceof MiniTwitterApplicationException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			else if(ex instanceof MiniTwitterUserException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
			}
			
			return responseEntity;
		}catch (JsonProcessingException e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Error Message","Application Error");
			responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
		return responseEntity;
	}
	
	
	@RequestMapping(value="/readTweets",method=RequestMethod.GET, consumes="application/json", produces="application/json")
	public ResponseEntity<String> getUserTweets(@RequestParam("user") String user,
			@RequestParam(value="search",required=false) String search)
	{
		ResponseEntity<String> responseEntity = null;
		try {
			List<UserTweetsVO> userTweets = getIMiniTwitterService().getUserTweets(user, search);
			ObjectMapper objMapper = new ObjectMapper();
			String jsonString = objMapper.writeValueAsString(userTweets);
			responseEntity = new ResponseEntity<String>(jsonString, HttpStatus.OK);
		}catch(MiniTwitterException ex)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Error Message",ex.getMessage());
			if(ex instanceof MiniTwitterApplicationException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			else if(ex instanceof MiniTwitterUserException)
			{
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
			}
			
			return responseEntity;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Error Message","Application Error");
			responseEntity = new ResponseEntity<String>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}


	public IMiniTwitterService getIMiniTwitterService() {
		return miniTwitterService;
	}


	public void setIMiniTwitterService(IMiniTwitterService miniTwitterService) {
		this.miniTwitterService = miniTwitterService;
	}

	

}
