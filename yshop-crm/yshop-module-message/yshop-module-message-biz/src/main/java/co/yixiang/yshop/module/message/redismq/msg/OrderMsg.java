package co.yixiang.yshop.module.message.redismq.msg;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderMsg implements Serializable {
    private String orderId;
}
