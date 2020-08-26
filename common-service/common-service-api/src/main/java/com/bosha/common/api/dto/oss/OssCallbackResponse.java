package com.bosha.common.api.dto.oss;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
@Data
public class OssCallbackResponse implements Serializable {
    private String autorizationInput;
    private String pubKeyInputp;
    private String uri;
    private String queryString;
    private String ossCallbackBody;

    public OssCallbackResponse(String autorizationInput, String pubKeyInputp, String uri, String queryString, String ossCallbackBody) {
        this.autorizationInput = autorizationInput;
        this.pubKeyInputp = pubKeyInputp;
        this.uri = uri;
        this.queryString = queryString;
        this.ossCallbackBody = ossCallbackBody;
    }

    public OssCallbackResponse() {
    }
}
