package org.katyshevtseva.invest.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.katyshevtseva.invest.core.TransactionType;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromId;

    private Long toId;

    private String comment;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Temporal(TemporalType.DATE)
    @Column(name = "transaction_date")
    private Date date;

    public void setValues(Long fromId, Long toId, String comment, TransactionType type, Date date, Integer amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.comment = comment;
        this.type = type;
        this.date = date;
        this.amount = amount;
    }
}
