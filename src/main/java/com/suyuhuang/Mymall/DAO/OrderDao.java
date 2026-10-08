package com.suyuhuang.Mymall.DAO;

import com.suyuhuang.Mymall.Model.Order;
import com.suyuhuang.Mymall.Model.OrderItem;
import com.suyuhuang.Mymall.dto.OrderQueryParams;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId,Integer totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
    Order getOrderById(Integer orderId);
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
}
