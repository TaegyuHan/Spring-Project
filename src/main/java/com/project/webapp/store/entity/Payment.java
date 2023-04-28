package com.project.webapp.store.entity;

import com.project.webapp.user.entity.Customer;
import com.project.webapp.user.entity.Staff;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "payment",
    indexes = {
        @Index(name = "idx_fk_staff_id", columnList = "staff_id"),
        @Index(name = "idx_fk_customer_id", columnList = "customer_id"),
        @Index(name = "idx_fk_rental_id", columnList = "rental_id")
    }
)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", columnDefinition = "SMALLINT UNSIGNED")
    private Integer paymentId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_customer"))
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_staff"))
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "rental_id",
            foreignKey = @ForeignKey(name = "fk_payment_rental"))
    private Rental rental;

    @Column(name = "amount", nullable = false,
            columnDefinition = "DECIMAL(5,2)")
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "payment_date", nullable = false)
    private Timestamp paymentDate;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;
}