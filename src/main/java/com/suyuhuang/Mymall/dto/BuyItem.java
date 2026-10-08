package com.suyuhuang.Mymall.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyItem {
    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;
}
