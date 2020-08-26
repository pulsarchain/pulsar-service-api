package com.bosha.finance.api.dto.response;

import com.bosha.commons.enums.LanguageEnum;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.finance.api.enums.FinanceServiceEnTypeEnum;
import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("收益明细列表")
public class MiningDetailListDto {
    private Long id;
    @ApiModelProperty("类型")
    private Integer serviceType;
    @ApiModelProperty("类型名字")
    private String serviceTypeName;
    @ApiModelProperty("合约地址")
    private String contractAddress;
    @ApiModelProperty("hash")
    private String hash;
    @ApiModelProperty("时间")
    private Date createTime;
    @ApiModelProperty("收益")
    private BigDecimal money;
    @ApiModelProperty("状态:0 处理中，1 成功，2 失败，3 其他")
    private Integer status;


    public String getServiceTypeName() {
        if (!StringUtils.isEmpty(serviceType)) {
            LanguageEnum language = RequestContextUtils.getRequestInfo().getLanguage();
            if (language == LanguageEnum.en_US){
                return FinanceServiceEnTypeEnum.getEnum(serviceType).getRemark();
            }else {
                return FinanceServiceTypeEnum.getEnum(serviceType).getRemark();
            }
        }
        return serviceTypeName;
    }
}
