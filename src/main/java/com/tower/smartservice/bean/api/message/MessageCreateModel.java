package com.tower.smartservice.bean.api.message;

import com.tower.smartservice.bean.db.MessageEntity;
import com.tower.smartservice.utils.TextUtil;

import com.google.gson.annotations.Expose;

/**
 * 消息创建Model
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/17 5:40
 */
public class MessageCreateModel {
	/**
	 * 对用户发送的消息
	 */
	public static final int RECEIVER_TYPE_USER = 1;

	/**
	 * 对群组发送的消息
	 */
	public static final int RECEIVER_TYPE_GROUP = 2;

	// Id 由发送客户端生成
	@Expose
	private String id;

	// 消息内容
	@Expose
	private String content;

	// 附件
	@Expose
	private String attachment;

	@Expose
	private int type = MessageEntity.TYPE_TEXT;

	// 消息接收者Id
	@Expose
	private String receiverId;

	// 接受者类型
	@Expose
	private int receiverType = RECEIVER_TYPE_USER;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public int getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(int receiverType) {
		this.receiverType = receiverType;
	}

	/**
	 * 数据格式校验
	 *
	 * @param model MessageCreateModel
	 * @return 是否可用
	 */
	public static boolean isAvailable(MessageCreateModel model) {
		return model != null
				&& !(TextUtil.isEmpty(model.id)
				|| TextUtil.isEmpty(model.content)
				|| TextUtil.isEmpty(model.receiverId))
				&& (model.receiverType == RECEIVER_TYPE_USER
				|| model.receiverType == RECEIVER_TYPE_GROUP)
				&& (model.type == MessageEntity.TYPE_TEXT
				|| model.type == MessageEntity.TYPE_IMAGE
				|| model.type == MessageEntity.TYPE_AUDIO
				|| model.type == MessageEntity.TYPE_FILE
		);
	}
}
