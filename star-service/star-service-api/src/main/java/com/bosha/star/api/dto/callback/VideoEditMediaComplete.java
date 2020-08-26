package com.bosha.star.api.dto.callback;

import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: VideoEditMediaComplete
 * @Author liqingping
 * @Date 2020-04-03 10:27
 */
@Data
public class VideoEditMediaComplete {

    private String EventType;
    private EditMediaCompleteEvent EditMediaCompleteEvent;
}
