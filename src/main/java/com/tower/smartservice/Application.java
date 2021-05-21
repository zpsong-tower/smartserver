package com.tower.smartservice;

import com.tower.smartservice.provider.GsonProvider;
import com.tower.smartservice.service.AccountService;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * Application
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/24 19:00
 */
public class Application extends ResourceConfig {
	public Application() {
		// 注册逻辑处理的包名 (com.tower.smartservice.service)
		packages(AccountService.class.getPackage().getName());

		// 注册JSON解析器
		// register(JacksonJsonProvider.class);
		// 替换解析器为Gson
		register(GsonProvider.class);

		// 注册日志打印输出
		register(Logger.class);
	}
}
