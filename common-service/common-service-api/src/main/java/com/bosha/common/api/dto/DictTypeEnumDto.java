package com.bosha.common.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: DictTypeEnumDto
 * @Author liqingping
 * @Date 2019-12-31 21:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictTypeEnumDto {

    private String key;

    private String value;
}
