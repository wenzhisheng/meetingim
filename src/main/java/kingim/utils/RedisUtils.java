package kingim.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class RedisUtils {

	private static RedisTemplate<String, Object> redisTemplate = SpringContextHolder
			.getBean("redisTemplate");

	public static void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public static Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public static Object getList(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * key是否存在
	 *
	 * @return
	 */
	public static boolean exists(final String key) {
		Boolean execute = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
		return execute;
	}

	/**
	 * value的长度
	 *
	 * @param key
	 * @return
	 */
	public static long llen(String key) {
		return redisTemplate.boundListOps(key).size();
	}

	/**
	 * lpop
	 *
	 * @param key
	 * @return
	 */
	public static Object lpop(String key) {
		return redisTemplate.boundListOps(key).leftPop();
	}

	/**
	 * rpop
	 *
	 * @param key
	 * @return
	 */
	public static Object rpop(String key) {
		return redisTemplate.boundListOps(key).rightPop();
	}

	/**
	 * lpush
	 *
	 * @param key
	 * @return
	 */
	public static void lpush(String key, String value) {
		redisTemplate.boundListOps(key).leftPush(value);
	}

	/**
	 * 获取list
	 * @param key
	 *        return :list<Object>
	 */
	public static List<Object> getObjList(String key){
		List<Object> range = redisTemplate.boundListOps(key).range(0, -1);
		return range;
	}

	/**
	 * 删除list中的一项
	 * @param key
	 * @param o
	 */
	public static void removeOneOfList(String key, Object o){
		redisTemplate.boundListOps(key).remove(1, o);
	}

}