package com.tower.smartservice.service;

import com.tower.smartservice.bean.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * AccountService
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/24 19:00
 */
@Path("/account")
public class AccountService {

	/**
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/account/login
	 *
	 * @return "hello world!"
	 */
	@GET
	@Path("/login")
	public String get() {
		return "hello world!";
	}

	/**
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/account/login
	 *
	 * @return {"name": "tower", "sex": 1}
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON) // 请求格式 JSON
	@Produces(MediaType.APPLICATION_JSON) // 返回格式 JSON
	public User post() {
		User user = new User();
		user.setName("tower");
		user.setSex(1);
		return user;
	}
}
