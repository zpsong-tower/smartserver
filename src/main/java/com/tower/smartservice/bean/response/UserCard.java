package com.tower.smartservice.bean.response;

import com.google.gson.annotations.Expose;
import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.utils.HibUtil;

import java.time.LocalDateTime;

/**
 * 用户Card
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/21 13:50
 */
public class UserCard {
	// Id
	@Expose
	private String id;

	// 用户名
	@Expose
	private String name;

	// 头像
	@Expose
	private String portrait;

	// 个性签名
	@Expose
	private String description;

	// 性别
	@Expose
	private int sex;

	// 最后更新用户信息时间
	@Expose
	private LocalDateTime updateAt;

	// 关注数
	@Expose
	private int followingNum;

	// 粉丝数
	@Expose
	private int followersNum;

	// 是否已关注该用户
	@Expose
	private boolean isFollow;

	public UserCard(UserEntity user) {
		this(user, false);
	}

	public UserCard(UserEntity user, boolean isFollow) {
		this.id = user.getId();
		this.name = user.getName();
		this.portrait = user.getPortrait();
		this.description = user.getDescription();
		this.sex = user.getSex();
		this.updateAt = user.getUpdateAt();

		// 懒加载会报错，因为没有Session
		// user.getFollowers().size()
		HibUtil.handle(session -> {
			// 重新加载一次用户信息
			session.load(user, user.getId());
			// 这个时候仅仅只是进行了数量查询，并没有查询整个集合
			// 要查询集合，必须在session存在情况下进行遍历
			// 或者使用Hibernate.initialize(user.getFollowers());
			followingNum = user.getFollowing().size();
			followersNum = user.getFollowers().size();
		});
		this.isFollow = isFollow;
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

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public int getFollowingNum() {
		return followingNum;
	}

	public void setFollowingNum(int followingNum) {
		this.followingNum = followingNum;
	}

	public int getFollowersNum() {
		return followersNum;
	}

	public void setFollowersNum(int followersNum) {
		this.followersNum = followersNum;
	}

	public boolean isFollow() {
		return isFollow;
	}

	public void setFollow(boolean follow) {
		isFollow = follow;
	}
}
