package org.katyshevtseva.invest.core.entity;

import com.katyshevtseva.date.DateUtils;
import lombok.Data;
import org.katyshevtseva.invest.core.Asset;
import org.katyshevtseva.invest.core.AssetType;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Share implements Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private Float purchasePrice;

    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    private boolean sold;

    @OneToMany(mappedBy = "share")
    private Set<ShareState> states;

    public Share() {
    }

    public Share(String title, Location location, Float purchasePrice, Date purchaseDate, boolean sold) {
        this.title = title;
        this.location = location;
        this.purchasePrice = purchasePrice;
        this.sold = sold;
        this.purchaseDate = purchaseDate;
    }

    public void setValues(String title, Location location, Float purchasePrice, Date purchaseDate) {
        this.title = title;
        this.location = location;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
    }

    @Override
    public AssetType getType() {
        return AssetType.SHARE;
    }

    @Override
    public Float getAi() {
        return 0f;
    }

    @Override
    public String getOtherInfo() {
        return "purchasePrice=" + purchasePrice;
    }

    @Override
    public boolean isActive() {
        return !sold;
    }

    @Override
    public String toString() {
        return title + "\n" +
                "location=" + location + "\n" +
                "purchasePrice=" + purchasePrice + "\n" +
                "purchaseDate=" + DateUtils.READABLE_DATE_FORMAT.format(purchaseDate) + "\n" +
                "sold=" + sold + "\n" +
                "states=" + states;
    }
}

