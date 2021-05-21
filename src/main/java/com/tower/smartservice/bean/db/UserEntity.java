package com.tower.smartservice.bean.db;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户表 (TB_USER)
 * 用户基本信息及账号信息
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/18 9:39
 */
@Entity
@Table(name = "TB_USER")
public class UserEntity {
	/**
	 * 性别_男
	 */
	public static final int SEX_TYPE_MALE = 0;

	/**
	 * 性别_女
	 */
	public static final int SEX_TYPE_FEMALE = 1;

	// Id
	@Id
	@PrimaryKeyJoinColumn
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(updatable = false, nullable = false)
	private String id;

	// 用户名
	@Column(nullable = false, length = 128, unique = true)
	private String name;

	// 手机号(账号)
	@Column(nullable = false, length = 64, unique = true)
	private String phone;

	// 密码
	@Column(nullable = false)
	private String password;

	// 头像
	@Column
	private String portrait;

	// 个性签名
	@Column
	private String description;

	// 性别
	@Column(nullable = false)
	private int sex = 0;

	// Token
	@Column(unique = true)
	private String token;

	// 推送Id
	@Column
	private String pushId;

	// 最后收到消息的时间
	@Column
	private LocalDateTime lastReceivedAt;

	// 创建时间
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createAt = LocalDateTime.now();

	// 更新时间
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime UpdateAt = LocalDateTime.now();

	// 我关注的人 UserFollowEntity.originId
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@JoinColumn(name = "originId")
	private Set<UserFollowEntity> following = new HashSet<>();

	// 关注我的人 UserFollowEntity.targetId
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@JoinColumn(name = "targetId")
	private Set<UserFollowEntity> followers = new HashSet<>();

	// 我创建的群 GroupEntity.ownerId
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@JoinColumn(name = "ownerId")
	private Set<GroupEntity> groups = new HashSet<>();

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public LocalDateTime getLastReceivedAt() {
		return lastReceivedAt;
	}

	public void setLastReceivedAt(LocalDateTime lastReceivedAt) {
		this.lastReceivedAt = lastReceivedAt;
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

	public Set<UserFollowEntity> getFollowing() {
		return following;
	}

	public void setFollowing(Set<UserFollowEntity> following) {
		this.following = following;
	}

	public Set<UserFollowEntity> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<UserFollowEntity> followers) {
		this.followers = followers;
	}

	public Set<GroupEntity> getGroups() {
		return groups;
	}

	public void setGroups(Set<GroupEntity> groups) {
		this.groups = groups;
	}
}
