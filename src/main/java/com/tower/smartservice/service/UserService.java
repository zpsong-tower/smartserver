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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserService
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/5 20:36
 */
@Path("/user")
public class UserService extends BaseService {
	/**
	 * 查询指定Id的用户信息
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
		if (id.equals(self.getId())) {
			// 查询成功 返回自己UserCard 不必再查询数据库
			return ResponseBuilder.success(new UserCard(self, true));
		}
		UserEntity user = UserFactory.findById(id);
		if (user == null) {
			// 返回该用户不存在
			return ResponseBuilder.searchNoSuchUser();
		}

		// 查询成功 返回标识了用户关系的UserCard
		boolean isFollow = UserFactory.getUserFollow(self, user) != null;
		return ResponseBuilder.success(new UserCard(user, isFollow));
	}

	/**
	 * 查询指定用户名的用户信息 (模糊查询 单页最多返回20个)
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/user/search/xxx
	 *
	 * @param name 用户名
	 * @return ResponseModel List<UserCard>
	 */
	@GET
	@Path("/search/{name:(.*)?}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel search(@DefaultValue("") @PathParam("name") String name) {
		UserEntity self = getSelf();
		if (self == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}
		List<UserEntity> searchUsers = UserFactory.search(name);
		List<UserEntity> contacts = UserFactory.getContacts(self);
		if (searchUsers == null || contacts == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}
		List<UserCard> userCards = searchUsers.stream()
				.map(user -> {
					boolean isFollow =
							// 这个人是否是我自己
							user.getId().equals(self.getId())
									|| contacts.stream().anyMatch(contactUser -> {
										// 或者是我已关注的人
										return contactUser.getId().equals(user.getId());
									}
							);
					return new UserCard(user, isFollow);
				}).collect(Collectors.toList());

		// 查询成功 返回标识了用户关系的UserCard集合
		return ResponseBuilder.success(userCards);
	}

	/**
	 * 更新用户信息
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

	/**
	 * 拉取联系人列表(关注的人列表)
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/user/contact
	 *
	 * @return ResponseModel ArrayList<UserCard>
	 */
	@GET
	@Path("/contact")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel getContacts() {
		UserEntity self = getSelf();
		List<UserEntity> users = UserFactory.getContacts(self);
		if (users == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}

		// List<UserCard> userCards = users.stream()
		// 		.map(user -> new UserCard(user, true))
		// 		.collect(Collectors.toList());
		List<UserCard> userCards = new ArrayList<>();
		for (UserEntity user : users) {
			userCards.add(new UserCard(user, true));
		}

		// 查询成功 返回UserCard集合
		return ResponseBuilder.success(userCards);
	}


	/**
	 * 关注人(目前设计为自动互关)
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/user/follow/xxx
	 *
	 * @param followId 被关注人的Id
	 * @return ResponseModel UserCard
	 */
	@PUT
	@Path("/follow/{followId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel follow(@PathParam("followId") String followId) {
		UserEntity self = getSelf();
		if (TextUtil.isEmpty(followId) || self.getId().equals(followId)) {
			// 参数非法
			return ResponseBuilder.paramIllegal();
		}
		UserEntity followUser = UserFactory.findById(followId);
		if (followUser == null) {
			// 返回该用户不存在
			return ResponseBuilder.searchNoSuchUser();
		}

		// TODO 备注名暂时为空 后期对功能进行扩展
		followUser = UserFactory.follow(self, followUser, null);
		if (followUser == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}

		// TODO 推送被关注人有与自己相关的新的用户关系被建立

		// 关注成功 返回被关注人的UserCard
		return ResponseBuilder.success(new UserCard(followUser, true));
	}
}
