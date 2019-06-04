package cn.im731.o2o.dao;

import cn.im731.o2o.BaseTest;
import cn.im731.o2o.entity.Area;
import cn.im731.o2o.entity.PersonInfo;
import cn.im731.o2o.entity.Shop;
import cn.im731.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryByShopId() {
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println(shop);
    }

    @Test
    @Ignore
    public void testInsertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
//        shop.setPriority(0);
        shop.setShopName("测试店铺1");
        shop.setShopDesc("我的新店铺");
        shop.setPhone("686731");
        shop.setShopImg("testImg");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");

        System.out.println("新增店铺："+shop);
        int effectedRows = shopDao.insertShop(shop);
        System.out.println("新增店铺："+shop);
        System.out.println(effectedRows);
    }

    @Test
    @Ignore
    public void testUpdateShop() {
        Shop shop = new Shop();
        shop.setShopId(5L);
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        shop.setShopDesc("我修改后的新店铺1");
        shop.setShopAddr("新的地址！");
        shop.setArea(area);

        System.out.println("修改店铺："+shop);
        int effectedRows = shopDao.updateShop(shop);
        System.out.println("修改店铺："+shop);
        System.out.println(effectedRows);
    }
}
