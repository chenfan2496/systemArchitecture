package per.architecture.seckill.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seckill_item")
public class SeckillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "original_price", length = 50)
    private String originalPrice;
    @Column(name = "seckill_price", length = 50)
    private String seckillPrice;
    @Column(name = "stock_count", length = 50)
    private String stockCount;
    @Column(name = "start_time", length = 50)
    private String startTime;
    @Column(name = "end_ime", length = 50)
    private LocalDateTime endTime;
    @Column(name = "create_time", length = 50)
    private LocalDateTime createdTime;
    @Column(name = "update_time", length = 50)
    private LocalDateTime updatedTime;
}
