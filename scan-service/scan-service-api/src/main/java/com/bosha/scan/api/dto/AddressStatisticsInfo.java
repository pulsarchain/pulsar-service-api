package com.bosha.scan.api.dto;

import java.io.Serializable;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: AddressMonitorInfo
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-15 21:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressStatisticsInfo implements Serializable {
    private static final long serialVersionUID = 2237361383516073995L;

    private String address;

    private String balance;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String nickName;

    private Info in;

    private Info out;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static final class Info {
        private String total;
        private Integer count;
        private List<AddressTransaction> list;
    }
}
