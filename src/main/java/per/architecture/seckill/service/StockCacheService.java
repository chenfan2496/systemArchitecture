package per.architecture.seckill.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import per.architecture.seckill.constant.RedisConstant;

@Service
public class StockCacheService {
    private final Cache<String, Boolean> localStockCache;
    private final RedisTemplate<String, Object> redisTemplate;

    public StockCacheService(Cache<String, Boolean> localStockCache,
                             RedisTemplate<String, Object> redisTemplate) {
        this.localStockCache = localStockCache;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取商品库存（优先从本地缓存获取)
     * @param itemId
     * @return
     */
    public Boolean getStock(String itemId) {
        try {
            return localStockCache.get(itemId,key -> {
                String redisKey = RedisConstant.REDIS_STOCK_KEY_PREFIX + itemId;
                Object value = redisTemplate.opsForValue().get(redisKey);
                if(value != null || Integer.parseInt(value.toString()) > 0){
                    return Boolean.TRUE;
                }else {
                    return Boolean.FALSE;
                }
            });
        }catch (Exception e) {
            String redisKey = RedisConstant.REDIS_STOCK_KEY_PREFIX + itemId;
            Object value = redisTemplate.opsForValue().get(redisKey);
            if(value != null || Integer.parseInt(value.toString()) > 0){
                return Boolean.TRUE;
            }else {
                return Boolean.FALSE;
            }
        }
    }
    /**
     * 更新本地缓存
     */
    public void updateLocalCache(String itemId) {
        localStockCache.put(itemId,Boolean.FALSE);
    }

    /**
     * 初始化本地缓存
     */
    public void initLocalCache(String itemId, Integer stock) {
        if (stock > 0) {
            localStockCache.put(itemId, Boolean.TRUE);
        } else {
            localStockCache.put(itemId, Boolean.FALSE);
        }
    }
}
