package per.architecture.seckill.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_flow")
public class StockFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;
    @Column(name = "item_id", length = 100)
    private String itemId;
    @Column(name = "order_id", length = 50)
    private String orderId;
    @Column(name = "change_amount", length = 50)
    private String changeAmount;
    @Column(name = "current_stock", length = 50)
    private String currentStock;
    @Column(name = "type", length = 50)
    private String type;
    @Column(name = "serial_number", length = 50)
    private String serialNumber;
    @Column(name = "create_time", length = 50)
    private LocalDateTime createTime;
}
