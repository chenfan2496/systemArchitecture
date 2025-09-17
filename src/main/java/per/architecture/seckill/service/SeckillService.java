package per.architecture.seckill.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.architecture.seckill.constant.SeckillResult;
import per.architecture.seckill.repository.SeckillOrderRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class SeckillService {
    private final DeduplicationService deduplicationService;
    private final StockCacheService stockCacheService;
    private final RedisStockService redisStockService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SeckillOrderRepository orderRepository;

    public SeckillService(DeduplicationService deduplicationService,
                          StockCacheService stockCacheService,
                          RedisStockService redisStockService,
                          KafkaTemplate<String, String> kafkaTemplate,
                          SeckillOrderRepository orderRepository) {
        this.deduplicationService = deduplicationService;
        this.stockCacheService = stockCacheService;
        this.redisStockService = redisStockService;
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepository = orderRepository;
    }
    public SeckillResult executeSeckill(String itemId, String userId, String serialNumber) {
        if(!deduplicationService.checkAndRecordSerial(serialNumber)) {
            return SeckillResult.REPEAT_REQUEST;
        }
        Integer localStock = stockCacheService.getStock(itemId);
        if (localStock <= 0) {
            deduplicationService.removeSerialRecord(serialNumber);
            return SeckillResult.OUT_OF_STOCK;
        }
        // 3. 更新本地缓存（减少本地库存）
        if (!stockCacheService.decreaseLocalStock(itemId)) {
            deduplicationService.removeSerialRecord(serialNumber);
            return SeckillResult.OUT_OF_STOCK;
        }
        // 4. Redis原子扣减库存[3,6](@ref)
        boolean redisSuccess = redisStockService.deductStock(itemId, serialNumber);
        if (!redisSuccess) {
            // Redis扣减失败，恢复本地缓存
            stockCacheService.updateLocalCache(itemId,
                    redisStockService.getRedisStock(Long.getLong(itemId)));
            deduplicationService.removeSerialRecord(serialNumber);
            return SeckillResult.OUT_OF_STOCK;
        }
        // 5. 发送Kafka消息异步创建订单[3](@ref)
        Map<String, String> message = new HashMap<>();
        message.put("itemId", itemId);
        message.put("userId", userId);
        message.put("serialNumber", serialNumber);
        message.put("timestamp", String.valueOf(System.currentTimeMillis()));

        try {
            kafkaTemplate.send("seckill-order-topic",
                    new ObjectMapper().writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return SeckillResult.SUCCESS;
    }
}
