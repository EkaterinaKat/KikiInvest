package org.katyshevtseva.invest.core.entity;

import lombok.Data;
import org.katyshevtseva.invest.core.Asset;
import org.katyshevtseva.invest.core.AssetType;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Deposit implements Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Float amount;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "deposit")
    private Set<DepositState> states;

    private Float annualInterest;

    private boolean closed;

    public Deposit() {
    }

    public Deposit(String title, Float amount, Location location, Float annualInterest, boolean closed) {
        this.title = title;
        this.amount = amount;
        this.location = location;
        this.annualInterest = annualInterest;
        this.closed = closed;
    }

    public void setValues(String title, Float amount, Location location, Float annualInterest) {
        this.title = title;
        this.amount = amount;
        this.location = location;
        this.annualInterest = annualInterest;
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
        return "amount=" + amount +
                ", closed=" + closed;
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
                "states=" + states + "\n" +
                "annualInterest=" + annualInterest + "\n" +
                "closed=" + closed;
    }
}
