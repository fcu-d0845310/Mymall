package com.suyuhuang.Mymall.Service;

import com.suyuhuang.Mymall.Model.Order;
import com.suyuhuang.Mymall.dto.CreatOrderRequest;
import com.suyuhuang.Mymall.dto.OrderQueryParams;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreatOrderRequest creatOrderRequest);
    Order getOrderById(Integer orderId);
    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
}
