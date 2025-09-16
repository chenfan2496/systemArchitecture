package per.architecture.seckill.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import per.architecture.seckill.constant.SeckillResult;
import per.architecture.seckill.service.SeckillService;

@RestController
@RequestMapping("/seckill")
public class SecKillController {
    @Autowired
    private final SeckillService seckillService;

    public SecKillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<SeckillResult> seckill(
            @PathVariable String itemId,
            @RequestHeader("userId") String userId,
            @RequestHeader("serialNumber") String serialNumber) {

        // 参数验证
        if (StringUtils.isBlank(itemId) || StringUtils.isBlank(userId) || StringUtils.isBlank(serialNumber)) {
            return ResponseEntity.badRequest().body(SeckillResult.FAILED);
        }
        // 执行秒杀
        SeckillResult result = seckillService.executeSeckill(itemId, userId, serialNumber);
        return ResponseEntity.ok(result);
    }
}
