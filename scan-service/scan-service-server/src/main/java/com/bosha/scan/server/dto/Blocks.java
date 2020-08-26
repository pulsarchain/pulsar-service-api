package com.bosha.scan.server.dto;

import java.io.Serializable;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: Blocks
 * @Author liqingping
 * @Date 2020-04-10 14:06
 */
@Data
@Document("blocks")
public class Blocks implements Serializable {

    private static final long serialVersionUID = -6544129454410045683L;

    private String id;

    private Long number;

    private String miner;

    private Long transactionCount;

    private Long timestamp;

    private String hash;
}
