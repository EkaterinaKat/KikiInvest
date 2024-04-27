package org.katyshevtseva.invest.core.entity;

import com.katyshevtseva.general.GeneralUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.katyshevtseva.invest.core.AssetType;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(mappedBy = "to")
    private Purchase purchase;

    @OneToOne(mappedBy = "from")
    private Sale sale;

    @OneToMany(mappedBy = "from")
    private List<Payment> payments;

    private String comment;

    public void setValues(String title, Location location, String comment, AssetType type) {
        this.title = title;
        this.location = location;
        this.comment = comment;
        this.type = type;
    }

    public String getFullInfo() {
        StringBuilder result = new StringBuilder(title + "\n" +
                "type=" + type + "\n" +
                "location=" + location);

        if (!GeneralUtils.isEmpty(comment)) {
            result.append("\n\n").append(comment);
        }

        return result.toString();
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", location=" + location +
                ", comment='" + comment + '\'' +
                '}';
    }
}
