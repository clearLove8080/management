package com.vcv.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vcv.model.User;

/**
 * 权限控制filter
 * 
 * @author 
 */
//@Component
public class AuthorityFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(AuthorityFilter.class);

	private static List<String> whiteList = new ArrayList<String>();

	static {
		whiteList.add("");
		whiteList.add("/");

		whiteList.add("/login");
		whiteList.add("/logout");
		whiteList.add("/favicon.ico");
		whiteList.add("/monitor/testrunning");

		whiteList.add("/register");
		whiteList.add("/error");
		whiteList.add("/error/400");
		whiteList.add("/error/403");
		whiteList.add("/error/404");
		whiteList.add("/error/405");
		whiteList.add("/error/408");
		whiteList.add("/error/500");
		whiteList.add("/user/login");
	}

	private boolean isNeedAuth(String reqURI) {
		// 验证路径是否是白名单
		if (whiteList.contains(reqURI)) {
			return false;
		}

		// 验证是否是对外接口
		if (reqURI.startsWith("/api/")) {
			return false;
		}

		// swagger不拦截 end

		// 如果是静态资源，直接让过
		if (reqURI.startsWith("/resources/") || reqURI.startsWith("/css/") || reqURI.startsWith("/font/")
				|| reqURI.startsWith("/img/") || reqURI.startsWith("/swf")) {
			return false;
		}

		return true;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 去掉项目名
		String reqURI = request.getRequestURI();
//		reqURI = reqURI.replace("/smsa-operation", "");
		if (reqURI.contains(";")) { // index.html;jsessionid=5D2E7A76FB03359B80368090E8229742
			reqURI = reqURI.substring(0, reqURI.indexOf(";"));
		}
		if (isLogin(request)) {// 已登录
			if ("/login".equals(reqURI)) {
				response.sendRedirect(request.getContextPath() + "/user/dashboard");
			}
			else {

//				if (reqURI.contains("swagger-ui.html") || reqURI.contains("webjars") || reqURI.contains("v2/api-docs")) {
//					if (ConfigUtils.environmentFlag.equals("development") || ConfigUtils.environmentFlag.equals("devtest")) {
//						filterChain.doFilter(request, response);
//					}else {
//						response.sendRedirect(request.getContextPath() + "/index");
//					}
//				}else {
					filterChain.doFilter(request, response);
//				}
			}
		} else {// 未登录
			if (isNeedAuth(reqURI)) {// 需授权的页面，且未登陆，则返回登陆界面
				// 判断是否是ajax
				if (request.getHeader("x-requested-with") != null
						&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {

					response.setHeader("CONTEXTPATH", request.getContextPath() + "/index/lockscreen");
					if(reqURI.equals("/index/isSessionValid")){
						response.setHeader("sessionstatus", "timeout");
					}else{
						response.setHeader("sessionstatus", "TIMEOUT");
					}
				} else {
					response.sendRedirect(request.getContextPath() + "/user/login");
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

	private boolean isLogin(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
