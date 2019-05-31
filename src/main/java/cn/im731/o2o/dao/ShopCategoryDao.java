package cn.im731.o2o.dao;

import cn.im731.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    /**
     * 查parent_id为shopCategoryCondition.parent的所有类目
     * 当shopCategoryCondition不为空时，查出所有parent_id不为空的类目（因为添加店铺时的类目必须是二级类目，不允许直接添加到一级类目下）
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
