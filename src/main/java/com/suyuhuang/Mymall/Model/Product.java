package com.suyuhuang.Mymall.Model;

import com.suyuhuang.Mymall.Constant.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Product {

    private Integer productId;
    private String productName;
    private ProductCategory category;
    private String imageUrl;
    private Integer stock;
    private String description;
    private Date createdDate;
    private Date lastModifiedDate;


}
