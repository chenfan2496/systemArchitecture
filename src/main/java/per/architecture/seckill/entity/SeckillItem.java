package per.architecture.seckill.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "seckill_item")
public class SeckillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "original_price", precision = 10, scale = 2)
    private BigDecimal originalPrice;
    @Column(name = "seckill_price", precision = 10, scale = 2)
    private BigDecimal seckillPrice;
    @Column(name = "stock_count")
    private Integer stockCount;
    @Column(name = "start_time",columnDefinition = "datetime")
    private String startTime;
    @Column(name = "end_time",columnDefinition = "datetime")
    private LocalDateTime endTime;
    @Column(name = "create_time",columnDefinition = "datetime")
    private LocalDateTime createdTime;
    @Column(name = "update_time",columnDefinition = "datetime")
    private LocalDateTime updatedTime;
}
