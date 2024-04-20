package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Account;
import org.katyshevtseva.invest.core.entity.Replenishment;

import java.util.Date;

public class ReplenishmentService {

    public static void save(Replenishment existing, Date date, Float amount, String comment, Account to) {
        if (existing == null) {
            existing = new Replenishment();
            existing.setValues(date, amount, comment, to);
            Dao.saveNew(existing);
        } else {
            if (!existing.getTo().equals(to)) {
                throw new RuntimeException();
            }
            existing.setValues(date, amount, comment, to);
            Dao.saveEdited(existing);
        }
        AccountCalculationService.recalculate(to);
    }
}
