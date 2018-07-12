package com.kapphk.pms.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kapphk.pms.util.ValidateUtils;

/**
 * 接口权限过滤器
 * @author zoneyu 14-12-11
 */
public class SessionFilter implements Filter{

	public void destroy() {
		System.out.println("session过滤器销毁....");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req ;
		HttpServletResponse response = (HttpServletResponse)res ;
		
		String uri = request.getRequestURI() ;
		System.out.println("请求链接："+uri);
		
		if(uri.contains("aspf")){
			chain.doFilter(request, response) ;
		}else{
			if(uri.contains("/login.jsp") || uri.contains("login.htm") 
					||uri.contains("/test.jsp") || uri.contains("webview.jsp") || uri.contains("share.jsp") || uri.contains("share1.jsp")){
				chain.doFilter(request, response) ;
			}else{
				Object userId = request.getSession().getAttribute("userId") ;
				if(!ValidateUtils.isBlank(userId)){
					chain.doFilter(request, response) ;
				}else{
					PrintWriter out = response.getWriter() ;
					String contextPath = request.getContextPath() ;
					out.write("<script>window.parent.parent.location.href = '"+contextPath+"/login/login.jsp';</script>") ;
					out.close(); 
				}
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("init....");
	}

}
