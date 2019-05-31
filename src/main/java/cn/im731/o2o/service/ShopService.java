package cn.im731.o2o.service;

import cn.im731.o2o.dto.ShopExecution;
import cn.im731.o2o.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;


public interface ShopService {
    ShopExecution addShop(Shop shop, InputStream shopImg, String fileName);
}