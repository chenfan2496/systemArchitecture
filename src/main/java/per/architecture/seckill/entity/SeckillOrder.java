package per.architecture.seckill.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seckill_item")
@Data
public class SeckillOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "item_id")
    private Integer itemId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "serial_number", length = 64)
    private String serialNumber;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
