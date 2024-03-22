package org.katyshevtseva.invest.core.entity;

import com.katyshevtseva.date.DateUtils;
import lombok.Data;
import org.katyshevtseva.invest.core.Asset;
import org.katyshevtseva.invest.core.AssetType;

import javax.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    private boolean repaid;

    private Float annualInterest;

    public Bond(String title, Location location, Float purchasePrice, Date purchaseDate, boolean repaid, Float annualInterest) {
        this.title = title;
        this.location = location;
        this.purchasePrice = purchasePrice;
        this.repaid = repaid;
        this.annualInterest = annualInterest;
        this.purchaseDate = purchaseDate;
    }

    public Bond() {
    }

    public void setValues(String title, Location location, Float purchasePrice, Float annualInterest, Date purchaseDate) {
        this.title = title;
        this.location = location;
        this.purchasePrice = purchasePrice;
        this.annualInterest = annualInterest;
        this.purchaseDate = purchaseDate;
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
        return "purchasePrice=" + purchasePrice;
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
                "purchaseDate=" + DateUtils.READABLE_DATE_FORMAT.format(purchaseDate) + "\n" +
                "repaid=" + repaid + "\n" +
                "annualInterest=" + annualInterest;
    }
}
