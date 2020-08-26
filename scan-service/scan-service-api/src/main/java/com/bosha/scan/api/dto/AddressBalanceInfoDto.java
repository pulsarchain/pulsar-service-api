package com.bosha.scan.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AddressBalanceInfoDto
 * @Author liqingping
 * @Date 2020-04-10 17:57
 */
@Data
@ApiModel("")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressBalanceInfoDto implements Serializable {
    private static final long serialVersionUID = 8901766798578338069L;

    @ApiModelProperty("总产出")
    private BigDecimal totalOutput;

    @ApiModelProperty("总的地址数")
    private Integer totalAccount;

    @ApiModelProperty("占比")
    private List<AddressRateDto> rateInfoList;

}
