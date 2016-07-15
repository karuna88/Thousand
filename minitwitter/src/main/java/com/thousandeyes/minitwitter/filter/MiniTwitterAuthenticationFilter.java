package com.thousandeyes.minitwitter.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thousandeyes.minitwitter.dao.MiniTwitterJDBCTemplate;

public class MiniTwitterAuthenticationFilter implements Filter{
	

	private MiniTwitterJDBCTemplate miniTwitterJDBCTemplate;
	
	private ApplicationContext context;

	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
	
		if(context==null)
		{
		context =
                WebApplicationContextUtils.getWebApplicationContext(
                		httpRequest.getSession().getServletContext());
        miniTwitterJDBCTemplate = (MiniTwitterJDBCTemplate)context.getBean("miniTwitterJDBCTemplate");
		}
		
		String authorization = httpRequest.getHeader("Authorization");
	    if (authorization != null && authorization.startsWith("Basic")) {
	        String base64Credentials = authorization.substring("Basic".length()).trim();
	        String credentials = new String(Base64.getDecoder().decode(base64Credentials),
	                Charset.forName("UTF-8"));
	  
	        String[] values = credentials.split(":",2);
	        String sql = "SELECT USER_ID,TOKEN FROM USER_ACCOUNT WHERE USER_ID = ?";
		    List<Map<String,Object>> user = getMiniTwitterJDBCTemplate().getJdbcTemplate().queryForList(sql, values[0]);
		    if(user !=null && user.size() > 0)
		    {
			    if(user.get(0).get("USER_ID").equals(values[0]) && (user.get(0).get("TOKEN").equals(values[1])))
			    {
			    	chain.doFilter(httpRequest, httpResponse);	
			    }
			    else
			    {
			    	httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			    }
		    }
		    else
		    {
		    	httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    }
		
	    }
	   
	}

	public void destroy() {
		
		
	}

	public MiniTwitterJDBCTemplate getMiniTwitterJDBCTemplate() {
		return miniTwitterJDBCTemplate;
	}

	public void setMiniTwitterJDBCTemplate(
			MiniTwitterJDBCTemplate miniTwitterJDBCTemplate) {
		this.miniTwitterJDBCTemplate = miniTwitterJDBCTemplate;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	
	

}
