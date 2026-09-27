package com.suyuhuang.Mymall.Service.ServiceImpl;

import com.suyuhuang.Mymall.Constant.ProductCategory;
import com.suyuhuang.Mymall.DAO.ProductDao;
import com.suyuhuang.Mymall.Model.Product;
import com.suyuhuang.Mymall.Service.ProductService;
import com.suyuhuang.Mymall.dto.ProductQueryParams;
import com.suyuhuang.Mymall.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer creatProduct(ProductRequest productRequest) {
        return productDao.creatProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct (productId,productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
