package com.bosha.common.api.dto.oss;

import java.io.Serializable;


import lombok.Data;

@Data
public class OssSignResponse implements Serializable {
    private static final long serialVersionUID = 9036599328093523166L;

    private String accessId;
    private String policy;
    private String signature;
    private String host;
    private String callback;
    private String key;
    private String callbackBody;
    private String callbackBodyType;
    private String expire;

}
