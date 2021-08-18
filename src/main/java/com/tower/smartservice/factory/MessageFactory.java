package com.tower.smartservice.factory;

import com.tower.smartservice.bean.api.message.MessageCreateModel;
import com.tower.smartservice.bean.db.GroupEntity;
import com.tower.smartservice.bean.db.MessageEntity;
import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.utils.HibUtil;
import com.tower.smartservice.utils.TextUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * MessageFactory
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/17 8:01
 */
public class MessageFactory extends BaseFactory {
	/**
	 * 查找一条消息
	 *
	 * @param id Id
	 * @return MessageEntity 如为Null则不存在
	 */
	@Nullable
	public static MessageEntity findById(String id) {
		if (TextUtil.isEmpty(id)) {
			return null;
		}
		return HibUtil.handle(session -> {
			return session.get(MessageEntity.class, id);
		});
	}

	/**
	 * 添加一条对用户发送的消息
	 *
	 * @param sender   发送者
	 * @param receiver 消息接收人
	 * @param model    MessageCreateModel
	 * @return MessageEntity
	 */
	@Nullable
	public static MessageEntity add(UserEntity sender, UserEntity receiver, MessageCreateModel model) {
		if (sender == null || receiver == null || model == null) {
			return null;
		}
		MessageEntity message = new MessageEntity(sender, receiver, model);
		return save(message);
	}

	/**
	 * 添加一条对群组发送的消息
	 *
	 * @param sender 发送者
	 * @param group  消息接收群
	 * @param model  MessageCreateModel
	 * @return MessageEntity
	 */
	@Nullable
	public static MessageEntity add(UserEntity sender, GroupEntity group, MessageCreateModel model) {
		if (sender == null || group == null || model == null) {
			return null;
		}
		MessageEntity message = new MessageEntity(sender, group, model);
		return save(message);
	}

	@Nullable
	private static MessageEntity save(@Nonnull MessageEntity message) {
		return HibUtil.handle(session -> {
			session.save(message);

			// 将session的缓存数据与数据库同步
			session.flush();

			// 使对象的状态和数据库中保持一致
			session.refresh(message);

			return message;
		});
	}
}
