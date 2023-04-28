package com.project.webapp.store.entity;

import com.project.webapp.user.entity.Customer;
import com.project.webapp.user.entity.Staff;

public class RentalTest {

    public static Rental buildRental(Inventory inventory, Customer customer, Staff staff) {
        return Rental.builder()
                .inventory(inventory)
                .customer(customer)
                .staff(staff)
                .build();
    }
}