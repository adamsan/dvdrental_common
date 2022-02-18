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
        try (PreparedStatement stmt = con.prepareStatement("select * from customer where customer_id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return mapper.map(rs);
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> findAll() {
        try (var s = con.createStatement()) {
            ResultSet rs = s.executeQuery("select * from customer");
            List<Customer> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Customer entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity);
        }
    }

    private void update(Customer customer) {
        try (PreparedStatement stmt = con.prepareStatement("""
                update customer
                set first_name = ?, last_name = ?, email = ?
                where customer_id = ?""")) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setInt(4, customer.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insert(Customer entity) {
        try (PreparedStatement stmt = con.prepareStatement("INSERT INTO customer" +
                " (first_Name,last_Name,email,store_id,address_id) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getEmail());
            stmt.setInt(4, 0);
            stmt.setInt(5, 1);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                entity.setId(id);
            } else {
                throw new RuntimeException("Could not get key for inserted Customer!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement ps = con.prepareStatement("delete from customer where customer_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
