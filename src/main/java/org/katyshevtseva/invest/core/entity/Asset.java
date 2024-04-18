package org.katyshevtseva.invest.core.entity;

import com.katyshevtseva.general.GeneralUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.katyshevtseva.invest.core.AssetType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Asset {
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

    private String comment;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    @OneToMany(mappedBy = "from", fetch = FetchType.EAGER)
    private List<Payment> payments;

    public void setValues(String title, Location location, Float purchasePrice,
                          Date purchaseDate, String comment, AssetType type) {
        this.title = title;
        this.location = location;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.comment = comment;
        this.type = type;
    }

    public String getFullInfo() {
        StringBuilder result = new StringBuilder(title + "\n" +
                "type=" + type + "\n" +
                "location=" + location + "\n" +
                "purchasePrice=" + purchasePrice + "\n" +
                "purchaseDate=" + purchaseDate + "\n" +
                "sold=" + sold + "\n");

        if (!GeneralUtils.isEmpty(comment)) {
            result.append("comment=").append(comment).append("\n");
        }

        if (!GeneralUtils.isEmpty(payments)) {
            result.append("payments=").append("\n");
            for (Payment payment : payments) {
                result.append(payment.getInfoForAsset()).append("\n");
            }
        }

        return result.toString();
    }
}
