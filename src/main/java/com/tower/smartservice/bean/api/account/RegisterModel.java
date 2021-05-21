package com.tower.smartservice.bean.api.account;

import com.google.gson.annotations.Expose;

/**
 * 注册Model
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/21 8:53
 */
public class RegisterModel {
	// 账号
	@Expose
	private String phone;

	// 密码
	@Expose
	private String password;

	// 用户名
	@Expose
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
