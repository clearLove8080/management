package com.vcv.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.vcv.redis.RedisService;
//@Component
public class IPFilter implements Filter {
	private FilterConfig config;
	@Autowired
	private RedisService redisService;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config=filterConfig;
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			//获取ServletConfig
	        ServletContext sc = config.getServletContext();
	        //获取ServletContext中的map
	        Map<String,Integer> map=(Map<String, Integer>) sc.getAttribute("map");
	        //如果map不存在，说明这是第一次被访问
	        if(map==null){
	            //创建map
	            map=new LinkedHashMap<String,Integer>();
	        }
	        //获取请求ip
	        String ip = request.getRemoteAddr();
	        Set<String> ips=new HashSet<String>();
	        //判断map中是否存在这个ip
	        if(map.containsKey(ip)){
	            //如果ip存在，说明这个IP已经访问过本站
	            // 获取访问次数
	            Integer count = map.get(ip);
	            //把访问次数+1
	            count++;
	            //把新的访问次数保存回去
	            map.put(ip, count);
	            ips.add(ip);
	        }else{
	            //因为这个IP是第一次访问，所以值为1
	            map.put(ip, 1);
	        }
	        	//把map放入ServletContext中
			    sc.setAttribute("map", map);
			    //放入redis
			    Object[]strs=ips.toArray();
			    String ipsF=redisService.getValue("ips");
			    ipsF=ipsF+ArrayUtils.toString(strs,",");
			    redisService.setValue("ips",ipsF);
			    //放行
			    chain.doFilter(request, response);
	        
    }
		

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
