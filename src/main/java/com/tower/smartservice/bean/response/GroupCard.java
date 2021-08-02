package com.tower.smartservice.bean.response;

import com.tower.smartservice.bean.db.GroupEntity;
import com.tower.smartservice.bean.db.GroupMemberEntity;

import com.google.gson.annotations.Expose;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

/**
 * 群组Card
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/2 19:19
 */
public class GroupCard {
	// Id
	@Expose
	private String id;

	// 群名称
	@Expose
	private String name;

	// 群描述
	@Expose
	private String description;

	// 群头像
	@Expose
	private String portrait;

	// 群主Id
	@Expose
	private String ownerId;

	// 当前用户的消息提醒模式
	@Expose
	private int notifyType;

	// 创建时间
	@Expose
	private LocalDateTime createAt;

	// 更新时间
	@Expose
	private LocalDateTime updateAt;

	public GroupCard(@Nonnull GroupMemberEntity member) {
		this(member.getGroup(), member);
	}

	public GroupCard(@Nonnull GroupEntity group) {
		this(group, null);
	}

	public GroupCard(@Nonnull GroupEntity group, GroupMemberEntity member) {
		this.id = group.getId();
		this.name = group.getName();
		this.description = group.getDescription();
		this.portrait = group.getPortrait();
		this.ownerId = group.getOwner().getId();
		this.notifyType = member != null ? member.getNotifyType() : 0;
		this.createAt = group.getCreateAt();
		this.updateAt = group.getUpdateAt();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
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
