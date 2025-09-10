-- 秒杀商品表
CREATE TABLE `seckill_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `original_price` decimal(10,2) NOT NULL COMMENT '原价',
  `seckill_price` decimal(10,2) NOT NULL COMMENT '秒杀价',
  `stock_count` int(11) NOT NULL COMMENT '库存数量',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_time` (`start_time`,`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀商品表';

-- 秒杀订单表
CREATE TABLE `seckill_order` (
  `id` varchar(32) NOT NULL COMMENT '订单ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `item_id` bigint(20) NOT NULL COMMENT '秒杀商品ID',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0-未支付,1-已支付,2-已取消',
  `serial_number` varchar(64) NOT NULL COMMENT '请求流水号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_serial` (`serial_number`),
  KEY `idx_user_item` (`user_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀订单表';

-- 库存流水表（用于对账和恢复）
CREATE TABLE `stock_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NOT NULL COMMENT '商品ID',
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单ID',
  `change_amount` int(11) NOT NULL COMMENT '库存变化数量',
  `current_stock` int(11) NOT NULL COMMENT '变化后库存',
  `type` tinyint(4) NOT NULL COMMENT '类型:1-扣减,2-恢复',
  `serial_number` varchar(64) NOT NULL COMMENT '流水号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_item` (`item_id`),
  KEY `idx_serial` (`serial_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存流水表';