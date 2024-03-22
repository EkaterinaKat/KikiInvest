package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Deposit;
import org.katyshevtseva.invest.core.entity.Location;

import java.util.List;

public class DepositService {

    public static List<Deposit> getDeposits() {
        return Dao.getAllDeposits();
    }

    public static void save(Deposit existing, String title, Float amount, Location location, Float annualInterest) {
        if (existing == null) {
            Dao.saveNew(new Deposit(title, amount, location, annualInterest, false));
        } else {
            existing.setValues(title, amount, location, annualInterest);
            Dao.saveEdited(existing);
        }
    }

    public static void close(Deposit deposit) {
        deposit.setClosed(!deposit.isClosed());
        Dao.saveEdited(deposit);
    }
}
