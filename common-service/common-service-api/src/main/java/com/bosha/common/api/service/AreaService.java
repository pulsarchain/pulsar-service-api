package com.bosha.common.api.service;


import java.util.List;


import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.entity.Area;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(CommonServiceConstants.SERVER_NAME)//服务名
@RequestMapping(CommonServiceConstants.SERVER_PRIFEX + "/area")//内部服务前缀
@Api(tags = " 公共服务 地区 server层接口")
public interface AreaService {

    @ApiOperation("获取所有省份")
    @GetMapping("/provinces")
    List<Area> listAllProvince();

    @ApiOperation("根据省份id获取所有城市")
    @GetMapping("/citys")
    List<Area> listAllCity(@RequestParam(value = "provinceId", required = false, defaultValue = "") Integer provinceId);

    @ApiOperation("根据城市id获取所有区域")
    @GetMapping("/areas")
    List<Area> listAllArea(@RequestParam(value = "cityId", required = false, defaultValue = "") Integer cityId);

    @ApiOperation("根据id获取")
    @GetMapping("/id")
    Area getById(@RequestParam("id") Integer id);
}
