package com.bosha.finance.api.dto.request;

import com.bosha.finance.api.entity.ContractMiningDetail;
import com.bosha.finance.api.enums.ContractMiningStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("批量修改实体")
public class BatchContractMiningDetailDto {
    @ApiModelProperty("状态")
    private ContractMiningStatusEnum contractMiningStatusEnum;
    @ApiModelProperty("数据")
    private List<ContractMiningDetail> contractMiningDetails;

}
