package per.architecture.seckill.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.architecture.seckill.entity.SeckillItem;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
public class RedisStockServiceTest {
    @Autowired
    private RedisStockService redisStockService;
    @Test
    public void initStockTest() {
        //redisStockService.initStock("1000",100000);
        redisStockService.deductStock("1000", IdUtil.getSnowflakeNextIdStr());
        Assertions.assertEquals(redisStockService.getRedisStock("1000"),99999);
    }
 }
