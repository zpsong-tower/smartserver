package com.tower.smartservice.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 推送记录表 (TB_PUSH_HISTORY)
 * 消息推送的历史记录
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/20 7:31
 */
@Entity
@Table(name = "TB_PUSH_HISTORY")
public class PushHistoryEntity {
	// Id
	@Id
	@PrimaryKeyJoinColumn
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(updatable = false, nullable = false)
	private String id;

	// 推送的具体实体(JSON)
	@Lob
	@Column(nullable = false, columnDefinition = "BLOB")
	private String entity;

	// 推送的实体类型
	@Column(nullable = false)
	private int entityType;

	// 推送发送者
	// 可能为系统消息，所以可以为空
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "senderId")
	private UserEntity sender;
	@Column(updatable = false, insertable = false)
	private String senderId;

	// 推送接收者
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "receiverId")
	private UserEntity receiver;
	@Column(nullable = false, updatable = false, insertable = false)
	private String receiverId;

	// 接收者当前状态下的推送Id
	@Column
	private String receiverPushId;

	// 消息送达的时间
	@Column
	private LocalDateTime arrivalAt;

	// 创建时间
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createAt = LocalDateTime.now();

	// 更新时间
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updateAt = LocalDateTime.now();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public int getEntityType() {
		return entityType;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
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

	public String getReceiverPushId() {
		return receiverPushId;
	}

	public void setReceiverPushId(String receiverPushId) {
		this.receiverPushId = receiverPushId;
	}

	public LocalDateTime getArrivalAt() {
		return arrivalAt;
	}

	public void setArrivalAt(LocalDateTime arrivalAt) {
		this.arrivalAt = arrivalAt;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
}
