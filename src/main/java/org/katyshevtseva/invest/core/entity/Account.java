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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getFullInfo() {
        return String.format("%s (%s)\n * %.2f * ", title, location.getTitle(), amount);
    }

    @Override
    public String toString() {
        return title;
    }
}
