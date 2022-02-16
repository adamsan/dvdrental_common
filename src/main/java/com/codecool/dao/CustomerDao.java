package com.codecool.dao;

import com.codecool.model.Customer;

import java.util.List;

public interface CustomerDao extends Dao<Customer> {
    List<Customer> findByEmail(String email);
}
