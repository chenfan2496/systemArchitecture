package per.architecture.seckill.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seckill_item")
public class SeckillOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "user_id", length = 50)
    private String userId;
    @Column(name = "item_id", length = 50)
    private String itemId;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "serial_number", length = 50)
    private String serialNumber;
    @Column(name = "create_time", length = 50)
    private LocalDateTime createTime;
    @Column(name = "update_time", length = 50)
    private LocalDateTime updateTime;
}
