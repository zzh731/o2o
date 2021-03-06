package cn.im731.o2o.service;

import cn.im731.o2o.BaseTest;
import cn.im731.o2o.dto.ShopExecution;
import cn.im731.o2o.entity.Area;
import cn.im731.o2o.entity.PersonInfo;
import cn.im731.o2o.entity.Shop;
import cn.im731.o2o.entity.ShopCategory;
import cn.im731.o2o.enums.ShopStateEnum;
import cn.im731.o2o.exceptions.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        ShopExecution shopExecution = shopService.getShopList(shopCondition, 1, 5);
        System.out.println(shopExecution);
    }

    @Test
    public void testModifyShop() throws ShopOperationException, FileNotFoundException {
        long shopId = 1;
        Shop shop = shopService.getByShopId(shopId);
        shop.setShopName("731早餐店");
        File shopImg = new File("E:/paint.jpg");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.modifyShop(shop, is, "ff.jpg");
        System.out.println(shopExecution);
    }

    @Test
    public void testAddShop() {
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
//        shop.setShopImg("testImg");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");

        File shopImg = new File("E:/shopImg/1.jpg");
        try {
            InputStream is = new FileInputStream(shopImg);
            ShopExecution shopExecution = shopService.addShop(shop, is, shopImg.getName());
            System.out.println("testAddShop新增店铺："+shopExecution);
            System.out.println(shopExecution.getState()+":"+shopExecution.getStateInfo());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
