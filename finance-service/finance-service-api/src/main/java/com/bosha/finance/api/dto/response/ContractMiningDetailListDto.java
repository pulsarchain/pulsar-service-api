package com.bosha.finance.api.dto.response;

import com.bosha.commons.enums.LanguageEnum;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.finance.api.enums.FinanceServiceEnTypeEnum;
import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("收益明细列表")
public class ContractMiningDetailListDto {
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
    private BigDecimal amount;
    @ApiModelProperty("备注信息")
    private String remark;


    public String getServiceTypeName() {
        if (!StringUtils.isEmpty(serviceType)) {
            LanguageEnum language = RequestContextUtils.getRequestInfo().getLanguage();
            if (language == LanguageEnum.en_US) {
                return FinanceServiceEnTypeEnum.getEnum(serviceType).getRemark();
            } else {
                return FinanceServiceTypeEnum.getEnum(serviceType).getRemark();
            }
        }
        return serviceTypeName;
    }
}
