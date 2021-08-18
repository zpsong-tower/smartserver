package com.tower.smartservice.factory;

import com.tower.smartservice.bean.db.PushHistoryEntity;
import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.bean.response.MessageCard;
import com.tower.smartservice.bean.response.base.PushBuilder;
import com.tower.smartservice.bean.response.base.PushCode;
import com.tower.smartservice.bean.response.base.PushList;
import com.tower.smartservice.utils.HibUtil;
import com.tower.smartservice.utils.TextUtil;

import javax.annotation.Nullable;

/**
 * PushFactory
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/17 8:07
 */
public class PushFactory extends BaseFactory {
	// 发送一条消息，并在当前的发送历史记录中存储记录
	@Nullable
	public static MessageCard pushUserMessage(UserEntity receiver, MessageCard card) {
		if (receiver == null || card == null) {
			return null;
		}
		String jsonStr = TextUtil.toJson(card);

		// 记录推送历史
		// 无论是否成功，不中断推送逻辑
		PushHistoryEntity pushHistory = new PushHistoryEntity();
		pushHistory.setEntityType(PushCode.PUSH_TYPE_MESSAGE);
		pushHistory.setEntity(jsonStr);
		pushHistory.setReceiver(receiver);
		pushHistory.setReceiverPushId(receiver.getPushId());
		HibUtil.handle(session -> {
			session.save(pushHistory);
		});

		// 推送
		PushList pushList = new PushList(pushHistory.getEntityType(), pushHistory.getEntity());
		if (new PushBuilder(receiver, pushList).commit()) {
			return card;
		}
		return null;
	}
}
