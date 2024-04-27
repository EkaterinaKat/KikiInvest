package org.katyshevtseva.invest.core;

import lombok.Getter;

@Getter
public enum AssetType {
    BOND("#CBB3FF", 3),
    SHARE("#FFE5B3", 2),
    DEPOSIT("#B3E6FF", 1);

    private final String color;
    private final int order;

    AssetType(String color, int order) {
        this.color = color;
        this.order = order;
    }
}
