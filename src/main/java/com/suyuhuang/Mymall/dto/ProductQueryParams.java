package com.suyuhuang.Mymall.dto;

import com.suyuhuang.Mymall.Constant.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQueryParams {
    //用來如果要傳遞多個參數的話 可以不用修改其他程式碼
    private ProductCategory category;
    private String search;
}
