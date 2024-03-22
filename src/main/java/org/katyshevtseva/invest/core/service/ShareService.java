package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Location;
import org.katyshevtseva.invest.core.entity.Share;

import java.util.Date;
import java.util.List;

public class ShareService {

    public static List<Share> getShares() {
        return Dao.getAllShares();
    }

    public static void save(Share existing, String title, Location location, Float purchasePrice, Date purchaseDate) {
        if (existing == null) {
            Dao.saveNew(new Share(title, location, purchasePrice, purchaseDate, false));
        } else {
            existing.setValues(title, location, purchasePrice, purchaseDate);
            Dao.saveEdited(existing);
        }
    }

    public static void close(Share share) {
        share.setSold(!share.isSold());
        Dao.saveEdited(share);
    }
}
