package com.tower.smartservice.service;

import com.tower.smartservice.bean.api.message.MessageCreateModel;
import com.tower.smartservice.bean.db.MessageEntity;
import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.bean.response.MessageCard;
import com.tower.smartservice.bean.response.base.ResponseBuilder;
import com.tower.smartservice.bean.response.base.ResponseModel;
import com.tower.smartservice.factory.MessageFactory;
import com.tower.smartservice.factory.PushFactory;
import com.tower.smartservice.factory.UserFactory;

import javax.annotation.Nonnull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * MessageService
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/11 23:05
 */
@Path("/message")
public class MessageService extends BaseService {
	/**
	 * 发送一条消息到服务器
	 * http://localhost:8080/Gradle___smartservice___smartservice_1_0_SNAPSHOT_war/api/message
	 *
	 * @param model MessageCreateModel
	 * @return ResponseModel MessageCard
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel send(MessageCreateModel model) {
		if (!MessageCreateModel.isAvailable(model)) {
			// 参数非法
			return ResponseBuilder.paramIllegal();
		}
		UserEntity self = getSelf();
		if (self == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}
		MessageEntity message = MessageFactory.findById(model.getId());
		if (message != null) {
			// 发送成功 返回数据库已存在消息
			return ResponseBuilder.success(new MessageCard(message));
		}
		if (model.getReceiverType() == MessageCreateModel.RECEIVER_TYPE_USER) {
			// 对用户发送消息
			return sendToUser(self, model);
		} else if (model.getReceiverType() == MessageCreateModel.RECEIVER_TYPE_GROUP) {
			// 对群组发送消息
			return sendToGroup(self, model);
		} else {
			// 未知错误
			return ResponseBuilder.unknownError();
		}
	}

	@Nonnull
	private ResponseModel sendToUser(@Nonnull UserEntity sender, @Nonnull MessageCreateModel model) {
		UserEntity receiver = UserFactory.findById(model.getReceiverId());
		if (receiver == null) {
			// 返回该用户不存在
			return ResponseBuilder.searchNoSuchUser();
		}
		MessageEntity message = MessageFactory.add(sender, receiver, model);
		if (message == null) {
			// 未知错误
			return ResponseBuilder.unknownError();
		}
		MessageCard card = new MessageCard(message);
		card = PushFactory.pushUserMessage(receiver, card);
		if (card == null) {
			// 推送失败
			return ResponseBuilder.pushMessageFailed();
		}

		// 推送成功 返回MessageCard
		return ResponseBuilder.success(card);
	}

	@Nonnull
	private ResponseModel sendToGroup(@Nonnull UserEntity sender, @Nonnull MessageCreateModel model) {
		// TODO 群组模块
		return ResponseBuilder.unknownError();
	}
}
