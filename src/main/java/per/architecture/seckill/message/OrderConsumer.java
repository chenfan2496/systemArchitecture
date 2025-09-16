package per.architecture.seckill.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import per.architecture.seckill.entity.SeckillOrder;
import per.architecture.seckill.entity.StockFlow;
import per.architecture.seckill.repository.SeckillOrderRepository;
import per.architecture.seckill.repository.StockFlowRepository;
import per.architecture.seckill.service.RedisStockService;

import java.math.BigDecimal;
import java.util.Map;

@Component
@Slf4j
public class OrderConsumer {
    private final SeckillOrderRepository orderRepository;
    private final StockFlowRepository stockFlowRepository;
    private final RedisStockService redisStockService;

    public OrderConsumer(SeckillOrderRepository orderRepository,
                         StockFlowRepository stockFlowRepository,
                         RedisStockService redisStockService) {
        this.orderRepository = orderRepository;
        this.stockFlowRepository = stockFlowRepository;
        this.redisStockService = redisStockService;
    }

    @KafkaListener(topics = "seckill-order-topic", groupId = "seckill-order-group")
    public void consumeOrderMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> data = mapper.readValue(message, Map.class);

            String itemId = data.get("itemId");
            String userId = data.get("userId");
            String serialNumber = data.get("serialNumber");
            // 检查订单是否已存在（幂等性保证）
            if (orderRepository.findBySerialNumber(serialNumber).isPresent()) {
                log.warn("订单已存在: {}", serialNumber);
                return;
            }

            // 创建订单
            createOrder(itemId, userId, serialNumber);

        } catch (Exception e) {
            log.error("处理订单消息异常: {}", message, e);
        }
    }
    /**
     * 创建订单（数据库操作）[3](@ref)
     */
    @Transactional
    public void createOrder(String itemId, String userId, String serialNumber) {
        // 生成订单ID
        String orderId = generateOrderId(userId);
        // 创建订单记录
        SeckillOrder order = new SeckillOrder();
        order.setId(Long.getLong(orderId));
        order.setUserId(Integer.valueOf(userId));
        order.setItemId(Integer.valueOf(itemId));
        order.setStatus(Integer.valueOf("0"));
        order.setSerialNumber(serialNumber);
        orderRepository.save(order);

        // 记录库存流水
        StockFlow stockFlow = new StockFlow();
        stockFlow.setItemId(Integer.valueOf(itemId));
        stockFlow.setOrderId(orderId);
        stockFlow.setChangeAmount(-1);
        stockFlow.setCurrentStock(redisStockService.getRedisStock(itemId));
        stockFlow.setType(Integer.valueOf(itemId));
        stockFlow.setSerialNumber(serialNumber);
        stockFlowRepository.save(stockFlow);

        log.info("订单创建成功: {}", orderId);
    }

    private String generateOrderId(String userId) {
        return System.currentTimeMillis() +
                String.format("%06d", Math.abs(userId.hashCode()) % 1000000);
    }
}
