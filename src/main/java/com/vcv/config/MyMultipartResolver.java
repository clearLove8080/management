package com.vcv.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Configuration
public class MyMultipartResolver extends CommonsMultipartResolver {
	@Override
	public boolean isMultipart(HttpServletRequest request) {
        // 过滤金格生成文书保存的接口  兼容MultipartResolver 或者 ServletFileUpload
        if (request.getRequestURI().contains("mgt/document/upload")||request.getRequestURI().contains("/modules/document.html")) {
            return false;
        }
        return super.isMultipart(request);
    }
}
