package com.tower.smartservice.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * hibernate工具类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/20 16:12
 */
public class HibUtil {
	// 全局SessionFactory
	private static SessionFactory sessionFactory;

	static { // 静态代码块初始化 SessionFactory
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // 从hibernate.cfg.xml文件初始化
				.build();
		try {
			sessionFactory = new MetadataSources(registry)
					.buildMetadata()
					.buildSessionFactory();
		} catch (Exception e) {
			// 异常则销毁
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
		}
	}

	private HibUtil() {
	}

	/**
	 * 获取全局的SessionFactory
	 *
	 * @return SessionFactory
	 */
	public static SessionFactory sessionFactory() {
		return sessionFactory;
	}

	/**
	 * 从SessionFactory中得到一个Session会话
	 *
	 * @return Session
	 */
	public static Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 关闭sessionFactory
	 */
	public static void closeFactory() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}
