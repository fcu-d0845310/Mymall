package com.suyuhuang.Mymall.DAO.DaoImpl;

import com.suyuhuang.Mymall.Constant.ProductCategory;
import com.suyuhuang.Mymall.DAO.ProductDao;
import com.suyuhuang.Mymall.Model.Product;
import com.suyuhuang.Mymall.dto.ProductQueryParams;
import com.suyuhuang.Mymall.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {

    private final JdbcClient jdbcClient;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT (*) FROM product WHERE 1=1";
        Map<String, Object> params = new HashMap<>();
        sql = addFilterSql(sql,params,productQueryParams);
        return jdbcClient.sql(sql)
                .params(params)
                .query(Integer.class)
                .optional()   // 查無資料時安全回傳 Optional.empty()
                .orElse(0);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "FROM product WHERE 1=1";
        Map<String, Object> params = new HashMap<>();

        sql = addFilterSql(sql,params,productQueryParams);

        sql+= " ORDER BY " +productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        sql+= " LIMIT :limit OFFSET :offset";

        params.put("limit",productQueryParams.getLimit());
        params.put("offset",productQueryParams.getOffset());

        return jdbcClient.sql(sql)
                .params(params)
                .query(Product.class)
                .list();
    }

    @Override
    public Product getProductById(Integer productId) {
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

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate" +
                " WHERE product_id = :productId ";
        Date now = new Date();
       jdbcClient.sql(sql)
                .param("productId", productId)
                .param("productName", productRequest.getProductName())
                .param("category", productRequest.getCategory().name())
                .param("imageUrl", productRequest.getImageUrl())
                .param("price", productRequest.getPrice())
                .param("stock", productRequest.getStock())
                .param("description", productRequest.getDescription())
                .param("lastModifiedDate", now)
                .update();
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product where product_id = :productId";
        jdbcClient.sql(sql)
                .param("productId",productId)
                .update();
    }

    private String addFilterSql(String sql,Map<String, Object> params,ProductQueryParams productQueryParams){
        if (productQueryParams.getCategory() != null)
        {
            sql += " AND category = :category";
            // Category 是 Enum，.name() 轉為字串
            params.put("category", productQueryParams.getCategory().name());
        }
        if(productQueryParams.getSearch() != null)
        {
            sql += " AND product_name LIKE search";
            // "%" 代表任意字符 放到map裡面才能夠生效
            params.put("search", "%" + productQueryParams.getSearch() + "%");
        }
        return sql;
    }
}
