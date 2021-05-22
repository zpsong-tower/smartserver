package com.tower.smartservice.factory;

import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.utils.HibUtil;
import com.tower.smartservice.utils.TextUtil;
import org.hibernate.Session;

/**
 * 用户Factory
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/21 14:45
 */
public class UserFactory {
	/**
	 * 用户注册
	 *
	 * @param phone    手机号(账号)
	 * @param password 密码
	 * @param name     用户名
	 * @return UserEntity
	 */
	public static UserEntity register(String phone, String password, String name) {
		UserEntity userEntity = new UserEntity();
		userEntity.setPhone(phone);
		userEntity.setPassword(encodePassword(password, phone));
		userEntity.setName(name);
		Session session = HibUtil.session();
		session.beginTransaction();
		try {
			session.save(userEntity);
			session.getTransaction().commit();
			return userEntity;
		} catch (Exception e) {
			// 失败回滚
			session.getTransaction().rollback();
		}
		return null;
	}

	/**
	 * 对密码进行不可逆加密
	 *
	 * @param password 密码
	 * @param phone    手机号 (用于处理盐值)
	 * @return 可存放在数据库的密码
	 */
	private static String encodePassword(String password, String phone) {
		if (TextUtil.isEmpty(password)) {
			return null;
		}
		String salt = "";
		if (phone != null && phone.length() > 10) {
			salt = phone.substring(3).trim();
		}

		// MD5 + base64
		return TextUtil.encodeBase64(TextUtil.getMD5(password.trim() + salt));
	}
}
