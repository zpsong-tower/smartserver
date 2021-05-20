package com.tower.smartservice.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 消息表 (TB_MESSAGE)
 * 每条消息的属性
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/19 7:36
 */
@Entity
@Table(name = "TB_MESSAGE")
public class MessageEntity {
	/**
	 * 文本类型消息
	 */
	public static final int TYPE_TEXT = 1;

	/**
	 * 图片类型消息
	 */
	public static final int TYPE_IMAGE = 2;

	/**
	 * 语音类型消息
	 */
	public static final int TYPE_AUDIO = 3;

	/**
	 * 文件类型消息
	 */
	public static final int TYPE_FILE = 4;

	// Id
	@Id
	@PrimaryKeyJoinColumn
	// @GeneratedValue(generator = "uuid") // 消息Id不自动生成，由发送客户端生成
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(updatable = false, nullable = false)
	private String id;

	// 消息内容
	@Column(nullable = false, columnDefinition = "text")
	private String content;

	// 附件
	@Column
	private String attachment;

	// 消息类型
	@Column(nullable = false)
	private int type;

	// 消息发送者 / Id
	@ManyToOne(optional = false)
	@JoinColumn(name = "senderId")
	private UserEntity sender;
	@Column(nullable = false, updatable = false, insertable = false)
	private String senderId;

	// 消息接收人 / Id
	// 接收者可能是人或群，所以可以为空
	@ManyToOne
	@JoinColumn(name = "receiverId")
	private UserEntity receiver;
	@Column(updatable = false, insertable = false)
	private String receiverId;

	// 消息接收群 / Id
	// 接收者可能是人或群，所以可以为空
	@ManyToOne
	@JoinColumn(name = "groupId")
	private GroupEntity group;
	@Column(updatable = false, insertable = false)
	private String groupId;

	// 创建时间
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createAt = LocalDateTime.now();

	// 更新时间
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime UpdateAt = LocalDateTime.now();

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

	public UserEntity getSender() {
		return sender;
	}

	public void setSender(UserEntity sender) {
		this.sender = sender;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public UserEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(UserEntity receiver) {
		this.receiver = receiver;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public GroupEntity getGroup() {
		return group;
	}

	public void setGroup(GroupEntity group) {
		this.group = group;
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

	public LocalDateTime getUpdateAt() {
		return UpdateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		UpdateAt = updateAt;
	}
}
