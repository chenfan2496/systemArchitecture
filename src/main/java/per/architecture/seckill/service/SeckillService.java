//package per.architecture.seckill.service;
//
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class SeckillService {
//    private final DeduplicationService deduplicationService;
//    private final StockCacheService stockCacheService;
//    private final RedisStockService redisStockService;
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private final SeckillOrderRepository orderRepository;
//
//    public SeckillService(DeduplicationService deduplicationService,
//                          StockCacheService stockCacheService,
//                          RedisStockService redisStockService,
//                          KafkaTemplate<String, String> kafkaTemplate,
//                          SeckillOrderRepository orderRepository) {
//        this.deduplicationService = deduplicationService;
//        this.stockCacheService = stockCacheService;
//        this.redisStockService = redisStockService;
//        this.kafkaTemplate = kafkaTemplate;
//        this.orderRepository = orderRepository;
//    }
//}
