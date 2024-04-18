package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.AssetType;
import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Account;
import org.katyshevtseva.invest.core.entity.Asset;
import org.katyshevtseva.invest.core.entity.Location;
import org.katyshevtseva.invest.core.entity.Purchase;

import java.util.Date;
import java.util.List;

public class AssetService {

    public static List<Asset> getAssets() {
        return Dao.getAllAssets();
    }

    public static void saveNew(String title, Location location, Float purchasePrice, Date purchaseDate,
                               String comment, AssetType type, Account purchaseAccount, String purchaseComment) {
        Asset asset = new Asset();
        asset.setSold(false);
        asset.setValues(title, location, purchasePrice, purchaseDate, comment, type);
        Dao.saveNew(asset);

        Purchase purchase = new Purchase(purchaseDate, purchasePrice, purchaseComment, purchaseAccount, asset);
        Dao.saveNew(purchase);

        AccountCalculationService.recalculate(purchaseAccount);
    }
}
