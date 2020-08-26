package com.bosha.star.api.dto.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ImGroupMember
 * @Author liqingping
 * @Date 2020-04-02 16:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImGroupMember {

    private String Member_Account;
}
