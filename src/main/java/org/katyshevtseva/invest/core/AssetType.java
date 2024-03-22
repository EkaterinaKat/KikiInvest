package org.katyshevtseva.invest.core;

public enum AssetType {
    BOND("#CBB3FF"), SHARE("#FFE5B3"), DEPOSIT("#B3E6FF");

    private final String color;

    AssetType(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
