package com.suyuhuang.Mymall.DAO.DaoImpl;

import com.suyuhuang.Mymall.DAO.ProductDao;
import com.suyuhuang.Mymall.Model.Product;
import com.suyuhuang.Mymall.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public Integer creatProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, " +
                ":createdDate, :lastModifiedDate)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Date now = new Date();
        jdbcClient.sql(sql)
                .param("productName", productRequest.getProductName())
                .param("category", productRequest.getCategory().toString())
                .param("imageUrl", productRequest.getImageUrl())
                .param("price", productRequest.getPrice())
                .param("stock", productRequest.getStock())
                .param("description", productRequest.getDescription())
                .param("createdDate", now)
                .param("lastModifiedDate", now)
                .update(keyHolder, "product_id"); // 第二個參數填寫資料庫的主鍵欄位名稱

        return Objects.requireNonNull(keyHolder.getKey()).intValue();

    }
}
