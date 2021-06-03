package com.tower.smartservice.bean.response.base;

/**
 * 响应说明
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/22 22:28
 */
class ResponseMsg {
	private ResponseMsg() {
	}

	/**
	 * 响应成功
	 *
	 * @see ResponseCode#SUCCESS
	 */
	static final String SUCCESS = "Success";

	/**
	 * 未知错误
	 *
	 * @see ResponseCode#UNKNOWN_ERROR
	 */
	static final String UNKNOWN_ERROR = "Unknown error";

	/**
	 * 服务器异常
	 *
	 * @see ResponseCode#SERVICE_ERROR
	 */
	static final String SERVICE_ERROR = "Service error";

	/**
	 * 参数非法
	 *
	 * @see ResponseCode#PARAM_ILLEGAL
	 */
	static final String PARAM_ILLEGAL = "Param illegal";

	/**
	 * 参数非法 手机号已存在
	 *
	 * @see ResponseCode#PARAM_PHONE_EXIST
	 */
	static final String PARAM_PHONE_EXIST = "The mobile phone number already exists";

	/**
	 * 参数非法 用户名已存在
	 *
	 * @see ResponseCode#PARAM_NAME_EXIST
	 */
	static final String PARAM_NAME_EXIST = "The user name already exists";
}
