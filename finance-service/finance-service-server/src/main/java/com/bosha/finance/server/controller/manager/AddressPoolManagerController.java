package com.bosha.finance.server.controller.manager;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.response.AddressPoolDetailDto;
import com.bosha.finance.api.dto.response.AddressPoolListDto;
import com.bosha.finance.api.service.AddressPoolService;
import com.bosha.finance.server.mapper.AddressPoolMapper;
import com.bosha.finance.server.mapper.CoinMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "地址池管理")
@RestController
@Slf4j
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/manager/pool/")
public class AddressPoolManagerController extends BaseController {
    @Autowired
    private AddressPoolService addressPoolService;

    @GetMapping("/findAddressPool")
    @ApiOperation("查询地址池 （后台管理系统）")
    public List<AddressPoolListDto> findAddressPool() {
        List<AddressPoolListDto> addressPoolListDtos = addressPoolService.findAddressPool();
        return addressPoolListDtos;
    }

    @GetMapping("/findAddressPoolDetail")
    @ApiOperation("查询地址池详细信息 （后台管理系统）")
    public PageInfo<AddressPoolDetailDto> findAddressPoolDetail(Long id, Page page) {
        PageInfo<AddressPoolDetailDto> addressPoolListDtos = addressPoolService.findAddressPoolDetail(id, page.getPage(), page.getSize());
        return addressPoolListDtos;
    }
}
