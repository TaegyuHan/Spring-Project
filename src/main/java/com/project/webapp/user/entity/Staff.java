package com.project.webapp.user.entity;

import com.project.webapp.area.entity.Address;
import com.project.webapp.store.entity.Store;
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
@Table(name = "staff",
        indexes = {
                @Index(name = "idx_fk_store_id", columnList = "store_id"),
                @Index(name = "idx_fk_address_id", columnList = "address_id")
        })
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Short staffId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_staff_address"))
    private Address address;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_staff_store"))
    private Store store;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "username", length = 16, nullable = false)
    private String username;

    @Column(name = "password", length = 40)
    private String password;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;
}