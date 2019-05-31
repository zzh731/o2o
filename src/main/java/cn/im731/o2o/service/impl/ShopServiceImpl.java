package cn.im731.o2o.service.impl;

import cn.im731.o2o.dao.ShopDao;
import cn.im731.o2o.dto.ShopExecution;
import cn.im731.o2o.entity.Shop;
import cn.im731.o2o.enums.ShopStateEnum;
import cn.im731.o2o.service.ShopService;
import cn.im731.o2o.util.ImageUtil;
import cn.im731.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.File;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopDao shopDao;

    @Override
    @Transactional//开启事务
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //1. 添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new RuntimeException("店铺创建失败");//必须是RuntimeException，事务才能回滚
            } else {
                if (shopImg != null) {
                    //2. 存储图片
                    try {
                        addShopImg(shop, shopImg);
                    } catch (Exception e) {
                        throw new RuntimeException("addShopImg error: " + e.getMessage());
                    }
                    //3. 更新店铺的图片信息到数据库
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new RuntimeException("更新图片地址失败");

                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("addShop error: "+e.getMessage());
        }
        return null;
    }


    /**
     * 将传来的图片起名，设置到shop
     * 生成缩略图
     * @param shop
     * @param shopImg
     */
    private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setShopImg(shopImgAddr);
    }
}
