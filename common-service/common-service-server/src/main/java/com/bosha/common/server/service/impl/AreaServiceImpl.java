package com.bosha.common.server.service.impl;

import java.util.List;


import com.bosha.common.api.entity.Area;
import com.bosha.common.api.service.AreaService;
import com.bosha.common.server.mapper.AreaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AreaServiceImpl
 * @Author liqingping
 * @Date 2019-04-13 12:27
 */
@Slf4j
@RestController
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;


    @Override
    public List<Area> listAllProvince() {
        return areaMapper.listAllProvince();
    }

    @Override
    public List<Area> listAllCity(@RequestParam(value = "provinceId",required = false,defaultValue = "") Integer provinceId) {
        return areaMapper.listAllCity(provinceId);
    }

    @Override
    public List<Area> listAllArea(@RequestParam(value = "cityId",required = false,defaultValue = "") Integer cityId) {
        return areaMapper.listAllArea(cityId);
    }

    @Override
    public Area getById(@RequestParam("id") Integer id) {
        return areaMapper.selectByPrimaryKey(id);
    }
}
