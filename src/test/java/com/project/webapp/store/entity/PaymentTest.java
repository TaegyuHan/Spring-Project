package com.project.webapp.store.entity;

import com.project.webapp.user.entity.Customer;
import com.project.webapp.user.entity.Staff;

import java.math.BigDecimal;

public class PaymentTest {

    public static Payment buildPayment(Customer customer, Staff staff, Rental rental) {
        return Payment.builder()
                .customer(customer)
                .staff(staff)
                .rental(rental)
                .amount(new BigDecimal("2.99"))
                .build();
    }
}