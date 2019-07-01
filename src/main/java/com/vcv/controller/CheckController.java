package com.vcv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vcv.redis.RedisService;
import com.vcv.util.MailUtil;
import com.vcv.util.PasswordUtil;

@Controller
@RequestMapping("check")
public class CheckController {
	@Autowired
    private RedisService redisService;
	@RequestMapping(value="registerCode",method=RequestMethod.GET)
	@ResponseBody
	public String registerCode(String email) {
		//加密后保存到缓存中，并设置好过期时间
		String realEmail=PasswordUtil.Base64Decode(email);
		String value=redisService.getValue(realEmail);
		if(value==null) {
			redisService.setValue(email, "open", 1800);
		}
		return "尊敬的用户,此邮件仅为验证邮件!";
	}
	
	@RequestMapping(value="sendCode",method=RequestMethod.GET)
	@ResponseBody
	public String sendCode(String code) {
		String url=PasswordUtil.Base64Encode(code);
		try {
			MailUtil.sendTextMail(code, "<a href=\"http://www.vculturev.xyz?code="+code+"\">尊敬的用户,此邮件仅为验证邮件!</a>");
		} catch (Exception e) {
			
		}
		return null;
	}
}
