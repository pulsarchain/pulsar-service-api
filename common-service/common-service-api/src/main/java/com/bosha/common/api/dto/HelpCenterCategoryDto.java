package com.bosha.common.api.dto;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: HelpCenterCategoryDto
 * @Author liqingping
 * @Date 2019-12-31 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HelpCenterCategoryDto {

    private String name;

    private Long id;

    private Date createTime;
}
