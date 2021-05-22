package com.tower.smartservice.service;

import com.tower.smartservice.bean.api.account.RegisterModel;
import com.tower.smartservice.bean.card.UserCard;
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
	public UserCard register(RegisterModel model) {
		if (model == null) {
			return null;
		}
		UserEntity userEntity = UserFactory.register(model.getPhone(), model.getPassword(), model.getName());
		if (userEntity == null) {
			return null;
		}
		UserCard userCard = new UserCard();
		userCard.setName(userEntity.getName());
		return userCard;
	}
}
