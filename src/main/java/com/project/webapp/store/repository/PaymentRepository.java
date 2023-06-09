package com.project.webapp.store.repository;

import com.project.webapp.store.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  PaymentRepository extends JpaRepository<Payment, Integer> {

}