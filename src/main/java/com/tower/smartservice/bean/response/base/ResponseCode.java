package com.tower.smartservice.bean.response.base;

/**
 * 响应错误码
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/22 22:14
 */
class ResponseCode {
	private ResponseCode() {
	}

	/**
	 * 响应成功
	 *
	 * @see ResponseMsg#SUCCESS
	 */
	static final int SUCCESS = 1;

	/**
	 * 未知错误
	 *
	 * @see ResponseMsg#UNKNOWN_ERROR
	 */
	static final int UNKNOWN_ERROR = -1;

	/**
	 * 服务器异常
	 *
	 * @see ResponseMsg#SERVICE_ERROR
	 */
	static final int SERVICE_ERROR = -2;

	/**
	 * 认证错误 Token无效
	 *
	 * @see ResponseMsg#AUTH_TOKEN_INVALID
	 */
	static final int AUTH_TOKEN_INVALID = 901;

	/**
	 * 参数非法
	 *
	 * @see ResponseMsg#PARAM_ILLEGAL
	 */
	static final int PARAM_ILLEGAL = 1000;

	/**
	 * 参数非法 手机号已存在
	 *
	 * @see ResponseMsg#PARAM_PHONE_EXIST
	 */
	static final int PARAM_PHONE_EXIST = 1001;

	/**
	 * 参数非法 用户名已存在
	 *
	 * @see ResponseMsg#PARAM_NAME_EXIST
	 */
	static final int PARAM_NAME_EXIST = 1002;

	/**
	 * 参数非法 手机号或密码错误
	 *
	 * @see ResponseMsg#PARAM_ACCOUNT_INVALID
	 */
	static final int PARAM_ACCOUNT_INVALID = 1003;

	/**
	 * 查询错误 该用户不存在
	 *
	 * @see ResponseMsg#SEARCH_NO_SUCH_USER
	 */
	static final int SEARCH_NO_SUCH_USER = 2001;

	/**
	 * 推送错误 推送消息失败
	 *
	 * @see ResponseMsg#PUSH_MESSAGE_FAILED
	 */
	static final int PUSH_MESSAGE_FAILED = 3001;
}
