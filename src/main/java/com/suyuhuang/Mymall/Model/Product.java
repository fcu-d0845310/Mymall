package com.suyuhuang.Mymall.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Product {

    private Integer productId;
    private String productName;
    private String category;
    private String imageUrl;
    private Integer stock;
    private String description;
    private Date createdDate;
    private Date lastModifiedDate;


}
