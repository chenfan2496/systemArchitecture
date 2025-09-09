package per.architecture.seckill.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static per.architecture.seckill.constant.RedisConstant.DUPLICATE_KEY_PREFIX;

@Service
public class DeduplicationService {
    private final RedisTemplate<String, Object> redisTemplate;

    public DeduplicationService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 检查并记录流水号，实现幂等性控制
     * @param serialNumber 流水号
     * @return true-首次请求，false-重复请求
     */
    public boolean checkAndRecordSerial(String serialNumber) {
        String key = DUPLICATE_KEY_PREFIX + serialNumber;
        // 设置键值并设置过期时间（5分钟）
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMinutes(5));
        return Boolean.TRUE.equals(result);
    }

    /**
     * 删除流水号记录（用于异常情况清理）
     */
    public void removeSerialRecord(String serialNumber) {
        String key = DUPLICATE_KEY_PREFIX + serialNumber;
        redisTemplate.delete(key);
    }
}
