package com.tower.smartservice.factory;

/**
 * BaseFactory
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/7 18:27
 */
public abstract class BaseFactory {
	/**
	 * 唯一结果的查询结果上限
	 */
	public static final int MAX_RESULTS_UNIQUE_RESULT = 1;

	/**
	 * 每页的查询结果上限
	 */
	public static final int MAX_RESULTS_EACH_PAGE = 20;
}
