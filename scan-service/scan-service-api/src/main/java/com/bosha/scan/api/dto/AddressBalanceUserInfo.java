package com.bosha.scan.api.dto;

import java.math.BigDecimal;


import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: AddressBalanceUserInfo
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-10 22:22
 */
@Data
public class AddressBalanceUserInfo {

    private String address;

    private String nickName = "";

    private boolean contract;

    private BigDecimal balance = BigDecimal.ZERO;
}
