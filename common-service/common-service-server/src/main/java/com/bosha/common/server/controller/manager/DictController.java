package com.bosha.common.server.controller.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.DictRequestDto;
import com.bosha.common.api.dto.DictTypeEnumDto;
import com.bosha.common.api.dto.LanguageInfo;
import com.bosha.common.api.entity.Dict;
import com.bosha.common.api.enums.DictTypeEnum;
import com.bosha.common.api.service.DictService;
import com.bosha.commons.constants.CommonConstants;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.enums.LanguageEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: DictController
 * @Author liqingping
 * @Date 2019-12-14 17:52
 */
@RestController
@RequestMapping(CommonServiceConstants.WEB_PRIFEX + CommonConstants.Path.MANAGER + "/dict")
@Slf4j
@Api(tags = "字典配置/分类")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    @ApiOperation("根据id查找")
    @GetMapping("/{id}")
    Dict dict(@PathVariable Long id) {
        return dictService.dict(id);
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    Long add(@RequestBody @Validated Dict dict) {
        if (StringUtils.isBlank(dict.getValue()))
            dict.setValue("");
        return dictService.add(dict);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    boolean update(@RequestBody Dict dict) {
        return dictService.update(dict);
    }

    @ApiOperation("字典/分类 列表（分页）")
    @GetMapping("/list")
    PageInfo<Dict> list(@ModelAttribute @Validated DictRequestDto dto) {
        return dictService.list(dto);
    }

    @ApiOperation("字典/分类 列表（没有分页）")
    @GetMapping("/categorys")
    List<Dict> category(@ApiParam(value = "名称") @RequestParam(value = "name", required = false) String name,
                        @ApiParam(value = "类型：全部：all，行情：market，币种：coin，项目分类：project_category，文章：article，快讯：flash，默认为all")
                        @RequestParam(value = "type", required = false, defaultValue = "all") DictTypeEnum dictTypeEnum) {
        DictRequestDto dto = new DictRequestDto();
        dto.setPage(1);
        dto.setSize(9999);
        dto.setName(name);
        dto.setType(dictTypeEnum);
        PageInfo<Dict> list = dictService.list(dto);
        return list.getList();
    }

    @ApiOperation("字典表的类型")
    @GetMapping("/types")
    List<DictTypeEnumDto> types() {
        List<DictTypeEnumDto> list = new ArrayList<>();
        Arrays.stream(DictTypeEnum.values())
                .filter(dictTypeEnum -> dictTypeEnum != DictTypeEnum.all)
                .forEach(dictTypeEnum -> list.add(DictTypeEnumDto.builder().key(dictTypeEnum.name()).value(dictTypeEnum.desc).build()));
        return list;
    }

    @ApiOperation("语言类型")
    @GetMapping("/languages")
    List<LanguageInfo> languages() {
        List<LanguageInfo> list = new ArrayList<>();
        for (LanguageEnum value : LanguageEnum.values()) {
            LanguageInfo build = LanguageInfo.builder().key(value.name()).build();
            switch (value) {
                case zh_CN:
                    build.setDesc("中文");
                    break;
                case en_US:
                    build.setDesc("英文");
                    break;
            }
            list.add(build);
        }
        return list;
    }
}
