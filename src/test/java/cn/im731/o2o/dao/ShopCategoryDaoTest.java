package cn.im731.o2o.dao;

import cn.im731.o2o.BaseTest;
import cn.im731.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(new ShopCategory());
        System.out.println(shopCategories);

        ShopCategory shopCategory1 = new ShopCategory();
        ShopCategory shopCategory2 = new ShopCategory();
        shopCategory2.setShopCategoryId(1L);
        shopCategory1.setParent(shopCategory2);
        shopCategories = shopCategoryDao.queryShopCategory(shopCategory1);
        System.out.println(shopCategories);
    }
}
