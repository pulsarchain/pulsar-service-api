package com.bosha.common.api.dto.oss;

import lombok.Data;

import java.io.Serializable;


@Data
public class PolicyResponse implements Serializable {
    private String policy;
    private String expire;
    private String signature;
}
