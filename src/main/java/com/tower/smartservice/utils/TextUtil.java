package com.tower.smartservice.utils;

import com.tower.smartservice.provider.GsonProvider;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * 文本工具类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/20 16:34
 */
public class TextUtil {
	/**
	 * 字符串判空
	 *
	 * @param str 要检查的字符串
	 * @return true: str为null或长度为零
	 */
	public static boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 计算一个字符串的MD5信息
	 *
	 * @param str 原始字符串
	 * @return 字符串的MD5值
	 */
	public static String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 计算md5函数
			md.update(str.getBytes());

			// digest()最后确定返回md5 hash值，返回值为8位字符串
			// BigInteger函数将8位的字符串转换成16位hex值，得到字符串形式的hash值
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * 对一个字符串进行Base64编码
	 *
	 * @param str 原始字符串
	 * @return 进行Base64编码后的字符串
	 */
	public static String encodeBase64(String str) {
		return Base64.getEncoder().encodeToString(str.getBytes());
	}

	/**
	 * 把任意类的实例转换为Json字符串
	 *
	 * @param obj Object
	 * @return Json字符串
	 */
	public static String toJson(Object obj) {
		return GsonProvider.getGson().toJson(obj);
	}
}

