package cn.im731.o2o.service.impl;

import cn.im731.o2o.dao.AreaDao;
import cn.im731.o2o.entity.Area;
import cn.im731.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
