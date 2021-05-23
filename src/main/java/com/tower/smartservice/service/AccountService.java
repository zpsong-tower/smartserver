package com.tower.smartservice.service;

import com.tower.smartservice.bean.api.account.RegisterModel;
import com.tower.smartservice.bean.response.base.ResponseBuilder;
import com.tower.smartservice.bean.response.base.ResponseModel;
import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.factory.UserFactory;

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
	 * 注册POST
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/account/register
	 *
	 * @param model RegisterModel
	 * @return UserEntity
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON) // 请求格式 JSON
	@Produces(MediaType.APPLICATION_JSON) // 返回格式 JSON
	public ResponseModel register(RegisterModel model) {
		if (!RegisterModel.isAvailable(model)) {
			// 参数非法
			return ResponseBuilder.paramIllegal();
		}
		UserEntity tempUser;
		tempUser = UserFactory.findByPhone(model.getPhone());
		if (tempUser != null) {
			// 手机号已存在
			return ResponseBuilder.paramPhoneExist();
		}
		tempUser = UserFactory.findByName(model.getName());
		if (tempUser != null) {
			// 用户名已存在
			return ResponseBuilder.paramNameExist();
		}
		tempUser = UserFactory.register(model.getPhone(), model.getPassword(), model.getName());
		if (tempUser == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}

		// 注册成功
		return ResponseBuilder.success();
	}
}
