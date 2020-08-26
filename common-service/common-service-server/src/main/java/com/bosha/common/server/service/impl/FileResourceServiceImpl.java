package com.bosha.common.server.service.impl;

import static com.bosha.common.api.constants.CommonServiceConstants.SEPARATOR;


import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.oss.OssCallbackResponse;
import com.bosha.common.api.dto.oss.OssSignResponse;
import com.bosha.common.api.entity.FileResource;
import com.bosha.common.api.service.FileResourceService;
import com.bosha.common.server.config.AliyunOSSWebConfig;
import com.bosha.common.server.mapper.FileResourceMapper;
import com.bosha.common.server.utils.RandomUtils;
import com.bosha.common.server.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * (FileResource)表服务实现类
 *
 * @author makejava
 * @since 2018-12-11 14:12:44
 */
@RestController
@RefreshScope
public class FileResourceServiceImpl implements FileResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileResourceServiceImpl.class);
    @Autowired
    private AliyunOSSWebConfig aliyunOSSWebConfig;
    @Value("${spring.profiles.active}")
    private String active;
    @Autowired
    @Qualifier("httpRestTemplate")
    private RestTemplate httpRestTemplate;
    @Autowired
    private FileResourceMapper fileResourceMapper;

    @Override
    public OssSignResponse findOssSign() {
        OssSignResponse ossSign = null;
        String accessId = aliyunOSSWebConfig.getAccessKeyId(); // 请填写您的AccessKeyId。
        String accessKey = aliyunOSSWebConfig.getAccessKeySecret(); // 请填写您的AccessKeySecret。
        String endpoint = aliyunOSSWebConfig.getEndpoint(); // 请填写您的 endpoint。
        String bucket = aliyunOSSWebConfig.getBucket(); // 请填写您的 bucketname 。
        String host = "http://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        String callbackUrl = aliyunOSSWebConfig.getCallbackUrl();
        try {
            OSSClient client = new OSSClient(endpoint, accessId, accessKey);
            int expireTime = 180;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY,
                    CommonServiceConstants.ORIGIN_PULSAR_DIR);

            String postPolicy = client.generatePostPolicy(expiration, policyConditions);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);


            ossSign = new OssSignResponse();
            ossSign.setAccessId(accessId);
            ossSign.setPolicy(encodedPolicy);
            ossSign.setSignature(postSignature);
            ossSign.setHost(host);
            ossSign.setExpire(String.valueOf(expireEndTime / 1000));
            String filename = SEPARATOR + RandomUtils.UUID();
            ossSign.setKey(CommonServiceConstants.ORIGIN_PULSAR_DIR + active + filename);


            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", callbackUrl);
            jasonCallback.put("callbackBody",
                    "bucket=${bucket}&object=${object}&etag=${etag}&size=${size}&mimeType=${mimeType}&imageInfo.height=${imageInfo.height}&imageInfo.width=${imageInfo.width}&imageInfo.format=${imageInfo.format}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            ossSign.setCallback(base64CallbackBody);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return ossSign;
    }

    @Override
    public boolean verifyOssCallback(@RequestBody OssCallbackResponse ossCallbackDto) {
        boolean ret ;
        byte[] authorization = BinaryUtil.fromBase64String(ossCallbackDto.getAutorizationInput());
        byte[] pubKey = BinaryUtil.fromBase64String(ossCallbackDto.getPubKeyInputp());
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            LOGGER.error("pub key address must be oss address");
        }
        String retString = httpRestTemplate.getForObject(pubKeyAddr, String.class);
        //LOGGER.info("回调连接：url:{}, 请求时间：{},retString:{}",pubKeyAddr, endTime - startTime,retString);
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        String decodeUri = null;
        try {
            decodeUri = java.net.URLDecoder.decode(ossCallbackDto.getUri(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        String authStr = decodeUri;
        if (ossCallbackDto.getQueryString() != null && !ossCallbackDto.getQueryString().equals("")) {
            authStr += "?" + ossCallbackDto.getQueryString();
        }
        authStr += "\n" + ossCallbackDto.getOssCallbackBody();
        ret =  doCheck(authStr, authorization, retString);
        FileResource fileResource = null;
        if (ret) {
            Map<String, String> params = UrlUtils.getParameters(UrlUtils.getURLDecoderString(ossCallbackDto.getOssCallbackBody()));
            fileResource = new FileResource();
            fileResource.setName(params.get("object"));
            if (!StringUtils.isEmpty(params.get("imageInfo.height"))) {
                fileResource.setHeight(Long.parseLong(params.get("imageInfo.height")));
            }
            if (!StringUtils.isEmpty(params.get("imageInfo.width"))) {
                fileResource.setWidth(Long.parseLong(params.get("imageInfo.width")));
            }
            fileResource.setFormat(params.get("imageInfo.format"));
            fileResource.setMimeType(params.get("mimeType"));
            fileResource.setSize(Long.parseLong(params.get("size")));
            fileResource.setUrl(aliyunOSSWebConfig.getBaseUrl() + fileResource.getName());
            fileResource.setCreateTime(new Date());
            fileResourceMapper.insertSelective(fileResource);
        }
        return ret;
    }

    private boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            boolean verify = signature.verify(sign);
            return verify;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}