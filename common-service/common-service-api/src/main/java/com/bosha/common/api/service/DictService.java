package com.bosha.common.api.service;

import java.util.List;
import java.util.Map;


import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.DictRequestDto;
import com.bosha.common.api.entity.Dict;
import com.bosha.common.api.enums.DictTypeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(CommonServiceConstants.SERVER_NAME)
@RequestMapping(CommonServiceConstants.SERVER_PRIFEX + "/dict")
public interface DictService {

    @ApiOperation("添加")
    @PostMapping("/add")
    Long add(@RequestBody Dict dict);

    @ApiOperation("修改")
    @PostMapping("/update")
    boolean update(@RequestBody Dict dict);

    @ApiOperation("列表")
    @PostMapping("/list")
    PageInfo<Dict> list(@RequestBody DictRequestDto dto);

    @ApiOperation("项目分类列表")
    @PostMapping("/projectCategories")
    PageInfo<Dict> list(@RequestParam(value = "name",required = false) String name, @RequestParam("page") int page, @RequestParam("size") int size);

    @ApiOperation("根据类型获取")
    @GetMapping("/listType")
    List<Dict> listType(@RequestParam("type") DictTypeEnum dictType);

    @ApiOperation("根据类型获取")
    @GetMapping("/mapType")
    Map<String, Dict> mapType(@RequestParam("type") DictTypeEnum dictType);

    @ApiOperation("根据id查找")
    @GetMapping("/{id}")
    Dict dict(@PathVariable Long id);

    @ApiOperation("根据type查找")
    @GetMapping("/getByType")
    Dict dict(@RequestParam("type") DictTypeEnum dictTypeEnum);

    @ApiOperation("根据父级id查询子集")
    @GetMapping("/getByParentId")
    List<Dict> getByParentId(@RequestParam("parentId") Long parentId);

    @GetMapping("/getByTypeAndValue")
    Dict getByTypeAndValue(@RequestParam("typeEnum") DictTypeEnum typeEnum, @RequestParam("value") String value);
}
