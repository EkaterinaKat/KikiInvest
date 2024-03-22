package org.katyshevtseva.invest.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class ShareState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price;

    @Temporal(TemporalType.DATE)
    private Date stateDate;

    @ManyToOne
    @JoinColumn(name = "share_id")
    private Share share;
}
