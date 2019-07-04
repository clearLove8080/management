package com.vcv.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vcv.redis.RedisService;
import com.vcv.util.MailUtil;
import com.vcv.util.PasswordUtil;

@Controller
@RequestMapping("check")
public class CheckController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CheckController.class);
	
	@Value("msg")
	private String msg;
	@Autowired
    private RedisService redisService;
	@RequestMapping(value="registerCode",method=RequestMethod.GET)
	@ResponseBody
	public String registerCode(String email) {
		msg="111";
		//加密后保存到缓存中，并设置好过期时间
		String realEmail=PasswordUtil.Base64Decode(email);
		String value=redisService.getValue(realEmail);
		if(value==null) {
			redisService.setValue(email, "open", 1800);
			logger.info("将email："+email+"设置到redis中");
		}
		return "尊敬的用户,恭喜你,验证成功哦!";
	}
	
	@RequestMapping(value="sendCode",method=RequestMethod.POST)
	@ResponseBody
	public String sendCode(HttpServletRequest request) {
		String code=getFormData(request).get("code");
		String url=PasswordUtil.Base64Encode(code);
		try {
			MailUtil.sendTextMail(code, "<a href=\"http://203.195.184.232/check/registerCode?email="+code+"\">尊敬的用户,此邮件仅为验证邮件!</a>");
		} catch (Exception e) {
			logger.error("邮件发送异常，错误信息为："+e.getMessage());
		}
		return "success";
	}
}
