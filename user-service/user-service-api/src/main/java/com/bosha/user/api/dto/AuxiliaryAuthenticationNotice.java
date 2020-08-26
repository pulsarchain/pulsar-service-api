package com.bosha.user.api.dto;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AuxiliaryAuthenticationNotice
 * @Author liqingping
 * @Date 2020-02-10 10:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuxiliaryAuthenticationNotice implements Serializable {
    private static final long serialVersionUID = 6194107333824929230L;

    private String address;

    private String nickName;
}
