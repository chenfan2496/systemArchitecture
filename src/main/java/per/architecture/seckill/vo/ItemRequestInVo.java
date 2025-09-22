package per.architecture.seckill.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestAttribute;

@Data
public class ItemRequestInVo {
    private String itemId;
    private String userId;
    private String serialNumber;
}
