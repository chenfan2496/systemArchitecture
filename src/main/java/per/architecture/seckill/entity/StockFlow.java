package per.architecture.seckill.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_flow")
@Data
public class StockFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;
    @Column(name = "item_id", length = 100)
    private Integer itemId;
    @Column(name = "order_id", length = 32)
    private String orderId;
    @Column(name = "change_amount")
    private Integer changeAmount;
    @Column(name = "current_stock")
    private Integer currentStock;
    @Column(name = "type")
    private Integer type;
    @Column(name = "serial_number", length = 64)
    private String serialNumber;
    @Column(name = "create_time")
    private LocalDateTime createTime;
}
