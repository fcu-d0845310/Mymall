package com.suyuhuang.Mymall.Service;

import com.suyuhuang.Mymall.Model.Product;
import com.suyuhuang.Mymall.dto.ProductRequest;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer creatProduct(ProductRequest productRequest);
}
