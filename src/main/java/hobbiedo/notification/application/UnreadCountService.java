// package hobbiedo.notification.application;
//
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.stereotype.Service;
//
// import lombok.RequiredArgsConstructor;
//
// @Service
// @RequiredArgsConstructor
// public class UnreadCountService {
//
// 	private static final String UNREAD_COUNT_KEY = "unread_count";
// 	private final RedisTemplate<String, String> redisTemplate;
//
// 	public void incrementUnreadCount(String uuid) {
// 		String key = UNREAD_COUNT_KEY + ":" + uuid;
// 		redisTemplate.opsForValue().increment(key, 1);
// 	}
//
// 	public void decrementUnreadCount(String uuid) {
// 		String key = UNREAD_COUNT_KEY + ":" + uuid;
// 		redisTemplate.opsForValue().decrement(key, 1);
// 	}
//
// 	public int getUnreadCount(String uuid) {
// 		String key = UNREAD_COUNT_KEY + ":" + uuid;
// 		String countStr = redisTemplate.opsForValue().get(key);
// 		return countStr == null ? 0 : Integer.parseInt(countStr);
// 	}
//
// 	public void deleteUnreadCount(String userId) {
// 		String key = UNREAD_COUNT_KEY + ":" + userId;
// 		redisTemplate.delete(key);
// 	}
// }
