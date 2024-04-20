package org.katyshevtseva.invest.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.katyshevtseva.invest.core.service.Operation;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Withdrawal implements Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date date;

    private Float amount;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private Account from;

    public void setValues(Date date, Float amount, String comment, Account from) {
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.from = from;
    }

    @Override
    public String getFromString() {
        return from.getTitle();
    }

    @Override
    public String getToString() {
        return "-";
    }
}
