package per.architecture.seckill.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import per.architecture.seckill.constant.SeckillResult;
import per.architecture.seckill.service.SeckillService;
import per.architecture.seckill.vo.ItemRequestInVo;

@RestController
@RequestMapping("/seckill")
public class SecKillController {
    @Autowired
    private final SeckillService seckillService;

    public SecKillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }

    @PostMapping("/items")
    public ResponseEntity<SeckillResult> seckill(
            @RequestBody ItemRequestInVo inVo) {

        // 参数验证
        if (StringUtils.isBlank(inVo.getItemId()) || StringUtils.isBlank(inVo.getUserId()) || StringUtils.isBlank(inVo.getSerialNumber())) {
            return ResponseEntity.badRequest().body(SeckillResult.FAILED);
        }
        // 执行秒杀
        SeckillResult result = seckillService.executeSeckill(inVo);
        return ResponseEntity.ok(result);
    }
}
