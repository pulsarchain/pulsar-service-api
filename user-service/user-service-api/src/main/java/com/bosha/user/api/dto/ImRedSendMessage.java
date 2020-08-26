package com.bosha.user.api.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ImRedSendMessage {
    private String address;
    private BigDecimal money;
}
