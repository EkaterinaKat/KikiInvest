package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.AssetType;
import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.*;

import java.util.Date;
import java.util.List;

public class AssetService {

    public static List<Asset> getAssets() {
        return Dao.getAllAssets();
    }

    public static void saveNew(String title, Location location, Float purchasePrice, Date purchaseDate,
                               String comment, AssetType type, Account purchaseAccount, String purchaseComment) {
        Asset asset = new Asset();
        asset.setValues(title, location, comment, type);
        Dao.saveNew(asset);

        Purchase purchase = new Purchase(purchaseDate, purchasePrice, purchaseComment, purchaseAccount, asset);
        Dao.saveNew(purchase);

        AccountCalculationService.recalculate(purchaseAccount);
    }

    public static void edit(Asset asset, String title, Location location, String comment) {
        asset.setTitle(title);
        asset.setLocation(location);
        asset.setComment(comment);
        Dao.saveEdited(asset);
    }

    public static void sale(Asset asset, Account account, Float salePrise, Date saleDate, String comment) {
        if (isSold(asset)) {
            throw new RuntimeException();
        }

        Dao.saveNew(new Sale(saleDate, salePrise, comment, asset, account));
        AccountCalculationService.recalculate(account);
    }

    public static boolean isSold(Asset asset) {
        return !Dao.findSalesByAsset(asset).isEmpty();
    }

    public static void payment(Asset asset, Account account, Float amount, Date saleDate, String comment) {
        if (asset.getType() != AssetType.BOND) {
            throw new RuntimeException();
        }

        Dao.saveNew(new Payment(comment, saleDate, amount, asset, account));
        AccountCalculationService.recalculate(account);
    }

    public static Asset getUpdated(Asset asset) {
        return Dao.findAssetById(asset.getId());
    }
}
