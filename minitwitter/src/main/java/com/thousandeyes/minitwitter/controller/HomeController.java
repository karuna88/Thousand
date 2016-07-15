package com.thousandeyes.minitwitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.thousandeyes.minitwitter.service.MiniTwitterService;
import com.thousandeyes.minitwitter.vo.MiniTwitterCommand;

@Controller("homeController")
public class HomeController {
	
	@Autowired
	@Qualifier("miniTwitterService")
	MiniTwitterService miniTwitterService;
	
	
	@RequestMapping(value="homePage.do")
	public ModelAndView homePage(ModelAndView mav,@ModelAttribute("miniTwitterCommand")
	MiniTwitterCommand miniTwitterCommand)
	{
		mav.setViewName("homePage");
		return mav;
	}

}
