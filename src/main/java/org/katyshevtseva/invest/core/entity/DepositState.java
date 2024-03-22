package org.katyshevtseva.invest.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class DepositState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;

    @Temporal(TemporalType.DATE)
    private Date stateDate;

    @ManyToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;
}
