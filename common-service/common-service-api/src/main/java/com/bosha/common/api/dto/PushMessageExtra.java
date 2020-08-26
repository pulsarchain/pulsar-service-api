package com.bosha.common.api.dto;

import java.io.Serializable;


import com.bosha.common.api.enums.PushMessageTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: PushMessageExtParameter
 * @Author liqingping
 * @Date 2020-02-19 15:55
 */

@Data
@ApiModel("推送消息参数")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushMessageExtra implements Serializable {
    private static final long serialVersionUID = 6474150442665315337L;

    private PushMessageTypeEnum type;

    private String subType;

    private Object extras;
}
