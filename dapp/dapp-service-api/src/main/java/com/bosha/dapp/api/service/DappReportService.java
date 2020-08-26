package com.bosha.dapp.api.service;


import java.util.List;


import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.DappReportAddDto;
import com.bosha.dapp.api.dto.DappReportDetailDto;
import com.bosha.dapp.api.dto.DappReportPublicListDto;
import com.bosha.dapp.api.dto.MyDappReportListDto;
import com.bosha.dapp.api.entity.DappReportCategory;
import com.bosha.dapp.api.entity.DappReportVote;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(DappServiceConstants.SERVER_NAME)//服务名
@RequestMapping(DappServiceConstants.SERVER_PRIFEX + "/report")//内部服务前缀
public interface DappReportService {

    @ApiOperation("举报分类")
    @GetMapping("/categories")
    List<DappReportCategory> categories();

    @ApiOperation("新增举报内容")
    @PostMapping("/add")
    Long add(@RequestBody DappReportAddDto dappReportAddDto);

    @ApiOperation("举报内容详情")
    @GetMapping("/detail")
    DappReportDetailDto detail(@RequestParam("id") Long id);

    @ApiOperation("我的举报记录")
    @GetMapping("/list")
    PageInfo<MyDappReportListDto> list(@ModelAttribute Page page);

    @ApiOperation("举报公示区列表")
    @GetMapping("/publicList")
    PageInfo<DappReportPublicListDto> publicList(@ModelAttribute Page page);

    @ApiOperation("公示详情")
    @GetMapping("/publicDetail")
    DappReportPublicListDto publicDetail(@RequestParam("id") Long id);

    @ApiOperation("投票")
    @PostMapping("/vote")
    void vote(@RequestBody DappReportVote dappReportVote);
}
