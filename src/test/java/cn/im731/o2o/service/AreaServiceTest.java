package cn.im731.o2o.service;

import cn.im731.o2o.BaseTest;
import cn.im731.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList() {
        List<Area> areas = areaService.getAreaList();
        for (Area area : areas) {
            System.out.println(area);
        }
    }
}
