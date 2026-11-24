package com.suyuhuang.Mymall.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CreatOrderRequest {
    //對應前端傳過來的json object
    @NotEmpty
    @Valid
    private List<BuyItem> buyItemList;
}
