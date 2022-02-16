package com.codecool.dao;

import com.codecool.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCustomerDao implements CustomerDao {
    private Connection con;

    RowMapper<Customer> mapper = rs -> new Customer(
            rs.getInt("customer_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"));

    public JdbcCustomerDao(Connection con) {
        this.con = con;
    }

    @Override
    public List<Customer> findByEmail(String email) {
        try {
            return tryToFindByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Customer> tryToFindByEmail(String email) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement("select * from customer where email like ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            List<Customer> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
            return results;
        }
    }

    @Override
    public Customer findById(Integer id) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void save(Customer entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }
}
