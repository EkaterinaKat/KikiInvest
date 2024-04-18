package org.katyshevtseva.invest;

import org.katyshevtseva.invest.core.AssetType;
import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Asset;

public class Test {

    public static void main(String[] args) {
        Asset asset = new Asset();
        asset.setSold(false);
        asset.setValues("title", null, 888f, null, "comment", AssetType.BOND);
        Dao.saveNew(asset);
        System.out.println(asset);
    }
}
