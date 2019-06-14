package com.vcv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.vcv.util.Constants;
import com.vcv.util.RunnableThreadWebCount;
import com.vcv.util.Timers;

@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		  logger.info("项目开始启动"); logger.info("配置日志路径");
		 
		//计数线程
		RunnableThreadWebCount runnableThreadWebCount = new RunnableThreadWebCount();
		runnableThreadWebCount.run();
		//计时器线程
		Timers timers = new Timers();
		timers.run();
		
	}
}
