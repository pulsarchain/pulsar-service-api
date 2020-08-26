package com.bosha.dapp.api.service;

import java.util.List;


import com.bosha.dapp.api.entity.SparksWitness;

public interface WitnessService {

    boolean update(Long id, String witnessAddress, String hash, String story);

    Long add(SparksWitness witness);

    void insertBatch(List<SparksWitness> witnesses);

    List<SparksWitness> list(Long relatedId);
}
