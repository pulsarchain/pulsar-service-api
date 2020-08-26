package com.bosha.star.server.mapper;

import java.util.List;


import com.bosha.star.api.dto.web.GiftAssetDto;
import com.bosha.star.api.entity.LiveMiningGiftAsset;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningGiftAssetMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LiveMiningGiftAsset record);

    LiveMiningGiftAsset selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMiningGiftAsset record);

    int batchInsert(@Param("list") List<LiveMiningGiftAsset> list);

    int insertOrUpdate(LiveMiningGiftAsset record);

    int insertOrUpdateSelective(LiveMiningGiftAsset record);

    List<LiveMiningGiftAsset> asset(@Param("address") String address);

    List<GiftAssetDto> assetDto(@Param("address") String address);

    LiveMiningGiftAsset getByAddressAndGiftId(@Param("address") String address, @Param("giftId") Long giftId);

    int updateGiftNum(@Param("id") Long id, @Param("num") Integer num, @Param("oldNum") Integer oldNum);
}