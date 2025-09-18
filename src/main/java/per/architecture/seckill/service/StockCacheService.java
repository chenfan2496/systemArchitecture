package per.architecture.seckill.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import per.architecture.seckill.constant.RedisConstant;
import per.architecture.seckill.entity.SeckillItem;
import per.architecture.seckill.repository.SeckillItemRepository;

import java.util.HashMap;
import java.util.List;

import static per.architecture.seckill.constant.RedisConstant.STOCK_KEY_PREFIX;

@Service
public class StockCacheService implements InitializingBean {
    @Autowired
    private final Cache<String, Boolean> localStockCache;
    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisStockService redisStockService;
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
        return localStockCache.getIfPresent(itemId);
//        try {
//            return localStockCache.get(itemId,key -> {
//                String redisKey = RedisConstant.REDIS_STOCK_KEY_PREFIX + itemId;
//                Object value = redisTemplate.opsForValue().get(redisKey);
//                if(value != null || Integer.parseInt(value.toString()) > 0){
//                    return Boolean.TRUE;
//                }else {
//                    return Boolean.FALSE;
//                }
//            });
//        }catch (Exception e) {
//            String redisKey = RedisConstant.REDIS_STOCK_KEY_PREFIX + itemId;
//            Object value = redisTemplate.opsForValue().get(redisKey);
//            if(value != null || Integer.parseInt(value.toString()) > 0){
//                return Boolean.TRUE;
//            }else {
//                return Boolean.FALSE;
//            }
//        }
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

    @Override
    public void afterPropertiesSet() throws Exception {

    }


}
