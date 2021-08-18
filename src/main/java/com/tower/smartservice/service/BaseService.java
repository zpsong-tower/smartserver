package com.tower.smartservice.service;

import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.utils.TextUtil;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * BaseService
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/5 18:24
 */
public abstract class BaseService {
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
		UserEntity self = (UserEntity) securityContext.getUserPrincipal();
		if (TextUtil.isEmpty(self.getId())) {
			System.err.println(TAG + ": " + "GET_SELF_ID ERROR");
			return null;
		}
		return self;
	}
}
