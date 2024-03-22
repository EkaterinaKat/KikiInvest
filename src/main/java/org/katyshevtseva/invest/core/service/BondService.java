package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Bond;
import org.katyshevtseva.invest.core.entity.Location;

import java.util.List;

public class BondService {

    public static List<Bond> getBonds() {
        return Dao.getAllBonds();
    }

    public static void save(Bond existing, String title, Location location, Float purchasePrice, Float annualInterest) {
        if (existing == null) {
            Dao.saveNew(new Bond(title, location, purchasePrice, false, annualInterest));
        } else {
            existing.setValues(title, location, purchasePrice, annualInterest);
            Dao.saveEdited(existing);
        }
    }
}
