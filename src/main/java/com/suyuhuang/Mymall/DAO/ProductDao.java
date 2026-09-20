package com.suyuhuang.Mymall.DAO;

import com.suyuhuang.Mymall.Model.Product;
import com.suyuhuang.Mymall.dto.ProductRequest;

public interface ProductDao {

    Product getProductByID(Integer productId);
    Integer creatProduct(ProductRequest productRequest);
}
