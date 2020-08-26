package com.bosha.star.api.dto.web;

import java.util.List;


import com.bosha.commons.dto.Page;
import lombok.Builder;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveMiningListQueryDto
 * @Author liqingping
 * @Date 2020-03-25 12:15
 */
@Data
@Builder
public class LiveMiningListQueryDto   {
    private Page page;
    private List<Integer> list;
}
