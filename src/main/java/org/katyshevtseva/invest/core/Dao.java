package org.katyshevtseva.invest.core;

import com.katyshevtseva.hibernate.CoreDao;
import org.katyshevtseva.invest.core.entity.Bond;
import org.katyshevtseva.invest.core.entity.Deposit;
import org.katyshevtseva.invest.core.entity.Location;
import org.katyshevtseva.invest.core.entity.Share;

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

    public static List<Bond> getAllBonds() {
        return coreDao.getAll(Bond.class.getSimpleName());
    }

    public static List<Deposit> getAllDeposits() {
        return coreDao.getAll(Deposit.class.getSimpleName());
    }

    public static List<Share> getAllShares() {
        return coreDao.getAll(Share.class.getSimpleName());
    }

}
