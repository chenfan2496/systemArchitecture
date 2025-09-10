package per.architecture.seckill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.architecture.seckill.entity.SeckillOrder;

@Repository
public interface SeckillOrderRepository extends JpaRepository<SeckillOrder,Long> {
}
