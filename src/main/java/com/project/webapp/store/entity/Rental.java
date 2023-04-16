package com.project.webapp.store.entity;

import com.project.webapp.user.entity.Customer;
import com.project.webapp.user.entity.Staff;
import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "rental",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_rental", columnNames = {"rental_date", "inventory_id", "customer_id"})
        },
        indexes = {
            @Index(name = "idx_fk_inventory_id", columnList = "inventory_id"),
            @Index(name = "idx_fk_customer_id", columnList = "customer_id"),
            @Index(name = "idx_fk_staff_id", columnList = "staff_id")
        }
)
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private int rentalId;

    @Column(name = "rental_date", nullable = false)
    private Date rentalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rental_inventory"))
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rental_customer"))
    private Customer customer;

    @Column(name = "return_date")
    private Date returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rental_staff"))
    private Staff staff;

    @Column(name = "last_update", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date lastUpdate;
}