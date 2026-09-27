package com.suyuhuang.Mymall.DAO;

import com.suyuhuang.Mymall.Constant.ProductCategory;
import com.suyuhuang.Mymall.Model.Product;
import com.suyuhuang.Mymall.dto.ProductQueryParams;
import com.suyuhuang.Mymall.dto.ProductRequest;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer creatProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);
}
