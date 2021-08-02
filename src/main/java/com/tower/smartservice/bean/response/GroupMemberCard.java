package com.tower.smartservice.bean.response;

import com.tower.smartservice.bean.db.GroupMemberEntity;

import com.google.gson.annotations.Expose;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

/**
 * 群成员Card
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/2 20:05
 */
public class GroupMemberCard {
	// Id
	@Expose
	private String id;

	// 群名片
	@Expose
	private String nickName;

	// 成员权限
	@Expose
	private int permission;

	// 对应的用户Id
	@Expose
	private String userId;

	// 对应的群Id
	@Expose
	private String groupId;

	// 入群时间 (即群成员实体的创建时间)
	@Expose
	private LocalDateTime createAt;

	// 更新时间
	@Expose
	private LocalDateTime updateAt;

	public GroupMemberCard(@Nonnull GroupMemberEntity member) {
		this.id = member.getId();
		this.nickName = member.getNickname();
		this.permission = member.getPermission();
		this.userId = member.getUser().getId();
		this.groupId = member.getGroup().getId();
		this.createAt = member.getCreateAt();
		this.updateAt = member.getUpdateAt();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
