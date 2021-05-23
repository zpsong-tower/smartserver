package com.tower.smartservice.factory;

import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.utils.HibUtil;
import com.tower.smartservice.utils.TextUtil;

import javax.annotation.Nullable;

/**
 * 用户Factory
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/21 14:45
 */
public class UserFactory {
	/**
	 * 通过手机号查找用户
	 *
	 * @param phone 手机号(账号)
	 * @return UserEntity 如为null则不存在
	 */
	@Nullable
	public static UserEntity findByPhone(String phone) {
		return HibUtil.handle(session -> {
			Object obj = session
					.createQuery("from UserEntity where phone=:inPhone")
					.setParameter("inPhone", phone)
					.uniqueResult();
			if (obj instanceof UserEntity) {
				return (UserEntity) obj;
			}
			return null;
		});
	}

	/**
	 * 通过用户名查找用户
	 *
	 * @param name 用户名
	 * @return UserEntity 如为null则不存在
	 */
	@Nullable
	public static UserEntity findByName(String name) {
		return HibUtil.handle(session -> {
			Object obj = session
					.createQuery("from UserEntity where name=:inName")
					.setParameter("inName", name)
					.uniqueResult();
			if (obj instanceof UserEntity) {
				return (UserEntity) obj;
			}
			return null;
		});
	}

	/**
	 * 用户注册
	 *
	 * @param phone    手机号(账号)
	 * @param password 密码
	 * @param name     用户名
	 * @return UserEntity
	 */
	@Nullable
	public static UserEntity register(String phone, String password, String name) {
		UserEntity userEntity = new UserEntity();
		userEntity.setPhone(phone);
		userEntity.setPassword(encodePassword(password, phone)); // 对密码进行不可逆加密
		userEntity.setName(name);
		return HibUtil.handle(session -> {
			session.save(userEntity);
			return userEntity;
		});
	}

	/**
	 * 对密码进行不可逆加密
	 *
	 * @param password 密码
	 * @param phone    手机号 (用于处理盐值)
	 * @return 可存放在数据库的密码
	 */
	private static String encodePassword(String password, String phone) {
		String salt = "";
		if (phone != null && phone.length() > 10) {
			salt = phone.substring(3);
		}

		// MD5 + base64
		return TextUtil.encodeBase64(TextUtil.getMD5(password + salt));
	}
}
