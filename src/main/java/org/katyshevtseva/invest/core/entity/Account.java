package org.katyshevtseva.invest.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private Float amount;

    public Account(String title, Location location, Float amount) {
        this.title = title;
        this.location = location;
        this.amount = amount;
    }
}
