package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.TransactionType;
import org.katyshevtseva.invest.core.entity.Transaction;

import java.util.Date;
import java.util.List;

public class TransactionService {

    public static List<Transaction> getTransactions() {
        return Dao.getAllTransactions();
    }

    public static void save(Transaction existing, Long fromId, Long toId, String comment, TransactionType type,
                            Date date, Integer amount) {
        if (existing == null) {
            existing = new Transaction();
            existing.setValues(fromId, toId, comment, type, date, amount);
            TransactionImpactService.makeAddChanges(existing);
            Dao.saveNew(existing);
        } else {
            existing.setValues(fromId, toId, comment, type, date, amount);
            TransactionImpactService.makeAddChanges(existing);
            Dao.saveEdited(existing);
        }
    }

    public void delete(Transaction transaction) {
        TransactionImpactService.makeDeleteChanges(transaction);
        Dao.delete(transaction);
    }
}
