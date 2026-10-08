package com.suyuhuang.Mymall.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;
}
