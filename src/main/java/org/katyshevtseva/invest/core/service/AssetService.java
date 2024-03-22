package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Asset;
import org.katyshevtseva.invest.core.Dao;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssetService {

    public static List<Asset> getAssets() {
        return Stream.concat(
                        Dao.getAllShares().stream(),
                        Stream.concat(
                                Dao.getAllDeposits().stream(),
                                Dao.getAllBonds().stream()))
                .collect(Collectors.toList());
    }
}
