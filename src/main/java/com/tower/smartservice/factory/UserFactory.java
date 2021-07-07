package com.tower.smartservice.factory;

import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.bean.db.UserFollowEntity;
import com.tower.smartservice.utils.HibUtil;
import com.tower.smartservice.utils.TextUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 用户Factory
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/21 14:45
 */
public class UserFactory extends BaseFactory {
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
		return update(user);
	}

	/**
	 * 更新用户信息到数据库
	 *
	 * @param user UserEntity
	 * @return UserEntity
	 */
	@Nullable
	public static UserEntity update(UserEntity user) {
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
	 * @return UserEntity
	 */
	@Nullable
	public static UserEntity bindPushId(@Nonnull UserEntity user, String pushId) {
		if (TextUtil.isEmpty(pushId)) {
			return null;
		}
		if (pushId.equals(user.getPushId())) {
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
				return update(user);
			}
		}
	}

	/**
	 * 获取联系人列表
	 *
	 * @param self UserEntity
	 * @return 联系人列表
	 */
	@Nullable
	public static List<UserEntity> getContacts(UserEntity self) {
		if (self == null) {
			return null;
		}
		return HibUtil.handle(session -> {
			// 操作懒加载的数据 需要重新load一次
			session.load(self, self.getId());

			// 获取我关注的人的关系列表
			Set<UserFollowEntity> flows = self.getFollowing();

			// return flows.stream()
			// 		.map(UserFollowEntity::getTarget)
			// 		.collect(Collectors.toList());
			List<UserEntity> users = new ArrayList<>();
			for (UserFollowEntity flow : flows) {
				users.add(flow.getTarget());
			}
			return users;
		});
	}

	/**
	 * 关注他人
	 *
	 * @param origin   发起者
	 * @param target   被关注的人
	 * @param nickname 备注名
	 * @return 被关注人的信息
	 */
	@Nullable
	public static UserEntity follow(UserEntity origin, UserEntity target, String nickname) {
		if (origin == null || target == null) {
			return null;
		}

		// 查询是否已关注
		UserFollowEntity follow = getUserFollow(origin, target);
		if (follow != null) {
			// 已关注 直接返回
			return follow.getTarget();
		}

		return HibUtil.handle(session -> {
			// 操作懒加载的数据 需要重新load一次
			session.load(origin, origin.getId());
			session.load(target, target.getId());

			// 创建新的关注用户关系
			// 关系中包含 发起者，被关注者和备注名
			UserFollowEntity originFollow = new UserFollowEntity();
			originFollow.setOrigin(origin);
			originFollow.setTarget(target);
			if (!TextUtil.isEmpty(nickname)) {
				originFollow.setNickname(nickname);
			}

			// 设计为互关 当用户关注他人时，让被关注人也关注该用户
			// 被关注者对该用户默认没有备注名
			UserFollowEntity targetFollow = new UserFollowEntity();
			targetFollow.setOrigin(target);
			targetFollow.setTarget(origin);

			// 保存数据库
			session.save(originFollow);
			session.save(targetFollow);

			return target;
		});
	}

	/**
	 * 查询是否已关注
	 *
	 * @param origin 发起者
	 * @param target 被关注人
	 * @return UserFollowEntity 返回Null则认为没有关注
	 */
	@Nullable
	public static UserFollowEntity getUserFollow(UserEntity origin, UserEntity target) {
		if (origin == null || target == null) {
			return null;
		}
		return HibUtil.handle(session -> {
			Object obj = session.createQuery("from UserFollowEntity where originId=:originId and targetId=:targetId")
					.setParameter("originId", origin.getId())
					.setParameter("targetId", target.getId())
					.setMaxResults(MAX_RESULTS_UNIQUE_RESULT)
					.uniqueResult();
			if (obj instanceof UserFollowEntity) {
				return (UserFollowEntity) obj;
			}
			return null;
		});
	}

	/**
	 * 通过用户名模糊查询用户
	 *
	 * @param name 用户名 允许为空
	 * @return 查询到的用户集合
	 */
	@SuppressWarnings("unchecked")
	public static List<UserEntity> search(@Nullable String name) {
		if (TextUtil.isEmpty(name)) {
			name = "";
		}
		String searchName = "%" + name + "%";
		return HibUtil.handle(session -> {
			// name忽略大小写 使用模糊查询
			// 完善过个人信息的用户才能被查询到 即头像不为空
			return (List<UserEntity>) session.createQuery("from UserEntity where lower(name) like :name and portrait is not null")
					.setParameter("name", searchName)
					.setMaxResults(MAX_RESULTS_EACH_PAGE)
					.list();
		});
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
