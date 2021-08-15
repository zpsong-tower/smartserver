package com.tower.smartservice.utils;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.io.IOException;
import java.util.List;

/**
 * 个推工具类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/15 19:12
 */
public class PushUtil {
	private static final String APP_ID = "";

	private static final String APP_KEY = "";

	private static final String MASTER_SECRET = "";

	private static final String HOST = "http://sdk.open.api.igexin.com/apiex.htm";

	private PushUtil() {
	}

	/**
	 * 批量单推
	 *
	 * @param bundles 推送Bundle
	 * @return 被成功添加的数据数目 0: 推送未能成功发送
	 */
	public static int push(List<PushBundle> bundles) {
		IGtPush pusher = new IGtPush(HOST, APP_KEY, MASTER_SECRET);
		IBatch batch = pusher.getBatch();

		// 如果有数据被添加且没有异常 计数+1
		int pushAmount = 0;

		for (PushBundle bundle : bundles) {
			try {
				batch.add(bundle.message, bundle.target);
				pushAmount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (pushAmount == 0) {
			// 没有数据被添加成功 直接返回0
			return 0;
		}
		IPushResult result = null;
		try {
			result = batch.submit();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				// 失败情况下尝试重复发送一次
				batch.retry();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (result == null) {
			return 0;
		}
		return pushAmount;
	}

	/**
	 * 推送Bundle
	 * 每个PushBundle包含了推送的目标和该目标所需的数据信息
	 */
	public static class PushBundle {
		Target target = new Target();

		SingleMessage message = new SingleMessage();

		/**
		 * 构造函数
		 *
		 * @param pushId  推送Id
		 * @param jsonStr 以Json字符串形式存储的需要推送的数据信息
		 */
		public PushBundle(String pushId, String jsonStr) {
			initTarget(pushId);
			initMessage(jsonStr);
		}

		private void initTarget(String pushId) {
			// 设置推送目标
			target.setAppId(APP_ID);
			target.setClientId(pushId);
		}

		private void initMessage(String jsonStr) {
			// 透传消息模板 (不在通知栏显示)
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(APP_ID);
			template.setAppkey(APP_KEY);
			template.setTransmissionContent(jsonStr);
			template.setTransmissionType(2); // 1：立即启动APP 2：客户端收到消息后需要自行处理

			// 单推消息模型
			message.setOffline(true); // 用户当前不在线时，是否离线存储
			message.setOfflineExpireTime(24 * 3600 * 1000); // 离线有效时间，单位为毫秒
			message.setData(template);
		}
	}
}
