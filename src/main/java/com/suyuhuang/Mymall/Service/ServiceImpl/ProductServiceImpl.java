package com.suyuhuang.Mymall.Service.ServiceImpl;

import com.suyuhuang.Mymall.DAO.ProductDao;
import com.suyuhuang.Mymall.Model.Product;
import com.suyuhuang.Mymall.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductByID(productId);
    }
}
