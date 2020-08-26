package com.bosha.user.api.dto;

import java.util.Date;


import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: QueryCreditScoreRangeDto
 * @Author liqingping
 * @Date 2020-03-05 18:29
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryCreditScoreRangeDto extends Page {
    private int min;
    private int max;
    private Date time;
}
