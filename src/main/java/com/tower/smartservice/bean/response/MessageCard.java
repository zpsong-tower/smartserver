package com.tower.smartservice.bean.response;

import com.tower.smartservice.bean.db.MessageEntity;

import com.google.gson.annotations.Expose;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

/**
 * 消息Card
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/2 20:39
 */
public class MessageCard {
	// Id
	@Expose
	private String id;

	// 消息内容
	@Expose
	private String content;

	// 附件
	@Expose
	private String attachment;

	// 消息类型
	@Expose
	private int type;

	// 消息发送者Id
	@Expose
	private String senderId;

	// 消息接收人Id
	@Expose
	private String receiverId;

	// 消息接收群Id
	@Expose
	private String groupId;

	// 创建时间
	@Expose
	private LocalDateTime createAt;

	public MessageCard(@Nonnull MessageEntity message) {
		this.id = message.getId();
		this.content = message.getContent();
		this.type = message.getType();
		this.attachment = message.getAttachment();
		this.senderId = message.getSenderId();
		this.receiverId = message.getReceiverId();
		this.groupId = message.getGroupId();
		this.createAt = message.getCreateAt();
	}

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

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
}
