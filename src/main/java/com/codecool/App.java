package com.codecool;

import com.codecool.dao.CustomerDao;
import com.codecool.dao.JdbcCustomerDao;
import com.codecool.model.Customer;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws SQLException {
        try (Connection con = getConnection()) {
            run(con);
        }
    }

    private static void run(Connection con) {
        runCustomerQueries(con);
        runFilmQueries(con);
    }

    private static void runFilmQueries(Connection con) {
    }

    private static void runCustomerQueries(Connection con) {
        CustomerDao dao = new JdbcCustomerDao(con);
        System.out.println("\nList of customers, whos email starts with 'am':");
        List<Customer> customers = dao.findByEmail("am%");
        customers.forEach(System.out::println);

        System.out.println("\nCreate new Customer:");
        Customer newCustomer = new Customer(null, "Geza", "Szines", "szinesgeza@freemail.hu");
        dao.save(newCustomer);
        System.out.println(newCustomer);

        System.out.println("\nDelete the previously created customer:");
        dao.deleteById(newCustomer.getId());
        System.out.println(dao.findById(newCustomer.getId())); // should be null

        System.out.println("\nFind customer:");
        Customer c = dao.findById(602);
        System.out.println(c);

        System.out.println("\nUpdate existing customer, switch names:");
        var tmp = c.getFirstName();
        c.setFirstName(c.getLastName());
        c.setLastName(tmp);
        dao.save(c);
        System.out.println(c);
        System.out.println("\nFinding all customers (displaying 10):");
        String lastNames = dao.findAll().stream()
                .limit(10)
                .map(Customer::getLastName)
                .collect(Collectors.joining(", "));
        System.out.println(lastNames);
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
