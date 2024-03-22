package org.katyshevtseva.invest.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public Location(String title) {
        this.title = title;
    }

    public Location() {
    }

    @Override
    public String toString() {
        return title;
    }
}
