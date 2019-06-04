package cn.im731.o2o.dao;

import cn.im731.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /**
     * 分页查询店铺
     * @param shopCondition 查询条件
     * @param rowIndex 从第几行开始取
     * @param pageSize 取几个
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    //返回条数，用于分页
    int queryShopConut(@Param("shopCondition") Shop shopCondition);

    Shop queryByShopId(long shopId);

    int insertShop(Shop shop);

    int updateShop(Shop shop);
}
