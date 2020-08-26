package com.bosha.dapp.api.service;

import java.util.List;


import com.bosha.dapp.api.entity.SparksReceiveAccount;
import com.bosha.dapp.api.entity.SparksReceiverAddress;

public interface ReveiveService {

    Long addReceiveAccount(SparksReceiveAccount account);

    boolean updateReceiveAccount(SparksReceiveAccount account);

    List<SparksReceiveAccount> listReceiveAccount(String address);

    Long addReceiveAddress(SparksReceiverAddress address);

    boolean updateReceiveAddress(SparksReceiverAddress address);

    List<SparksReceiverAddress> listReceiveAddress(String address);
}
