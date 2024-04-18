package org.katyshevtseva.invest.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.katyshevtseva.invest.core.service.Operation;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Purchase implements Operation {
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

    @ManyToOne
    @JoinColumn(name = "to_id")
    private Asset to;

    public Purchase(Date date, Float amount, String comment, Account from, Asset to) {
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.from = from;
        this.to = to;
    }

    @Override
    public String getFromString() {
        return from.getTitle();
    }

    @Override
    public String getToString() {
        return to.getTitle();
    }
}
