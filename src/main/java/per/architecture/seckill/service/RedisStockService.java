package per.architecture.seckill.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static per.architecture.seckill.constant.RedisConstant.STOCK_KEY_PREFIX;

@Service
public class RedisStockService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String SECKILL_SCRIPT =
            "local stockKey = KEYS[1] "+
            "local requested = tonumber(ARGV[1]) " +
            "local serialNumber = ARGV[2] " +
            "local currentStock = tonumber(redis.call('GET', stockKey) or 0) " +
            "if currentStock < requested then " +
            "    return -1 " +
            "end " +
            "redis.call('DECRBY', stockKey, requested) " +
            "return 1 ";
    public RedisStockService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 初始化Redis库存
     */
    public void initStock(String itemId, Integer stock) {
        String key = STOCK_KEY_PREFIX + itemId;
        redisTemplate.opsForValue().set(key, stock);
    }

    /**
     * 原子扣减库存
     */
    public boolean deductStock(String itemId, String serialNumber) {
        String key = STOCK_KEY_PREFIX + itemId;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(SECKILL_SCRIPT, Long.class);
        Long result = redisTemplate.execute(redisScript,
                Collections.singletonList(key),
                1, serialNumber);
        return result != null && result == 1;
    }

    /**
     * 获取Redis中的库存
     */
    public int getRedisStock(String itemId) {
        String key = STOCK_KEY_PREFIX + itemId;
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? Integer.parseInt(value.toString()) : 0;
    }

    /**
     * 增加库存（用于补偿）
     */
    public void increaseStock(String itemId, Integer amount) {
        String key = STOCK_KEY_PREFIX + itemId;
        redisTemplate.opsForValue().increment(key, amount);
    }
}
