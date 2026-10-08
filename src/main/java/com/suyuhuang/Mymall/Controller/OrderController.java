package com.suyuhuang.Mymall.Controller;


import com.suyuhuang.Mymall.Model.Order;
import com.suyuhuang.Mymall.Service.OrderService;
import com.suyuhuang.Mymall.dto.CreatOrderRequest;
import com.suyuhuang.Mymall.dto.OrderQueryParams;
import com.suyuhuang.Mymall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/users/{userid}/orders")
    public ResponseEntity<?> creatOrder(@PathVariable Integer userId, @RequestBody @Valid CreatOrderRequest creatOrderRequest){

        Integer orderId = orderService.createOrder(userId,creatOrderRequest);

        Order order = orderService.getOrderById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/users/{userid}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,@RequestParam(defaultValue = "10") @Max(1000) @Min(0)Integer limit,@RequestParam(defaultValue = "0") @Min(0) Integer offset)
    {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        List<Order> orderList = orderService.getOrders(orderQueryParams);

        Integer count = orderService.countOrder(orderQueryParams);

        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }


}
