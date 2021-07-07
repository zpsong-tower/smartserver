package com.tower.smartservice.service;

import com.tower.smartservice.bean.db.UserEntity;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * BaseService
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/5 18:24
 */
public class BaseService {
	private static final String TAG = BaseService.class.getName();

	@Context
	protected SecurityContext securityContext;

	/**
	 * 从上下文中直接获取自己的UserEntity
	 *
	 * @return UserEntity
	 */
	protected UserEntity getSelf() {
		if (securityContext == null || !(securityContext.getUserPrincipal() instanceof UserEntity)) {
			System.err.println(TAG + ": " + "GET_SELF ERROR");
			return null;
		}
		return (UserEntity) securityContext.getUserPrincipal();
	}
}
