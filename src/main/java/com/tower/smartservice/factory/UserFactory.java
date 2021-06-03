package com.tower.smartservice.factory;

import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.utils.HibUtil;
import com.tower.smartservice.utils.TextUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * 用户Factory
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/21 14:45
 */
public class UserFactory {
	private static final Object PUSH_ID_LOCK = new Object();

	/**
	 * 通过手机号查找用户
	 *
	 * @param phone 手机号(账号)
	 * @return UserEntity 如为Null则不存在
	 */
	@Nullable
	public static UserEntity findByPhone(String phone) {
		if (TextUtil.isEmpty(phone)) {
			return null;
		}
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
	 * @return UserEntity 如为Null则不存在
	 */
	@Nullable
	public static UserEntity findByName(String name) {
		if (TextUtil.isEmpty(name)) {
			return null;
		}
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
	 * 通过Token查找用户
	 *
	 * @param token Token
	 * @return UserEntity 如为Null则不存在
	 */
	@Nullable
	public static UserEntity findByToken(String token) {
		if (TextUtil.isEmpty(token)) {
			return null;
		}
		return HibUtil.handle(session -> {
			Object obj = session
					.createQuery("from UserEntity where token=:inToken")
					.setParameter("inToken", token)
					.uniqueResult();
			if (obj instanceof UserEntity) {
				return (UserEntity) obj;
			}
			return null;
		});
	}

	/**
	 * 通过Id查找用户
	 *
	 * @param id Id
	 * @return UserEntity 如为Null则不存在
	 */
	@Nullable
	public static UserEntity findById(String id) {
		if (TextUtil.isEmpty(id)) {
			return null;
		}
		return HibUtil.handle(session -> {
			return session.get(UserEntity.class, id);
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
		if (TextUtil.isEmpty(phone) || TextUtil.isEmpty(password) || TextUtil.isEmpty(name)) {
			return null;
		}
		UserEntity user = new UserEntity();
		user.setPhone(phone);
		user.setPassword(encodePassword(password, phone)); // 对密码进行不可逆加密
		user.setName(name);
		return HibUtil.handle(session -> {
			session.save(user);
			return user;
		});
	}

	/**
	 * 用户登录
	 *
	 * @param phone    手机号(账号)
	 * @param password 密码
	 * @return UserEntity
	 */
	@Nullable
	public static UserEntity login(String phone, String password) {
		if (TextUtil.isEmpty(phone) || TextUtil.isEmpty(password)) {
			return null;
		}

		// 把密码进行注册存入数据库时同样的处理
		String encodePassword = encodePassword(password, phone);
		UserEntity user = HibUtil.handle(session -> {
			Object obj = session
					.createQuery("from UserEntity where phone=:inPhone and password=:inPassword")
					.setParameter("inPhone", phone)
					.setParameter("inPassword", encodePassword)
					.uniqueResult();
			if (obj instanceof UserEntity) {
				return (UserEntity) obj;
			}
			return null;
		});
		if (user != null) {
			// 对User进行登录操作，更新Token
			user = updateToken(user);
		}
		return user;
	}

	/**
	 * 更新Token
	 *
	 * @param user UserEntity
	 * @return UserEntity
	 */
	private static UserEntity updateToken(@Nonnull UserEntity user) {
		String newToken = TextUtil.encodeBase64(UUID.randomUUID().toString());
		user.setToken(newToken);
		return updateUser(user);
	}

	/**
	 * 更新用户信息到数据库
	 *
	 * @param user UserEntity
	 * @return UserEntity
	 */
	@Nullable
	public static UserEntity updateUser(UserEntity user) {
		if (user == null) {
			return null;
		}
		return HibUtil.handle(session -> {
			session.saveOrUpdate(user);
			return user;
		});
	}

	/**
	 * 给当前的账户绑定PushId
	 *
	 * @param user   UserEntity
	 * @param pushId PushId
	 * @return User
	 */
	@Nullable
	public static UserEntity bindPushId(@Nonnull UserEntity user, String pushId) {
		if (TextUtil.isEmpty(pushId)) {
			return null;
		}
		if (pushId.equalsIgnoreCase(user.getPushId())) {
			// 如果当前需要绑定的PushId，之前已经绑定过了
			// 那么不需要额外绑定
			return user;
		} else {
			synchronized (PUSH_ID_LOCK) {
				// 有其他账户绑定了这个PushId则取消其绑定，避免推送混乱
				HibUtil.handle(session -> {
					List userList = session
							.createQuery("from UserEntity where lower(pushId)=:inPushId and id!=:inId")
							.setParameter("inPushId", pushId.toLowerCase())
							.setParameter("inId", user.getId())
							.list();
					for (Object o : userList) {
						if (o instanceof UserEntity) {
							((UserEntity) o).setPushId(null);
						}
						session.saveOrUpdate(o);
					}

				});
				if (!TextUtil.isEmpty(user.getPushId())) {
					// TODO 向当前账户之前的PushId推送一条下线通知
				}

				// 更新新的PushId
				user.setPushId(pushId);
				return updateUser(user);
			}
		}
	}

	/**
	 * 对密码进行不可逆加密
	 *
	 * @param password 密码
	 * @param phone    手机号 (用于处理盐值)
	 * @return 可存放在数据库的密码
	 */
	private static String encodePassword(@Nonnull String password, @Nonnull String phone) {
		String salt = "";
		if (phone.length() > 10) {
			salt = phone.substring(3);
		}

		// MD5 + base64
		return TextUtil.encodeBase64(TextUtil.getMD5(password + salt));
	}
}
