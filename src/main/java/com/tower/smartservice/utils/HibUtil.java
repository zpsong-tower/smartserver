package com.tower.smartservice.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.annotation.Nullable;

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
			e.printStackTrace();

			// 异常则销毁
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	private HibUtil() {
	}

	/**
	 * Session事务处理 (有返回值)
	 *
	 * @param handler ITransactionHandler
	 * @param <T>     需要返回的对象类型
	 * @return 泛型规定的返回对象
	 */
	@Nullable
	public static <T> T handle(ITransactionHandler<T> handler) {
		Session session = openSession();
		if (session == null) {
			return null;
		}
		T t;
		final Transaction transaction = session.beginTransaction();
		try {
			t = handler.handleAndReturn(session);

			// 事务提交
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t = null;
			try {
				// 处理失败则回滚
				transaction.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			// session必须关闭
			session.close();
		}
		return t;
	}

	/**
	 * Session事务处理 (无返回值)
	 *
	 * @param handler ITransactionHandler
	 */
	public static void handle(INotReturnTransactionHandler handler) {
		Session session = openSession();
		if (session == null) {
			return;
		}
		final Transaction transaction = session.beginTransaction();
		try {
			handler.handleOnly(session);

			// 事务提交
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 处理失败则回滚
				transaction.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			// session必须关闭
			session.close();
		}
	}

	/**
	 * 从SessionFactory中打开一个新的session
	 * 使用完需要close关闭掉
	 *
	 * @return Session
	 */
	@Nullable
	private static Session openSession() {
		try {
			return sessionFactory.openSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 事务处理接口 (有返回值)
	 *
	 * @param <T> 返回类型
	 */
	public interface ITransactionHandler<T> {
		/**
		 * 处理事务并返回一个对象
		 *
		 * @param session Session缓存
		 * @return 泛型规定的返回对象
		 */
		T handleAndReturn(Session session);
	}

	/**
	 * 事务处理接口 (无返回值)
	 */
	public interface INotReturnTransactionHandler {
		/**
		 * 仅处理事务，无返回值
		 *
		 * @param session Session缓存
		 */
		void handleOnly(Session session);
	}
}
