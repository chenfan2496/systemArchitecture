package per.architecture.seckill.message;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final SeckillOrderRepository orderRepository;
    @Autowired
    private final StockFlowRepository stockFlowRepository;
    @Autowired
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
           String itemIdStr = data.get("itemId");
            Long itemId = Long.parseLong(data.get("itemId"));
            Long userId = Long.parseLong(data.get("userId"));
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
    public void createOrder(Long itemId, Long userId, String serialNumber) {
        // 生成订单ID
        String orderId = generateOrderId(userId);
        // 创建订单记录
        SeckillOrder order = new SeckillOrder();
        order.setId(Long.parseLong(orderId));
        order.setUserId(userId);
        order.setItemId(itemId);
        order.setStatus(Integer.valueOf("0"));
        order.setSerialNumber(serialNumber);
        orderRepository.save(order);

        // 记录库存流水
        StockFlow stockFlow = new StockFlow();
        stockFlow.setItemId(itemId);
        stockFlow.setOrderId(orderId);
        stockFlow.setChangeAmount(-1);
        stockFlow.setCurrentStock(redisStockService.getRedisStock(itemId.toString()));
        stockFlow.setType(1);
        stockFlow.setSerialNumber(serialNumber);
        stockFlowRepository.save(stockFlow);

        log.info("订单创建成功: {}", orderId);
    }

    private String generateOrderId(Long userId) {
        return IdUtil.getSnowflakeNextIdStr();
    }
}
