package per.architecture.seckill.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import per.architecture.seckill.constant.RedisConstant;

@Service
public class StockCacheService {
    private final Cache<String, Integer> localStockCache;
    private final RedisTemplate<String, Object> redisTemplate;

    public StockCacheService(Cache<String, Integer> localStockCache,
                             RedisTemplate<String, Object> redisTemplate) {
        this.localStockCache = localStockCache;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取商品库存（优先从本地缓存获取)
     * @param itemId
     * @return
     */
    public Integer getStock(String itemId) {
        try {
            return localStockCache.get(itemId,key -> {
                String redisKey = RedisConstant.REDIS_STOCK_KEY_PREFIX + itemId;
                Object value = redisTemplate.opsForValue().get(redisKey);
                return value != null ? Integer.parseInt(value.toString()) : 0;
            });
        }catch (Exception e) {
            String redisKey = RedisConstant.REDIS_STOCK_KEY_PREFIX + itemId;
            Object value = redisTemplate.opsForValue().get(redisKey);
            return value != null ? Integer.parseInt(value.toString()) : 0;
        }
    }
    /**
     * 更新本地缓存
     */
    public void updateLocalCache(String itemId, Integer stock) {
        localStockCache.put(itemId,stock);
    }

    /**
     * 减少本地缓存库存
     */
    public boolean decreaseLocalStock(String itemId) {
        Integer currentStock = localStockCache.getIfPresent(itemId);
        if (currentStock != null && currentStock > 0) {
            localStockCache.put(itemId, currentStock - 1);
            return true;
        }
        return false;
    }
    /**
     * 初始化本地缓存
     */
    public void initLocalCache(String itemId, Integer stock) {
        localStockCache.put(itemId, stock);
    }
}
