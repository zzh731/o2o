package cn.im731.o2o.service;

import cn.im731.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
