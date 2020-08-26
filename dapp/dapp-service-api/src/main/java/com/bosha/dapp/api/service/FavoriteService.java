package com.bosha.dapp.api.service;

import com.bosha.dapp.api.entity.SparksFavorite;

public interface FavoriteService {

    Long add(SparksFavorite favorite);

    boolean cancel(SparksFavorite favorite);
}
