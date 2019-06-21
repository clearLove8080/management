package com.vcv.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.vcv.Application;
import com.vcv.mapper.UserMapper;
import com.vcv.model.User;
import com.vcv.redis.RedisService;
import com.vcv.util.Constants;
import com.vcv.util.RequestContextHolderUtil;

/**
 * 用户管理
 */
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RedisService redisService;

    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean

    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数

    /**
     * 登录跳转
     *
     * @param model
     * @return
     */
    @GetMapping("/user/login")
    public String loginGet(Model model) {
    	//先根据Redis缓存取数据
    	String user_ticket=redisService.getValue(Constants.LOGIN_TICKET.getValue());

    	if(RequestContextHolderUtil.getSession().getId().equals(user_ticket)) {
    		User user=(User) RequestContextHolderUtil.getSession().getAttribute("user");
    			if(user!=null) {
    				return "redirect:dashboard";
    			}
    	}
        return "login";
    }

    /**
     * 登录
     *
     * @param
     * @param model
     * @param
     * @return
     */
    @PostMapping("/user/login")
    public String loginPost(User user, Model model) {
    	 User user1 = userMapper.selectByNameAndPwd(user);
         if (user1 != null) {
         	 RequestContextHolderUtil.getSession().setAttribute("user", user1);
         	 redisService.setValue(Constants.LOGIN_TICKET.getValue(), RequestContextHolderUtil.getSession().getId());
             User name = (User) RequestContextHolderUtil.getSession().getAttribute("user");
             return "redirect:dashboard";
         } else {
         	 model.addAttribute("error", "用户名或密码错误，请重新登录！");
             return "login";
         }
    }

    /**
     * 注册
     *
     * @param model
     * @return
     */
    @GetMapping("/user/register")
    public String register(Model model) {
        return "register";
    }

    /**
     * 注册
     *
     * @param model
     * @return
     */
    @PostMapping("/user/register")
    public String registerPost(User user, Model model) {
        System.out.println("用户名" + user.getUserName());
        try {
            userMapper.selectIsName(user);
            model.addAttribute("error", "该账号已存在！");
        } catch (Exception e) {
            Date date = new Date();
            user.setAddDate(date);
            user.setUpdateDate(date);
            userMapper.insert(user);
            System.out.println("注册成功");
            model.addAttribute("error", "恭喜您，注册成功！");
            return "login";
        }

        return "register";
    }

    /**
     * 登录跳转
     *
     * @param model
     * @return
     */
    @GetMapping("/user/forget")
    public String forgetGet(Model model) {
        return "forget";
    }

    /**
     * 登录
     *
     * @param
     * @param model
     * @param
     * @return
     */
    @PostMapping("/user/forget")
    public String forgetPost(User user, Model model) {
        String password = userMapper.selectPasswordByName(user);
        if (password == null) {
            model.addAttribute("error", "帐号不存在或邮箱不正确！");
        } else {
            String email = user.getEmail();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(Sender);
            message.setTo(email); //接收者邮箱
            message.setSubject("YX后台信息管理系统-密码找回");
            StringBuilder sb = new StringBuilder();
            sb.append(user.getUserName() + "用户您好！您的注册密码是：" + password + "。感谢您使用YX信息管理系统！");
            message.setText(sb.toString());
            mailSender.send(message);
            model.addAttribute("error", "密码已发到您的邮箱,请查收！");
        }
        return "forget";

    }

    @GetMapping("/user/userManage")
    public String userManageGet(Model model) {
        User user = (User) RequestContextHolderUtil.getSession().getAttribute("user");
        User user1 = userMapper.selectByNameAndPwd(user);
        model.addAttribute("user", user1);
        return "user/userManage";
    }

    @PostMapping("/user/userManage")
    public String userManagePost(Model model, User user, HttpSession httpSession) {
        Date date = new Date();
        user.setUpdateDate(date);
        int i = userMapper.update(user);
        httpSession.setAttribute("user",user);
        return "redirect:userManage";
    }

}
