package com.project.webapp.user.entity;

import com.project.webapp.area.entity.Address;
import com.project.webapp.store.entity.Store;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Table(name = "customer",
        indexes = {
            @Index(name = "idx_fk_store_id", columnList = "store_id"),
            @Index(name = "idx_fk_address_id", columnList = "address_id"),
            @Index(name = "idx_last_name", columnList = "last_name")
        }
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "SMALLINT UNSIGNED")
    private Integer customerId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(name = "fk_customer_store"))
    private Store store;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "fk_customer_address"))
    private Address address;

    @Column(name = "active", nullable = false)
    private boolean active;

    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;
}