package com.bosha.dapp.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.PddSearchResult;
import com.bosha.dapp.api.dto.PddUrlGenerate;
import com.bosha.dapp.api.dto.TaoBaoSearchResult;
import com.bosha.dapp.api.dto.WishDetailDto;
import com.bosha.dapp.api.dto.WishListDto;
import com.bosha.dapp.api.entity.SparksWish;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.service.WishService;
import com.bosha.dapp.server.service.impl.PddService;
import com.bosha.dapp.server.service.impl.TaoBaoService;
import com.github.pagehelper.PageInfo;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsSearchResponse;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WishController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-31 11:03
 */
@RestController
@Slf4j
@Api(tags = "心愿清单")
@RequestMapping(DappServiceConstants.WEB_PRIFEX + "/wish")
public class WishController extends BaseController {

    @Autowired
    private WishService wishService;
    @Autowired
    private TaoBaoService taoBaoService;
    @Autowired
    private PddService pddService;

    @ApiOperation("添加")
    @PostMapping("/add")
    Long add(@RequestBody @Validated SparksWish wish) {
        if (StringUtils.isAllBlank(wish.getUrl(), wish.getSchemaUrl()))
            throw new BaseException(DappErrorMsgEnum.URL_AND_SCHEMA_URL_CAN_NOT_BE_NULL);
        return wishService.add(wish);
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    PageInfo<WishListDto> list(Page page) {
        return wishService.list(page);
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    WishDetailDto detail(@RequestParam("id") Long id) {
        return wishService.detail(id);
    }

    @ApiOperation("我创建的心愿清单")
    @GetMapping("/my")
    PageInfo<WishListDto> my(Page page) {
        return wishService.my(getAddress(), page);
    }

    @ApiOperation("搜索淘宝的商品")
    @GetMapping("/taobao/search")
    TaoBaoSearchResult<TbkDgMaterialOptionalResponse.MapData> searchTaoBao(
            @ApiParam(value = "关键词") @RequestParam("q") String q, @RequestParam("page") Long page, @RequestParam("size") Long size) {
        return taoBaoService.search(q, page, size, 1);
    }

    @ApiOperation("搜索拼多多的商品")
    @GetMapping("/pdd/search")
    PddSearchResult<PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem> searchPdd(
            @ApiParam(value = "关键词") @RequestParam("q") String q, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return pddService.search(q, page, size);
    }

    @ApiOperation("获取拼多多的推广链接")
    @GetMapping("/pdd/generateUrl")
    PddUrlGenerate generateUrl(@ApiParam(value = "拼多多商品的id") @RequestParam("goodsId") Long goodsId,
                               @ApiParam(value = "拼多多商品搜索返回的search_id") @RequestParam("searchId") String searchId) {
        return pddService.generateUrl(goodsId, searchId);
    }
}
