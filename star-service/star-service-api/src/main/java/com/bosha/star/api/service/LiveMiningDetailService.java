package com.bosha.star.api.service;

import com.bosha.star.api.dto.callback.LivePushAndDisconnectCallbackDto;
import com.bosha.star.api.dto.callback.LiveRecordCallbackDto;
import com.bosha.star.api.dto.callback.VideoEditMediaComplete;
import com.bosha.star.api.dto.web.LiveRoomInfoDto;
import com.bosha.star.api.dto.web.MiningDto;

public interface LiveMiningDetailService {

    void mining(MiningDto mining);

    LiveRoomInfoDto liveRoomInfo(Long id, boolean push);

    void startPush(LivePushAndDisconnectCallbackDto callback);

    void disconnect(LivePushAndDisconnectCallbackDto callback);

    void record(LiveRecordCallbackDto callback);

    void editMediaComplete( VideoEditMediaComplete complete);
}
