package cn.im731.o2o.dao;

import cn.im731.o2o.entity.Shop;

public interface ShopDao {
    Shop queryByShopId(long shopId);

    int insertShop(Shop shop);

    int updateShop(Shop shop);
}
