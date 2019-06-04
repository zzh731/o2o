package cn.im731.o2o.service;

import cn.im731.o2o.dto.ShopExecution;
import cn.im731.o2o.entity.Shop;
import cn.im731.o2o.exceptions.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;


public interface ShopService {
    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

    ShopExecution addShop(Shop shop, InputStream shopImg, String fileName);
}
