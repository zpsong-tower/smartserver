package com.tower.smartservice.bean.response.base;

import com.tower.smartservice.utils.TextUtil;

import com.google.gson.annotations.Expose;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 推送集合
 * 每个PushList包含了一组要推送给同一客户端的数据信息
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/15 18:14
 */
public class PushList {
	private List<PushModel> pushModels = new ArrayList<>();

	public PushList() {
	}

	/**
	 * 该构造函数会执行一次Add
	 * 使构造单推更便捷
	 *
	 * @param type    推送类型
	 * @param content 推送内容
	 */
	public PushList(int type, String content) {
		add(type, content);
	}

	/**
	 * 构建推送集合的主要方法
	 * 向推送集合添加一条推送数据
	 *
	 * @param type    推送类型
	 * @param content 推送内容
	 * @return this 用于链式调用
	 */
	public PushList add(int type, String content) {
		pushModels.add(new PushModel(type, content));
		return this;
	}

	/**
	 * 构建推送集合的结束方法
	 * 将推送集合转换为Json字符串
	 *
	 * @return JsonStr
	 */
	@Nullable
	String toJsonStr() {
		if (pushModels.size() > 0) {
			return TextUtil.toJson(pushModels);
		}
		return null;
	}

	/**
	 * 推送Model
	 * 每个PushModel包含了一条要推送给客户端的数据信息
	 */
	private static class PushModel {
		// 推送类型
		@Expose
		private int type;

		// 推送内容
		@Expose
		private String content;

		// 创建时间
		@Expose
		private LocalDateTime createAt = LocalDateTime.now();

		/**
		 * 构造方法
		 *
		 * @param type    推送类型
		 * @param content 推送内容
		 */
		private PushModel(int type, String content) {
			this.type = type;
			this.content = content;
		}
	}
}
