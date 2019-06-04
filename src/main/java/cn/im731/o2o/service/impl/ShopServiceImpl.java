package cn.im731.o2o.service.impl;

import cn.im731.o2o.dao.ShopDao;
import cn.im731.o2o.dto.ShopExecution;
import cn.im731.o2o.entity.Shop;
import cn.im731.o2o.enums.ShopStateEnum;
import cn.im731.o2o.exceptions.ShopOperationException;
import cn.im731.o2o.service.ShopService;
import cn.im731.o2o.util.ImageUtil;
import cn.im731.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopDao shopDao;

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        //1. 判断是否需要修改图片
        if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
            //1.1 先查原来的图片是否为空，如果原来的图片不为空，则先删除
            Shop tempShop = shopDao.queryByShopId(shop.getShopId());
            if (tempShop.getShopImg() != null) {
                ImageUtil.deleteFileOrPath(tempShop.getShopImg());
            }
            //1.2 然后把新的图片加入
            addShopImg(shop, shopImgInputStream, fileName);
        }

        //2. 更新店铺信息
        shop.setLastEditTime(new Date());

        //3. 写入数据库
        int effectedRows = shopDao.updateShop(shop);
        if (effectedRows < 0) {
            return new ShopExecution(ShopStateEnum.INNER_ERROR);
        } else {
            shop = shopDao.queryByShopId(shop.getShopId());
            return new ShopExecution(ShopStateEnum.SUCCESS, shop);
        }

    }

    @Override
    @Transactional//开启事务
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {
            //1. 配置店铺状态
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            //2. 向数据库添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new RuntimeException("店铺创建失败");//必须是RuntimeException，事务才能回滚
            } else {
                if (shopImgInputStream != null) {
                    //2. 存储图片
                    try {
                        addShopImg(shop, shopImgInputStream, fileName);
                    } catch (Exception e) {
                        throw new RuntimeException("addShopImg error: " + e.getMessage());
                    }
                    //4. 更新店铺的图片信息到数据库
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new RuntimeException("更新图片地址失败");
                    } else {
                        return new ShopExecution(ShopStateEnum.CHECK, shop);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("addShop error: "+e.getMessage());
        }
        return null;
    }


    /**
     * 将传来的图片按照店铺ID随机起名，设置到shop的shop_img中
     * 生成缩略图
     */
    private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
        //获取shop图片目录的相对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        //生成缩略图,返回带文件名的相对路径
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
        shop.setShopImg(shopImgAddr);
    }
}
