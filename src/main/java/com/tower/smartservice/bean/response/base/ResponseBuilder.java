package com.tower.smartservice.bean.response.base;

/**
 * 响应生成器
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/23 0:51
 */
public class ResponseBuilder {
	private ResponseBuilder() {
	}

	/**
	 * 响应成功，没有数据需要返回
	 *
	 * @return ResponseModel
	 */
	public static ResponseModel success() {
		return new ResponseModel();
	}

	/**
	 * 响应成功，并返回客户端请求的数据
	 *
	 * @param result 返回给App的消息中携带的数据
	 * @param <R>    返回给App的消息中携带的数据的模型
	 * @return ResponseModel
	 */
	public static <R> ResponseModel<R> success(R result) {
		return new ResponseModel<>(result);
	}

	/**
	 * 未知的错误
	 *
	 * @return ResponseModel
	 */
	public static ResponseModel unknownError() {
		return new ResponseModel(ResponseCode.UNKNOWN_ERROR, ResponseMsg.UNKNOWN_ERROR);
	}

	/**
	 * 服务器异常
	 *
	 * @return ResponseModel
	 */
	public static ResponseModel serviceError() {
		return new ResponseModel(ResponseCode.SERVICE_ERROR, ResponseMsg.SERVICE_ERROR);
	}

	/**
	 * 认证错误 Token无效
	 *
	 * @return ResponseModel
	 */
	public static ResponseModel authTokenInvalid() {
		return new ResponseModel(ResponseCode.AUTH_TOKEN_INVALID, ResponseMsg.AUTH_TOKEN_INVALID);
	}

	/**
	 * 参数非法
	 *
	 * @return ResponseModel
	 */
	public static ResponseModel paramIllegal() {
		return new ResponseModel(ResponseCode.PARAM_ILLEGAL, ResponseMsg.PARAM_ILLEGAL);
	}

	/**
	 * 参数非法 手机号已存在
	 *
	 * @return ResponseModel
	 */
	public static ResponseModel paramPhoneExist() {
		return new ResponseModel(ResponseCode.PARAM_PHONE_EXIST, ResponseMsg.PARAM_PHONE_EXIST);
	}

	/**
	 * 参数非法 用户名已存在
	 *
	 * @return ResponseModel
	 */
	public static ResponseModel paramNameExist() {
		return new ResponseModel(ResponseCode.PARAM_NAME_EXIST, ResponseMsg.PARAM_NAME_EXIST);
	}

	/**
	 * 参数非法 手机号或密码错误
	 *
	 * @return ResponseModel
	 */
	public static ResponseModel paramAccountInvalid() {
		return new ResponseModel(ResponseCode.PARAM_ACCOUNT_INVALID, ResponseMsg.PARAM_ACCOUNT_INVALID);
	}

	/**
	 * 查询错误 该用户不存在
	 *
	 * @return ResponseModel
	 */
	public static ResponseModel searchNoSuchUser() {
		return new ResponseModel(ResponseCode.SEARCH_NO_SUCH_USER, ResponseMsg.SEARCH_NO_SUCH_USER);
	}
}
