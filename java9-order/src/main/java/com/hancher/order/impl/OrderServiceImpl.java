package com.hancher.order.impl;

import com.hancher.order.OrderService;

public class OrderServiceImpl implements OrderService {
    @Override
    public String getOrderGoodsName(String orderId) {
        String goodsId = "Good"+orderId;
        return "";
    }
}
