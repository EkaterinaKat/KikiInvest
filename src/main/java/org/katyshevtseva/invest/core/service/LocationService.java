package org.katyshevtseva.invest.core.service;

import org.katyshevtseva.invest.core.Dao;
import org.katyshevtseva.invest.core.entity.Location;

import java.util.List;

public class LocationService {

    public static List<Location> getLocations() {
        return Dao.getAllLocations();
    }

    public static void save(Location existing, String title) {
        if (existing == null) {
            Dao.saveNew(new Location(title));
        } else {
            existing.setTitle(title);
            Dao.saveEdited(existing);
        }
    }
}
