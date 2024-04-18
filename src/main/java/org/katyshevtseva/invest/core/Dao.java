package org.katyshevtseva.invest.core;

import com.katyshevtseva.hibernate.CoreDao;
import org.hibernate.criterion.Restrictions;
import org.katyshevtseva.invest.core.entity.*;

import java.util.List;

public class Dao {
    private static final CoreDao coreDao = new CoreDao();

    public static <T> void saveNew(T t) {
        coreDao.saveNew(t);
    }

    public static <T> void saveEdited(T t) {
        coreDao.update(t);
    }

    public static <T> void delete(T t) {
        coreDao.delete(t);
    }

    public static List<Location> getAllLocations() {
        return coreDao.getAll(Location.class.getSimpleName());
    }

    public static List<Account> getAllAccounts() {
        return coreDao.getAll(Account.class.getSimpleName());
    }

    public static List<Asset> getAllAssets() {
        return coreDao.getAll(Asset.class.getSimpleName());
    }

    public static List<Payment> findPaymentsByAccount(Account account) {
        return coreDao.find(Payment.class, Restrictions.eq("to", account));
    }

    public static List<Replenishment> findReplenishmentsByAccount(Account account) {
        return coreDao.find(Replenishment.class, Restrictions.eq("to", account));
    }

    public static List<Sale> findSalesByAccount(Account account) {
        return coreDao.find(Sale.class, Restrictions.eq("to", account));
    }

    public static List<Withdrawal> findWithdrawalsByAccount(Account account) {
        return coreDao.find(Withdrawal.class, Restrictions.eq("from", account));
    }

    public static List<Purchase> findPurchasesByAccount(Account account) {
        return coreDao.find(Purchase.class, Restrictions.eq("from", account));
    }
}
