package org.katyshevtseva.invest.core.entity;

import lombok.Data;
import org.katyshevtseva.invest.core.Asset;
import org.katyshevtseva.invest.core.AssetType;

import javax.persistence.*;

import static org.katyshevtseva.invest.core.AssetType.BOND;

@Data
@Entity
public class Bond implements Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private Float purchasePrice;

    private boolean repaid;

    private Float annualInterest;

    public Bond(String title, Location location, Float purchasePrice, boolean repaid, Float annualInterest) {
        this.title = title;
        this.location = location;
        this.purchasePrice = purchasePrice;
        this.repaid = repaid;
        this.annualInterest = annualInterest;
    }

    public Bond() {
    }

    public void setValues(String title, Location location, Float purchasePrice, Float annualInterest) {
        this.title = title;
        this.location = location;
        this.purchasePrice = purchasePrice;
        this.annualInterest = annualInterest;
    }

    @Override
    public AssetType getType() {
        return BOND;
    }

    @Override
    public Float getAi() {
        return annualInterest;
    }

    @Override
    public String getOtherInfo() {
        return "purchasePrice=" + purchasePrice +
                ", repaid=" + repaid;
    }

    @Override
    public boolean isActive() {
        return !repaid;
    }

    @Override
    public String toString() {
        return title + "\n" +
                "location=" + location + "\n" +
                "purchasePrice=" + purchasePrice + "\n" +
                "repaid=" + repaid + "\n" +
                "annualInterest=" + annualInterest;
    }
}
