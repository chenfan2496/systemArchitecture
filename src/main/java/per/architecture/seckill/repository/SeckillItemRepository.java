package per.architecture.seckill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.architecture.seckill.entity.SeckillItem;

@Repository
public interface SeckillItemRepository extends JpaRepository<SeckillItem,Long> {
}
