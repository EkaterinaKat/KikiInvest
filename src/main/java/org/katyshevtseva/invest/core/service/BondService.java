package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Bond;
import org.katyshevtseva.invest.core.entity.Location;

import java.util.Date;
import java.util.List;

public class BondService {

    public static List<Bond> getBonds() {
        return Dao.getAllBonds();
    }

    public static void save(Bond existing, String title, Location location, Float purchasePrice,
                            Float annualInterest, Date purchaseDate) {
        if (existing == null) {
            Dao.saveNew(new Bond(title, location, purchasePrice, purchaseDate, false, annualInterest));
        } else {
            existing.setValues(title, location, purchasePrice, annualInterest, purchaseDate);
            Dao.saveEdited(existing);
        }
    }

    public static void close(Bond bond) {
        bond.setRepaid(!bond.isRepaid());
        Dao.saveEdited(bond);
    }
}
