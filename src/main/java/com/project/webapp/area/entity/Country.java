package com.project.webapp.area.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", columnDefinition = "SMALLINT UNSIGNED")
    private Integer countryId;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;
}