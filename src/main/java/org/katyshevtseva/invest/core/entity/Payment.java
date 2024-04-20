package org.katyshevtseva.invest.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.katyshevtseva.invest.core.Operation;
import org.katyshevtseva.invest.core.OperationType;

import javax.persistence.*;
import java.util.Date;

import static org.katyshevtseva.invest.core.OperationType.PAYMENT;

@Data
@Entity
@NoArgsConstructor
public class Payment implements Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date date;

    private Float amount;

    private Boolean finalPayment;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private Asset from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private Account to;

    @Override
    public String getFromString() {
        return from.getTitle();
    }

    @Override
    public String getToString() {
        return to.getTitle();
    }

    public String getInfoForAsset() {
        return "date=" + date +
                ", amount=" + amount +
                ", finalPayment=" + finalPayment +
                ", to=" + to +
                ", comment=" + comment;
    }

    @Override
    public OperationType getType() {
        return PAYMENT;
    }
}