package per.architecture.seckill.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seckill_order")
@Data
public class SeckillOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "status", columnDefinition = "TINYINT")
    private Integer status;
    @Column(name = "serial_number", length = 64)
    private String serialNumber;
    @Column(name = "create_time",columnDefinition = "datetime")
    private LocalDateTime createTime;
    @Column(name = "update_time",columnDefinition = "datetime")
    private LocalDateTime updateTime;
}
