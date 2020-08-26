package com.bosha.common.server.controller.manager;

import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.SystemContentManagerListDto;
import com.bosha.common.api.dto.SystemContentManagerQueryDto;
import com.bosha.common.api.entity.Dict;
import com.bosha.common.api.entity.SystemContent;
import com.bosha.common.api.service.DictService;
import com.bosha.common.api.service.SystemContentService;
import com.bosha.common.server.config.CommonServiceConfig;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.constants.CommonConstants;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.exception.BaseException;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: DictController
 * @Author liqingping
 * @Date 2019-12-14 17:52
 */
@RestController
@RequestMapping(CommonServiceConstants.WEB_PRIFEX + CommonConstants.Path.MANAGER + "/systemContent")
@Slf4j
@Api(tags = "系统内容管理")
public class SystemContentController extends BaseController {

    @Autowired
    private SystemContentService systemContentService;
    @Autowired
    private DictService dictService;
    @Autowired
    private CommonServiceConfig commonServiceConfig;

    @ApiOperation("发布")
    @PostMapping("/add")
    @RedissonDistributedLock(key = "")
    public Long add(@RequestBody @Validated SystemContent systemContent) {
        Dict dict = dictService.dict(systemContent.getDictId());
        if (commonServiceConfig.getInstructions().contains(dict.getValue())) {
            if (systemContentService.countByDictId(dict.getId()) > 0)
                throw new BaseException("使用说明下的该类型value[" + dict.getValue() + "]已存在，无法再次添加");
        }
        return systemContentService.add(systemContent);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @RedissonDistributedLock(key = "")
    public boolean update(@RequestBody SystemContent systemContent) {
        return systemContentService.update(systemContent);
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    PageInfo<SystemContentManagerListDto> list(@ModelAttribute @Validated SystemContentManagerQueryDto query) {
        return systemContentService.list(query);
    }

}
