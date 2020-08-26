package com.bosha.scan.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: AddressTransaction
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-16 20:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AddressTransaction implements Serializable {

    private static final long serialVersionUID = 365198369492661036L;

    private String address;

    private String nickName;

    private String balance;

    @JsonIgnore
    private BigDecimal balanceNumber;

    private Integer count;

    private String total;

    private Date firstTime;

    private Date lastTime;
}
