package com.tower.smartservice.service;

import com.tower.smartservice.bean.api.user.LoginModel;
import com.tower.smartservice.bean.api.user.RegisterModel;
import com.tower.smartservice.bean.response.AccountCard;
import com.tower.smartservice.bean.response.base.ResponseBuilder;
import com.tower.smartservice.bean.response.base.ResponseModel;
import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.factory.UserFactory;
import com.tower.smartservice.utils.TextUtil;

import javax.annotation.Nonnull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * AccountService
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/24 19:00
 */
@Path("/account")
public class AccountService extends BaseService {
	/**
	 * 注册POST
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/account/register
	 *
	 * @param model RegisterModel
	 * @return ResponseModel AccountRspModel
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
		UserEntity user;
		user = UserFactory.findByPhone(model.getPhone());
		if (user != null) {
			// 手机号已存在
			return ResponseBuilder.paramPhoneExist();
		}
		user = UserFactory.findByName(model.getName());
		if (user != null) {
			// 用户名已存在
			return ResponseBuilder.paramNameExist();
		}
		user = UserFactory.register(model.getPhone(), model.getPassword(), model.getName());
		if (user == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}
		if (TextUtil.isEmpty(model.getPushId())) {
			// 注册成功 返回未绑定PushId的当前账户
			AccountCard card = new AccountCard(user);
			return ResponseBuilder.success(card);
		}

		// 如果Model有携带PushId
		return bind(user, model.getPushId());
	}

	/**
	 * 登录POST
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/account/login
	 *
	 * @param model LoginModel
	 * @return ResponseModel AccountRspModel
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel login(LoginModel model) {
		if (!LoginModel.isAvailable(model)) {
			// 参数非法
			return ResponseBuilder.paramIllegal();
		}
		UserEntity user = UserFactory.login(model.getPhone(), model.getPassword());
		if (user == null) {
			// 登录失败 返回用户名或密码错误
			return ResponseBuilder.paramAccountInvalid();
		}
		if (TextUtil.isEmpty(model.getPushId())) {
			// 登录成功 返回未绑定PushId的当前账户
			AccountCard card = new AccountCard(user);
			return ResponseBuilder.success(card);
		}

		// 如果Model有携带PushId
		return bind(user, model.getPushId());
	}

	/**
	 * 通过Token绑定PushId
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/account/bind/xxxxx
	 *
	 * @param pushId PushId
	 * @return ResponseModel AccountRspModel
	 */
	@POST
	@Path("/bind/{pushId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel bind(@PathParam("pushId") String pushId) { // 从url地址中获取pushId
		if (TextUtil.isEmpty(pushId)) {
			// 参数非法
			return ResponseBuilder.paramIllegal();
		}

		// 拿到自己的个人信息
		UserEntity self = getSelf();
		if (self == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}

		// 绑定
		return bind(self, pushId);
	}

	/**
	 * 绑定PushId
	 *
	 * @param self   UserEntity
	 * @param pushId PushId
	 * @return ResponseModel AccountRspModel
	 */
	private ResponseModel bind(@Nonnull UserEntity self, String pushId) {
		// 进行PushId绑定的操作
		UserEntity user = UserFactory.bindPushId(self, pushId);
		if (user == null) {
			// 绑定失败 未知错误
			return ResponseBuilder.unknownError();
		}

		// 绑定成功 返回当前的账户 并且已经绑定了PushId
		AccountCard card = new AccountCard(user, true);
		return ResponseBuilder.success(card);
	}
}
