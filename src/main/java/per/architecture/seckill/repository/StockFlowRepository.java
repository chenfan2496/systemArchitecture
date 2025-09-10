package per.architecture.seckill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.architecture.seckill.entity.StockFlow;

@Repository
public interface StockFlowRepository extends JpaRepository<StockFlow,Long> {
}
