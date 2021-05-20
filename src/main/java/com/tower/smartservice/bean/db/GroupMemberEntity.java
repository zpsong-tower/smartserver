package com.tower.smartservice.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 群组成员表 (TB_GROUP_MEMBER)
 * 用户在群中的信息
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/19 20:52
 */
@Entity
@Table(name = "TB_GROUP_MEMBER")
public class GroupMemberEntity {
	/**
	 * 普通成员权限 (默认权限)
	 */
	public static final int PERMISSION_DEFAULT = 0;

	/**
	 * 管理员权限
	 */
	public static final int PERMISSION_MANAGER = 1;

	/**
	 * 创建者权限
	 */
	public static final int PERMISSION_OWNER = 2;

	/**
	 * 接受消息且提示 (默认类型)
	 */
	public static final int NOTIFY_TYPE_DEFAULT = 0;

	/**
	 * 接收消息但不提示
	 */
	public static final int NOTIFY_TYPE_CLOSE = 1;

	/**
	 * 不接收消息
	 */
	public static final int NOTIFY_TYPE_NONE = 2;

	// Id
	@Id
	@PrimaryKeyJoinColumn
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(updatable = false, nullable = false)
	private String id;

	// 群名片
	@Column
	private String nickname;

	// 成员权限
	@Column
	private int permission;

	// 消息提醒模式
	@Column
	private int notifyType;

	// 对应的用户 / Id
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private UserEntity user;
	@Column(nullable = false, updatable = false, insertable = false)
	private String userId;

	// 对应的群 / Id
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "groupId")
	private GroupEntity group;
	@Column(nullable = false, updatable = false, insertable = false)
	private String groupId;

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

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
}
