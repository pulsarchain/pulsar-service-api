package com.bosha.star.api.dto.server;

import java.io.Serializable;
import java.util.List;


import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: ImGroupInfo
 * @Author liqingping
 * @Date 2020-05-06 17:20
 */
@Data
public class ImGroupInfo implements Serializable {

    private static final long serialVersionUID = -8703370045072676513L;

    private Long roomId;
    private Integer number;
    private List<String> address;
}
