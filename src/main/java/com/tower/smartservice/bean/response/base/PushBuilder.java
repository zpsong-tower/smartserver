package com.tower.smartservice.bean.response.base;

import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.utils.PushUtil;
import com.tower.smartservice.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * PushBuilder
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/16 1:49
 */
public class PushBuilder {
	// PushBundle集合 每个PushBundle包含了推送的目标和该目标所需的数据信息
	private final List<PushUtil.PushBundle> bundles = new ArrayList<>();

	public PushBuilder() {
	}

	/**
	 * 该构造函数会执行一次Add
	 * 使构造单推更便捷
	 *
	 * @param user UserEntity
	 * @param list PushList
	 */
	public PushBuilder(UserEntity user, PushList list) {
		add(user, list);
	}

	/**
	 * 添加一组目标用户与发送数据
	 *
	 * @param user 推送目标
	 * @param list PushList
	 * @return this 用于链式调用
	 */
	public PushBuilder add(UserEntity user, PushList list) {
		String pushId = user.getPushId();
		String jsonStr = list.toJsonStr();
		if (TextUtil.isEmpty(pushId) || TextUtil.isEmpty(jsonStr)) {
			return this;
		}
		bundles.add(new PushUtil.PushBundle(pushId, jsonStr));
		return this;
	}

	/**
	 * 将被添加的数据提交推送
	 *
	 * @return 提交推送是否成功
	 */
	public boolean commit() {
		return bundles.size() > 0 && PushUtil.push(bundles) > 0;
	}
}
