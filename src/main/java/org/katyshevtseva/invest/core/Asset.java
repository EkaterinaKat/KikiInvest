package org.katyshevtseva.invest.core;

import org.katyshevtseva.invest.core.entity.Location;

public interface Asset {
    Location getLocation();

    AssetType getType();

    Float getAi();

    String getOtherInfo();

    boolean isActive();

    String getTitle();
}
