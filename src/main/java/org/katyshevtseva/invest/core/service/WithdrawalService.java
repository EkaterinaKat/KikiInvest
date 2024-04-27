package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Account;
import org.katyshevtseva.invest.core.entity.Withdrawal;

import java.util.Date;

public class WithdrawalService {

    public static void save(Withdrawal existing, Date date, Float amount, String comment, Account from) {
        if (existing == null) {
            existing = new Withdrawal();
            existing.setValues(date, amount, comment, from);
            Dao.saveNew(existing);
        } else {
            if (!existing.getFrom().equals(from)) {
                throw new RuntimeException();
            }
            existing.setValues(date, amount, comment, from);
            Dao.saveEdited(existing);
        }
        AccountCalculationService.updateAccountAmount(from);
    }
}
