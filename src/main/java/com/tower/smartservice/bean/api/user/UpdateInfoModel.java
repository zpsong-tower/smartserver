package com.tower.smartservice.bean.api.user;

import com.google.gson.annotations.Expose;
import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.utils.TextUtil;

import javax.annotation.Nonnull;

/**
 * 更新用户信息Model
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/5 18:52
 */
public class UpdateInfoModel {
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

	/**
	 * 把当前Model实例中的数据填充到UserEntity中
	 *
	 * @param user UserEntity
	 * @return UserEntity
	 */
	public UserEntity updateToUser(UserEntity user) {
		if (user == null) {
			return null;
		}
		if (!TextUtil.isEmpty(name)) {
			user.setName(name);
		}
		if (!TextUtil.isEmpty(portrait)) {
			user.setPortrait(portrait);
		}
		if (description != null) {
			user.setDescription(description);
		}
		if (sex != UserEntity.SEX_TYPE_UNKNOWN) {
			user.setSex(sex);
		}
		return user;
	}

	/**
	 * 数据格式校验
	 *
	 * @param model UpdateInfoModel
	 * @return 是否可用
	 */
	public static boolean isAvailable(UpdateInfoModel model) {
		return model != null
				&& (!TextUtil.isEmpty(model.name) || !TextUtil.isEmpty(model.portrait)
				|| !TextUtil.isEmpty(model.description) || model.sex != UserEntity.SEX_TYPE_UNKNOWN);
	}
}
