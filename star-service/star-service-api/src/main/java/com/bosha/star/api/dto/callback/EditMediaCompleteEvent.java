package com.bosha.star.api.dto.callback;

import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: EditMediaCompleteEvent
 * @Author liqingping
 * @Date 2020-04-03 10:27
 */
@Data
public class EditMediaCompleteEvent {

    private String TaskId;
    private String Status;
    private Integer ErrCode;
    private String Message;
    private Output Output;

    @Data
    public static final class Output{
        private String FileType;
        private String FileId;
        private String FileUrl;
    }
}
