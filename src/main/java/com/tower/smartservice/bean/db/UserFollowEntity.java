package com.tower.smartservice.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户关系表 (TB_USER_FOLLOW)
 * 用户好友关系
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/18 12:33
 */
@Entity
@Table(name = "TB_USER_FOLLOW")
public class UserFollowEntity {
	// Id
	@Id
	@PrimaryKeyJoinColumn
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(updatable = false, nullable = false)
	private String id;

	// 备注名
	@Column
	private String nickname;

	// 关注发起人 / Id
	@ManyToOne(optional = false)
	@JoinColumn(name = "originId")
	private UserEntity origin;
	@Column(nullable = false, updatable = false, insertable = false)
	private String originId;

	// 被关注的人 / Id
	@ManyToOne(optional = false)
	@JoinColumn(name = "targetId")
	private UserEntity target;
	@Column(nullable = false, updatable = false, insertable = false)
	private String targetId;

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public UserEntity getOrigin() {
		return origin;
	}

	public void setOrigin(UserEntity origin) {
		this.origin = origin;
	}

	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public UserEntity getTarget() {
		return target;
	}

	public void setTarget(UserEntity target) {
		this.target = target;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
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
