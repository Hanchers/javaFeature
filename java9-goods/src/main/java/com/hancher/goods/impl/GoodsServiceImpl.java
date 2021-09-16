package com.hancher.goods.impl;

import com.hancher.goods.GoodsService;

public class GoodsServiceImpl implements GoodsService {
    @Override
    public String getGoodsNameById(String goodsId) {
        return "商品"+goodsId;
    }
}
