package cn.im731.o2o.dao;

import cn.im731.o2o.BaseTest;
import cn.im731.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea() {
        List<Area> areaList = areaDao.queryArea();
        for (Area area : areaList) {
            System.out.println(area);
        }
    }
}
