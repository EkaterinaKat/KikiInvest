package org.katyshevtseva.invest.core.entity;

import com.katyshevtseva.date.DateUtils;
import lombok.Data;
import org.katyshevtseva.invest.core.Asset;
import org.katyshevtseva.invest.core.AssetType;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Deposit implements Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Float amount;

    @Temporal(TemporalType.DATE)
    private Date openDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private Float annualInterest;

    private boolean closed;

    public Deposit() {
    }

    public Deposit(String title, Float amount, Location location, Date openDate, Float annualInterest, boolean closed) {
        this.title = title;
        this.amount = amount;
        this.location = location;
        this.annualInterest = annualInterest;
        this.closed = closed;
        this.openDate = openDate;
    }

    public void setValues(String title, Float amount, Location location, Date openDate, Float annualInterest) {
        this.title = title;
        this.amount = amount;
        this.location = location;
        this.annualInterest = annualInterest;
        this.openDate = openDate;
    }

    @Override
    public AssetType getType() {
        return AssetType.DEPOSIT;
    }

    @Override
    public Float getAi() {
        return annualInterest;
    }

    @Override
    public String getOtherInfo() {
        return "amount=" + amount;
    }

    @Override
    public boolean isActive() {
        return !closed;
    }

    @Override
    public String toString() {
        return title + "\n" +
                "amount=" + amount + "\n" +
                "location=" + location + "\n" +
                getOpenDateString() +
                "annualInterest=" + annualInterest + "\n" +
                "closed=" + closed;
    }

    private String getOpenDateString() {
        return openDate == null ? "" :
                "openDate=" + DateUtils.READABLE_DATE_FORMAT.format(openDate) + "\n";
    }
}
