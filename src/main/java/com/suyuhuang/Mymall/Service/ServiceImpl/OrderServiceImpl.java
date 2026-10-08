package com.suyuhuang.Mymall.Service.ServiceImpl;

import com.suyuhuang.Mymall.DAO.OrderDao;
import com.suyuhuang.Mymall.DAO.ProductDao;
import com.suyuhuang.Mymall.DAO.UserDao;
import com.suyuhuang.Mymall.Model.Order;
import com.suyuhuang.Mymall.Model.OrderItem;
import com.suyuhuang.Mymall.Model.Product;
import com.suyuhuang.Mymall.Model.User;
import com.suyuhuang.Mymall.Service.OrderService;
import com.suyuhuang.Mymall.dto.BuyItem;
import com.suyuhuang.Mymall.dto.CreatOrderRequest;
import com.suyuhuang.Mymall.dto.OrderQueryParams;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    @Override
    public Integer createOrder(Integer userId, CreatOrderRequest creatOrderRequest) {

        User user = userDao.getUserById(userId);
        if(user == null)
        {
            log.warn("該user不存在");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for(BuyItem item: creatOrderRequest.getBuyItemList())
        {
            Product product = productDao.getProductById(item.getProductId());

            if(product == null)
            {
                log.warn("商品不存在");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if(product.getStock() < item.getQuantity())
            {
                log.warn("商品庫存不足");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(product.getProductId(), product.getStock() - item.getQuantity());

            int amount = item.getQuantity() * product.getPrice();
            totalAmount+=amount;

            //轉換BuyItem to OderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }


        Integer orderId = orderDao.createOrder(userId,totalAmount);//建立訂單
        orderDao.createOrderItems(orderId,orderItemList);
        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemsList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList((orderItemsList));
        return order;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for(Order order: orderList)
        {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }
}
