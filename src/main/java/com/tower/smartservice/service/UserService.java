package com.tower.smartservice.service;

import com.tower.smartservice.bean.api.user.UpdateInfoModel;
import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.bean.response.UserCard;
import com.tower.smartservice.bean.response.base.ResponseBuilder;
import com.tower.smartservice.bean.response.base.ResponseModel;
import com.tower.smartservice.factory.UserFactory;
import com.tower.smartservice.utils.TextUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * UserService
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/5 20:36
 */
@Path("/user")
public class UserService extends BaseService {
	/**
	 * 查询指定用户的信息
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/user/xxx
	 *
	 * @param id 查询的用户Id
	 * @return ResponseModel UserCard
	 */
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON) // 请求格式 JSON
	@Produces(MediaType.APPLICATION_JSON) // 返回格式 JSON
	public ResponseModel getUserInfo(@PathParam("id") String id) { // 从url地址中获取Id
		if (TextUtil.isEmpty(id)) {
			// 参数非法
			return ResponseBuilder.paramIllegal();
		}
		UserEntity self = getSelf();
		if (self == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}
		if (id.equalsIgnoreCase(self.getId())) {
			// 查询成功 返回自己UserCard 不必再查询数据库
			return ResponseBuilder.success(new UserCard(self, true));
		}
		UserEntity user = UserFactory.findById(id);
		if (user == null) {
			// 返回该用户不存在
			return ResponseBuilder.searchNoSuchUser();
		}

		// TODO 查询成功 返回标识了用户关系的UserCard
		boolean isFollow = true;
		return ResponseBuilder.success(new UserCard(user, isFollow));
	}

	/**
	 * 更新用户信息PUT
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/user
	 *
	 * @param model RegisterModel
	 * @return ResponseModel UserCard
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel updateInfo(UpdateInfoModel model) {
		if (!UpdateInfoModel.isAvailable(model)) {
			// 参数非法
			return ResponseBuilder.paramIllegal();
		}
		UserEntity self = getSelf();
		self = model.updateToUser(self);
		self = UserFactory.update(self);
		if (self == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}

		// 更新成功 返回更新后的自己UserCard
		UserCard card = new UserCard(self, true);
		return ResponseBuilder.success(card);
	}
}
