package org.anson.miniProject.tool.helper.id;

import org.anson.miniProject.tool.util.id.IdWorker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * id 生成 (主键生成)
 */
public class IdHelper {

	private static final IdWorker idWorker = new IdWorker(0, 0);

	private static final AtomicInteger atomic = new AtomicInteger();

	/**
	 * 下一个 id
	 *
	 * @return
	 */
	public static String nextId() {
		return idWorker.nextId().toString();
	}

	/**
	 * @Description: 自增长
	 * @params:
	 * @Author: zlf
	 * @Date: 2018-12-08
	 */
	public static Integer nextId(Integer maxId) {
		atomic.set(maxId);
		return atomic.incrementAndGet();
	}
}
