package com.project.webapp.store.entity;

import com.project.webapp.film.entity.Film;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "inventory",
        indexes = {
                @Index(name = "idx_fk_film_id", columnList = "film_id"),
                @Index(name = "idx_store_id_film_id", columnList = "store_id, film_id")
})
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false,
            columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer inventoryId;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_film"))
    private Film film;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_store"))
    private Store store;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date lastUpdate;
}