package per.architecture.seckill.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.architecture.seckill.entity.SeckillItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@SpringBootTest
public class SeckillItemRepositoryTest {
    @Autowired
    private SeckillItemRepository seckillItemRepository;
    @Test
    public void addBatch() {
        SeckillItem seckillItem= new SeckillItem();
        Date startDate = DateUtil.parse("2024-01-01", "yyyy-MM-dd"); // 开始日期
        Date endDate = DateUtil.parse("2024-12-31", "yyyy-MM-dd");   // 结束日期
        for(int i=0;i<100000;i++) {
            seckillItem.setId((long)i);
            seckillItem.setSeckillPrice(new BigDecimal(RandomUtil.randomInt(10,10000)));
            seckillItem.setOriginalPrice(new BigDecimal(RandomUtil.randomInt(10,10000)));
            seckillItem.setName(RandomUtil.randomString(12));
            seckillItem.setStockCount(RandomUtil.randomInt(10000,1000000));
            seckillItem.setCreatedTime(RandomUtil.randomDay(10,100).toLocalDateTime());
            seckillItem.setEndTime(RandomUtil.randomDay(10,100).toLocalDateTime());
            seckillItem.setStartTime(RandomUtil.randomDay(10,100).toLocalDateTime());
            seckillItem.setUpdatedTime(RandomUtil.randomDay(10,100).toLocalDateTime());
        }
    }


}
