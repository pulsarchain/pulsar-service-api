package com.bosha.dapp.api.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappWitnessNoticeDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 15:38
 */
@Data
@Builder
public class DappWitnessNoticeDto {

    private String nickName;

    private Long dappId;
}
