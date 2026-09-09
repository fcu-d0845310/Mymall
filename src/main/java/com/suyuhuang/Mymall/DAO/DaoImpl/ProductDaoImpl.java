package com.suyuhuang.Mymall.DAO.DaoImpl;

import com.suyuhuang.Mymall.DAO.ProductDao;
import com.suyuhuang.Mymall.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {

    private final JdbcClient jdbcClient;

    @Override
    public Product getProductByID(Integer productId) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";

        return jdbcClient.sql(sql)
                .param("productId",productId)
                .query(Product.class)
                .optional()//回傳optional<Product>
                .orElse(null);//查無商品的話 回傳null
    }
}
