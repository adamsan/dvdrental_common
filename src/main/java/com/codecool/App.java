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
        System.out.println("List of customers, whos email starts with 'am':");
        List<Customer> customers = dao.findByEmail("am%");
        customers.forEach(System.out::println);

        System.out.println("Create new Customer:");
        Customer newCustomer = new Customer(null, "Geza", "Szines", "szinesgeza@freemail.hu");
        dao.save(newCustomer);
        System.out.println(newCustomer);

        System.out.println("Find customer:");
        Customer c = dao.findById(602);
        System.out.println(c);

        System.out.println("Update existing customer, switch names:");
        var tmp = c.getFirstName();
        c.setFirstName(c.getLastName());
        c.setLastName(tmp);
        dao.save(c);
        System.out.println(c);
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
