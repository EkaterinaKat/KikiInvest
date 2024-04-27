package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Account;
import org.katyshevtseva.invest.core.entity.Location;

import java.util.List;

public class AccountService {

    public static List<Account> getAccounts() {
        return Dao.getAllAccounts();
    }

    public static void save(Account existing, String title, Location location) {
        if (existing == null) {
            Dao.saveNew(new Account(title, location, 0f));
        } else {
            existing.setTitle(title);
            existing.setLocation(location);
            Dao.saveEdited(existing);
        }
    }

    public static Account getUpdated(Account account) {
        return Dao.findAccountById(account.getId());
    }
}
