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
}
