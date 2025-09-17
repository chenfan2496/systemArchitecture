package per.architecture.seckill.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.architecture.seckill.entity.SeckillItem;

@SpringBootTest
public class SeckillItemRepositoryTest {
    @Autowired
    private SeckillItemRepository seckillItemRepository;
    @Test
    public void addBatch() {
        SeckillItem seckillItem= new SeckillItem();
        for(int i=0;i<100000;i++) {
            seckillItem.setId((long)i);
        }

    }
}
