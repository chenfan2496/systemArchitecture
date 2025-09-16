package per.architecture.seckill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.architecture.seckill.entity.SeckillOrder;

import java.util.Optional;

@Repository
public interface SeckillOrderRepository extends JpaRepository<SeckillOrder,Long> {
    /**
     * 根据流水号查询订单
     */
    Optional<SeckillOrder> findBySerialNumber(String serialNumber);
}
