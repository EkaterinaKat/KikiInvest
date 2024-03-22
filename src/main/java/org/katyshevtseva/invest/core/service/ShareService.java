package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Location;
import org.katyshevtseva.invest.core.entity.Share;

import java.util.List;

public class ShareService {

    public static List<Share> getShares() {
        return Dao.getAllShares();
    }

    public static void save(Share existing, String title, Location location, Float purchasePrice) {
        if (existing == null) {
            Dao.saveNew(new Share(title, location, purchasePrice, false));
        } else {
            existing.setValues(title, location, purchasePrice);
            Dao.saveEdited(existing);
        }
    }
}
