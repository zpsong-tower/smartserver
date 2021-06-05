package com.tower.smartservice.bean.api.user;

import com.google.gson.annotations.Expose;
import com.tower.smartservice.utils.TextUtil;

/**
 * 登录Model
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/2 17:52
 */
public class LoginModel {
	@Expose
	private String phone;

	@Expose
	private String password;

	@Expose
	private String pushId;

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

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	/**
	 * 数据格式校验
	 *
	 * @param model LoginModel
	 * @return 是否可用
	 */
	public static boolean isAvailable(LoginModel model) {
		return model != null
				&& !TextUtil.isEmpty(model.phone)
				&& !TextUtil.isEmpty(model.password);
	}
}
