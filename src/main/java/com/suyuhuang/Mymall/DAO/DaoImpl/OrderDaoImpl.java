package com.suyuhuang.Mymall.DAO.DaoImpl;

import com.suyuhuang.Mymall.DAO.OrderDao;
import com.suyuhuang.Mymall.Model.Order;
import com.suyuhuang.Mymall.Model.OrderItem;
import com.suyuhuang.Mymall.dto.OrderQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

        private final JdbcClient jdbcClient;

        @Transactional
        @Override
        public Integer createOrder(Integer userId, Integer totalAmount) {
                String sql = "INSERT INTO `order`(user_id, total_amount, created_date, last_modified_date) " +
                        "VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)"; //`這裡是跳脫保留字`
                Date now = new Date();
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcClient.sql(sql)
                        .param("userId",userId)
                        .param("totalAmount",totalAmount)
                        .param("createDate",now)
                        .param("lastModifiedDate",now)
                        .update(keyHolder,"order_id");
                return Objects.requireNonNull(keyHolder.getKey()).intValue();
        }

        @Override
        public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
                String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                        "VALUES (:orderId, :productId, :quantity, :amount)";
                //jdbcClient尚未提供batch
                for (OrderItem item : orderItemList)
                {
                        jdbcClient.sql(sql)
                                .param("orderId", orderId)
                                .param("productId", item.getProductId())
                                .param("quantity", item.getQuantity())
                                .param("amount", item.getAmount())
                                .update(); // 每次迴圈獨立發送一次 INSERT 請求
                }
        }

        @Override
        public Order getOrderById(Integer orderId) {
                String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                        "FROM `order` WHERE order_id = :orderId";
                return jdbcClient.sql(sql)
                        .param("orderId", orderId)
                        .query(Order.class)
                        .optional()
                        .orElse(null);
        }

        @Override
        public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
                String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                        "FROM order_item as oi " +
                        "LEFT JOIN product as p ON oi.product_id = p.product_id " +
                        "WHERE oi.order_id = :orderId";

                return jdbcClient.sql(sql)
                        .param("orderId", orderId)
                        .query(OrderItem.class)
                        .list();
        }

        @Override
        public Integer countOrder(OrderQueryParams orderQueryParams) {
                String sql = "SELECT count(*) FROM `order` WHERE　1=1";
                Map<String, Object> params = new HashMap<>();
                sql = addFilterSql(sql,params,orderQueryParams);

                sql+=" ORDER BY created_date DESC";

                sql+=" LIMIT :limit OFFSET :offset";

                params.put("limit",orderQueryParams.getLimit());
                params.put("offset",orderQueryParams.getOffset());

                return jdbcClient.sql(sql)
                                .params(params)
                                .query(Integer.class)
                                .optional()   // 查無資料時安全回傳 Optional.empty()
                                .orElse(0);

        }

        @Override
        public List<Order> getOrders(OrderQueryParams orderQueryParams) {
                String sql = "SELECT order_id, user_id, created_date,last_modified_date FROM `order` WHERE 1=1";
                Map<String, Object> params = new HashMap<>();
                sql = addFilterSql(sql,params,orderQueryParams);

                sql+=" ORDER BY created_date DESC";

                sql+=" LIMIT :limit OFFSET :offset";

                return jdbcClient.sql(sql)
                        .params(params)
                        .query(Order.class)
                        .list();
        }

        private String addFilterSql(String sql, Map<String, Object> params, OrderQueryParams orderQueryParams)
        {
                if(orderQueryParams.getUserId() != null)
                {
                        sql+=" AND user_id: useId";
                        params.put("userId",orderQueryParams.getUserId());
                }
                return sql;
        }


}
