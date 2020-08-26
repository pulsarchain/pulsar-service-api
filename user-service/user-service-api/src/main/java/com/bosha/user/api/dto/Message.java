package com.bosha.user.api.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5054453136650792076L;
    /**
     * 消息id
     */
    private String msgId;
    /**
     * 消息发送者id
     */
    private String from;
    /**
     * 消息接收者id
     */
    private String to;
    /**
     * 消息类型 chat个人消息，groupchat，群消息
     */
    private String type;

    /**
     * 消息内容
     */
    private JSONObject body;

    private Long createdAt;

    private String receive;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result
                + ((msgId == null) ? 0 : msgId.hashCode());
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        if (body == null) {
            if (other.body != null)
                return false;
        } else if (!body.equals(other.body))
            return false;

        if (from == null) {
            if (other.from != null)
                return false;
        } else if (!from.equals(other.from))
            return false;
        if (msgId == null) {
            if (other.msgId != null)
                return false;
        } else if (!msgId.equals(other.msgId))
            return false;
        if (to == null) {
            if (other.to != null)
                return false;
        } else if (!to.equals(other.to))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
