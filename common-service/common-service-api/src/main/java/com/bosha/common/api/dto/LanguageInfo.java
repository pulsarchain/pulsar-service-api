package com.bosha.common.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: LanguageInfo
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-06-13 11:56
 */
@Data
@ApiModel("语言信息")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageInfo {

    private String desc;

    private String key;
}
