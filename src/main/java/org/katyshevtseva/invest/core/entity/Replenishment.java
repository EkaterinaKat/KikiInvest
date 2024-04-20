package org.katyshevtseva.invest.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.katyshevtseva.invest.core.Operation;
import org.katyshevtseva.invest.core.OperationType;

import javax.persistence.*;
import java.util.Date;

import static org.katyshevtseva.invest.core.OperationType.REPLENISHMENT;

@Data
@Entity
@NoArgsConstructor
public class Replenishment implements Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date date;

    private Float amount;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private Account to;

    public void setValues(Date date, Float amount, String comment, Account to) {
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.to = to;
    }

    @Override
    public String getFromString() {
        return "-";
    }

    @Override
    public String getToString() {
        return to.getTitle();
    }

    @Override
    public OperationType getType() {
        return REPLENISHMENT;
    }
}
