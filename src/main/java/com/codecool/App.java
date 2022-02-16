package com.codecool;

import com.codecool.dao.CustomerDao;
import com.codecool.dao.JdbcCustomerDao;
import com.codecool.model.Customer;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException {
        try (Connection con = getConnection()) {
            run(con);
        }
    }

    private static void run(Connection con) {
        CustomerDao dao = new JdbcCustomerDao(con);
//        List<Customer> customers = dao.findByEmail("am%");
//        customers.forEach(System.out::println);

        Customer newCustomer = new Customer(null, "Geza", "Szines", "szinesgeza@freemail.hu");
        dao.save(newCustomer);
        System.out.println(newCustomer);
    }

    private static Connection getConnection() {
        var ds = new PGSimpleDataSource();
        ds.setURL(System.getenv("DB_URL"));
        ds.setUser(System.getenv("DB_USER"));
        ds.setPassword(System.getenv("DB_PASSWORD"));
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
