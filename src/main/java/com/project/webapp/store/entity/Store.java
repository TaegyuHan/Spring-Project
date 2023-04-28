package com.project.webapp.store.entity;

import com.project.webapp.area.entity.Address;
import com.project.webapp.user.entity.Staff;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "store",
        uniqueConstraints = {
                @UniqueConstraint(name = "idx_unique_manager", columnNames = "manager_staff_id")
        },
        indexes = {
            @Index(name = "idx_fk_address_id", columnList = "address_id")
        })
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false,
            columnDefinition = "TINYINT UNSIGNED")
    private Integer storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_staff_id",
            foreignKey = @ForeignKey(name = "fk_store_staff"))
    private Staff managerStaff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_store_address"))
    private Address address;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;
}