package com.tower.smartservice.bean.response.base;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

/**
 * 响应Model
 *
 * @param <R> 返回给App的消息中携带的数据的模型
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/22 22:05
 */
public class ResponseModel<R> {
	// 响应错误码
	@Expose
	private int code;

	// 响应说明
	@Expose
	private String msg;

	// 需要返回的数据
	@Expose
	private R result;

	// 当前时间
	@Expose
	private LocalDateTime time = LocalDateTime.now();

	/**
	 * 构造方法 响应成功，不需要返回数据
	 */
	ResponseModel() {
		this.code = ResponseCode.SUCCESS;
		this.msg = ResponseMsg.SUCCESS;
	}

	/**
	 * 构造方法 响应成功，需要返回数据
	 *
	 * @param result 客户端请求的数据
	 */
	ResponseModel(R result) {
		this();
		this.result = result;
	}

	/**
	 * 构造方法 响应失败，不需要返回数据
	 *
	 * @param code 响应错误码
	 * @param msg  响应说明
	 */
	ResponseModel(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 构造方法 响应失败，需要返回数据
	 *
	 * @param code   响应错误码
	 * @param msg    响应说明
	 * @param result 客户端请求的数据
	 */
	ResponseModel(int code, String msg, R result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public R getResult() {
		return result;
	}

	public void setResult(R result) {
		this.result = result;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}
